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
                Failsafe.with(retryPolicy).get(() -> {
                    try {
                        return Promise.sync(work);
                    } catch (Throwable throwable) {
                        throw new DatabaseException("Operation failed", throwable);
                    }
                }))
                .flatMap(result -> result);
    }

    public <T> Promise<T> executeInTransaction(Factory<T> work) {
        return Blocking.get(() ->
                Failsafe.with(retryPolicy).get(() ->
                        Transaction.get(dataSource::getConnection)
                                .wrap(Promise.sync(work))
                                .onError(throwable -> {
                                    throw new DatabaseException("Operation failed", throwable);
                                })))
                .flatMap(result -> result);
    }

    public void execute(Block work) {
        Blocking.exec(() ->
                Failsafe.with(retryPolicy).run(() -> {
                    try {
                        work.execute();
                    } catch (Exception exception) {
                        throw new DatabaseException("Operation failed", exception);
                    }
                }));
    }

    public void executeInTransaction(Block work) {
        Blocking.exec(() ->
                Failsafe.with(retryPolicy).run(() ->
                        Transaction.get(dataSource::getConnection)
                                .wrap(Operation.of(work))
                                .onError(throwable -> {
                                    throw new DatabaseException("Operation failed", throwable);
                                })));
    }

}
