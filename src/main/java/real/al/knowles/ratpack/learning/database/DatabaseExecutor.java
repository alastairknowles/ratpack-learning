package real.al.knowles.ratpack.learning.database;

import ratpack.exec.Blocking;
import ratpack.exec.Operation;
import ratpack.exec.Promise;
import ratpack.func.Block;
import ratpack.func.Factory;
import ratpack.jdbctx.Transaction;
import real.al.knowles.ratpack.learning.retry.RetryEvaluator;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

public class DatabaseExecutor {

    private final int retryCount;

    private final int retryBaseMillis;

    private final DataSource dataSource;

    private final RetryEvaluator retryEvaluator;

    @Inject
    public DatabaseExecutor(int retryCount, int retryBaseMillis, DataSource dataSource, RetryEvaluator retryEvaluator) {
        this.retryCount = retryCount;
        this.retryBaseMillis = retryBaseMillis;
        this.dataSource = dataSource;
        this.retryEvaluator = retryEvaluator;
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

    private Promise<Duration> configureRetry(int retryBaseMillis, Integer attempt, Throwable error) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        DatabaseException wrappedError = new DatabaseException(error);
        return retryEvaluator.withFullJitter(retryBaseMillis, attempt, random, wrappedError);
    }

}
