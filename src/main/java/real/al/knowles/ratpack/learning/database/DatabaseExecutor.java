package real.al.knowles.ratpack.learning.database;

import com.codahale.metrics.MetricRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import ratpack.exec.Blocking;
import ratpack.exec.ExecResult;
import ratpack.exec.Promise;
import ratpack.func.BiAction;
import ratpack.func.BiFunction;
import ratpack.func.Block;
import ratpack.func.Factory;
import ratpack.jdbctx.Transaction;
import real.al.knowles.ratpack.learning.retry.RetryHandler;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

public class DatabaseExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseExecutor.class);

    private final int retryCount;

    private final int retryBaseMillis;

    private final DataSource dataSource;

    private final RetryHandler retryHandler;

    private final BiAction<ExecResult<?>, Duration> queryExecutionTimeLogger;

    private BiFunction<Integer, Throwable, Promise<Duration>> queryRetryer;

    @Inject
    public DatabaseExecutor(int retryCount, int retryBaseMillis, DataSource dataSource, RetryHandler retryHandler,
                            MetricRegistry metricRegistry) {
        this.retryCount = retryCount;
        this.retryBaseMillis = retryBaseMillis;
        this.dataSource = dataSource;
        this.retryHandler = retryHandler;
        this.queryExecutionTimeLogger = buildQueryExecutionTimeLogger(metricRegistry);
        this.queryRetryer = buildQueryRetryer();
    }

    public Promise<Void> execute(Block work) {
        return Blocking.op(work)
                .promise()
                .timeResult(queryExecutionTimeLogger)
                .retry(retryCount, queryRetryer);
    }

    public Promise<Void> executeInTransaction(Block work) {
        return Blocking.get(this::getTransaction)
                .flatOp(transaction -> transaction.wrap(Blocking.op(work)))
                .promise()
                .timeResult(queryExecutionTimeLogger)
                .retry(retryCount, queryRetryer);
    }

    public <T> Promise<T> execute(Factory<T> work) {
        return Blocking.get(work)
                .timeResult(queryExecutionTimeLogger)
                .retry(retryCount, queryRetryer);
    }

    public <T> Promise<T> executeInTransaction(Factory<T> work) {
        return Blocking.get(this::getTransaction)
                .flatMap(transaction -> transaction.wrap(Blocking.get(work)))
                .timeResult(queryExecutionTimeLogger)
                .retry(retryCount, queryRetryer);
    }

    private Transaction getTransaction() {
        return Transaction.get(() -> {
            Connection connection = dataSource.getConnection();
            connection.setReadOnly(false);
            return connection;
        });
    }

    private BiAction<ExecResult<?>, Duration> buildQueryExecutionTimeLogger(MetricRegistry metricRegistry) {
        return (result, duration) -> {
            long executionTime = duration.toMillis();
            String executionResult = result.isSuccess() ? "succeeded" : "failed";

            LOGGER.info("Query execution {} in: {}ms", executionResult, executionTime);

            metricRegistry.histogram("atm-addon.txDurationMs").update(executionTime);
            MDC.put("txDuration", Long.toString(executionTime));
        };
    }

    private BiFunction<Integer, Throwable, Promise<Duration>> buildQueryRetryer() {
        return (attempt, error) -> {
            ThreadLocalRandom random = ThreadLocalRandom.current();
            return retryHandler.withFullJitter(retryBaseMillis, attempt, random, error);
        };
    }

}
