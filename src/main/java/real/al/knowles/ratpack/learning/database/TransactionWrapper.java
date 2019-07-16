package real.al.knowles.ratpack.learning.database;

import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.RetryPolicy;
import ratpack.exec.Blocking;
import ratpack.exec.Promise;
import ratpack.func.Block;
import ratpack.func.Factory;
import ratpack.jdbctx.Transaction;

import javax.inject.Inject;
import javax.sql.DataSource;

public class TransactionWrapper {

    private final DataSource dataSource;

    private final RetryPolicy<Promise> retryPolicy;

    @Inject
    public TransactionWrapper(DataSource dataSource, RetryPolicy<Promise> retryPolicy) {
        this.dataSource = dataSource;
        this.retryPolicy = retryPolicy;
    }

    public <T> Promise<T> execute(Factory<T> work) {
        return Blocking.get(() ->
                Failsafe.with(retryPolicy).get(() -> {
                    Transaction transaction = Transaction.get(dataSource::getConnection);
                    transaction.begin();

                    try {
                        T result = work.create();
                        transaction.commit();
                        return Promise.value(result);
                    } catch (Exception exception) {
                        transaction.rollback();
                        throw new DatabaseException("Operation failed", exception);
                    }
                }))
                .flatMap(result -> result);
    }

    public void execute(Block work) {
        Blocking.exec(() ->
                Failsafe.with(retryPolicy).run(() -> {
                    Transaction transaction = Transaction.get(dataSource::getConnection);
                    transaction.begin();

                    try {
                        work.execute();
                        transaction.commit();
                    } catch (Exception exception) {
                        transaction.rollback();
                        throw new DatabaseException("Operation failed", exception);
                    }
                }));
    }

}
