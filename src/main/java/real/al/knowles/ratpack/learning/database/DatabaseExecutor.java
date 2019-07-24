package real.al.knowles.ratpack.learning.database;

import ratpack.exec.Blocking;
import ratpack.exec.Operation;
import ratpack.exec.Promise;
import ratpack.func.Block;
import ratpack.func.Factory;
import ratpack.jdbctx.Transaction;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

import static com.google.common.math.IntMath.pow;

public class DatabaseExecutor {

    private final int retryCount;

    private final int retryBaseMillis;

    private final DataSource dataSource;

    @Inject
    public DatabaseExecutor(int retryCount, int retryBaseMillis, DataSource dataSource) {
        this.retryCount = retryCount;
        this.retryBaseMillis = retryBaseMillis;
        this.dataSource = dataSource;
    }

    public <T> Promise<T> execute(Factory<T> work) {
        return Blocking.get(() ->
                Promise.sync(work)
                        .retry(retryCount, (attempt, throwable) ->
                                configureRetry(retryBaseMillis, attempt, throwable))
                        .onError(DatabaseException::doThrow))
                .flatMap(result -> result);
    }

    public <T> Promise<T> executeInTransaction(Factory<T> work) {
        return Blocking.get(() -> {
            Connection connection = dataSource.getConnection();
            connection.setReadOnly(false);

            return Transaction.get(() -> connection)
                    .wrap(Promise.sync(work))
                    .retry(retryCount, (attempt, throwable) ->
                            configureRetry(retryBaseMillis, attempt, throwable))
                    .onError(DatabaseException::doThrow);
        })
                .flatMap(result -> result);
    }

    public void execute(Block work) {
        Blocking.exec(() ->
                Operation.of(work)
                        .promise()
                        .retry(retryCount, (attempt, throwable) ->
                                configureRetry(retryBaseMillis, attempt, throwable))
                        .onError(DatabaseException::doThrow));
    }

    public void executeInTransaction(Block work) {
        Blocking.exec(() -> {
            Connection connection = dataSource.getConnection();
            connection.setReadOnly(false);

            Transaction.get(() -> connection)
                    .wrap(Operation.of(work))
                    .promise()
                    .retry(retryCount, (attempt, throwable) ->
                            configureRetry(retryBaseMillis, attempt, throwable))
                    .onError(DatabaseException::doThrow);
        });
    }

    private Promise<Duration> configureRetry(int retryBaseMillis, Integer attempt, Throwable throwable) {
        DatabaseException exception = new DatabaseException(throwable);
        if (exception.isRetryable()) {
            int retryInterval = retryBaseMillis * pow(attempt, 2);
            int retryIntervalWithJitter = ThreadLocalRandom.current().nextInt(retryInterval);
            return Promise.value(Duration.ofMillis(retryIntervalWithJitter));
        }

        throw exception;
    }

}
