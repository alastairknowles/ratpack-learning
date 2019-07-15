package real.al.knowles.ratpack.learning.database;

import ratpack.exec.Blocking;
import ratpack.exec.Operation;
import ratpack.exec.Promise;
import ratpack.func.Block;
import ratpack.func.Factory;
import ratpack.jdbctx.Transaction;

import javax.inject.Inject;
import javax.sql.DataSource;

public class TransactionWrapper {

    private final DataSource dataSource;

    @Inject
    public TransactionWrapper(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> Promise<T> execute(Factory<T> work) {
        return Blocking.get(() -> {
            Transaction transaction = Transaction.get(dataSource::getConnection);
            return transaction.wrap(Promise.sync(work));
        }).flatMap(result -> result);
    }

    public void execute(Block work) {
        Blocking.exec(() -> {
            Transaction transaction = Transaction.get(dataSource::getConnection);
            transaction.wrap(Operation.of(work));
        });
    }

}
