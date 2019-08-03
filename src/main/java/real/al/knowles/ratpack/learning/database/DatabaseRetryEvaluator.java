package real.al.knowles.ratpack.learning.database;

import com.google.common.collect.ImmutableList;
import real.al.knowles.ratpack.learning.retry.RetryEvaluator;

import java.sql.SQLException;
import java.util.List;

public class DatabaseRetryEvaluator implements RetryEvaluator {

    private static final int LOCK_WAIT_TIMEOUT_EXCEEDED = 1205;

    private static final int DEADLOCK = 1213;

    private static final List<Integer> retryableSqlErrorCodes =
            ImmutableList.of(LOCK_WAIT_TIMEOUT_EXCEEDED, DEADLOCK);

    @Override
    public boolean isRetryable(Throwable error) {
        int sqlErrorCode = getSqlErrorCode(error);
        return retryableSqlErrorCodes.contains(sqlErrorCode);
    }

    private int getSqlErrorCode(Throwable error) {
        if (error == null) {
            return 0;
        }

        if (error instanceof SQLException) {
            return ((SQLException) error).getErrorCode();
        }

        Throwable wrappedError = error.getCause();
        return getSqlErrorCode(wrappedError);
    }

}
