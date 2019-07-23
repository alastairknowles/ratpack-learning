package real.al.knowles.ratpack.learning.database;

import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.RetryPolicy;
import ratpack.exec.Blocking;
import ratpack.exec.Operation;
import ratpack.exec.Promise;
import ratpack.func.Block;
import ratpack.func.Factory;
import ratpack.jdbctx.Transaction;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;

public class DatabaseExecutor {

    private final DataSource dataSource;

    private final RetryPolicy<Promise> retryPolicy;

    @Inject
    public DatabaseExecutor(DataSource dataSource, RetryPolicy<Promise> retryPolicy) {
        this.dataSource = dataSource;
        this.retryPolicy = retryPolicy;
    }

    public <T> Promise<T> execute(Factory<T> work) {
        return Blocking.get(() ->
                Failsafe.with(retryPolicy).get(() ->
                        Promise.sync(work)
                                .onError(throwable -> {
                                    throw new DatabaseException("Operation failed", throwable);
                                })))
                .flatMap(result -> result);
    }

    public <T> Promise<T> executeInTransaction(Factory<T> work) {
        return Blocking.get(() ->
                Failsafe.with(retryPolicy).get(() -> {
                    Connection connection = dataSource.getConnection();
                    connection.setReadOnly(false);

                    return Transaction.get(() -> connection)
                            .wrap(Promise.sync(work))
                            .onError(throwable -> {
                                throw new DatabaseException("Operation failed", throwable);
                            });
                }))
                .flatMap(result -> result);
    }

    public void execute(Block work) {
        Blocking.exec(() ->
                Failsafe.with(retryPolicy).run(() ->
                        Operation.of(work)
                                .onError(throwable -> {
                                    throw new DatabaseException("Operation failed", throwable);
                                })));
    }

    public void executeInTransaction(Block work) {
        Blocking.exec(() ->
                Failsafe.with(retryPolicy).run(() -> {
                    Connection connection = dataSource.getConnection();
                    connection.setReadOnly(false);

                    Transaction.get(() -> connection)
                            .wrap(Operation.of(work))
                            .onError(throwable -> {
                                throw new DatabaseException("Operation failed", throwable);
                            });
                }));
    }

}
