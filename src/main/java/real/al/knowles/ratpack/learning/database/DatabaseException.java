package real.al.knowles.ratpack.learning.database;

import com.google.common.collect.ImmutableList;
import com.querydsl.core.QueryException;
import lombok.Getter;
import real.al.knowles.ratpack.learning.retry.RetryableException;

import java.sql.SQLException;
import java.util.List;

@Getter
public class DatabaseException extends RetryableException {

    private static final List<Integer> retryableSqlErrorCodes =
            ImmutableList.of(1205, 1213);

    private final int sqlErrorCode;

    public static DatabaseException doThrow(Throwable cause) {
        if (cause instanceof DatabaseException) {
            throw (DatabaseException) cause;
        }

        throw new DatabaseException(cause);
    }

    public DatabaseException(Throwable cause) {
        super("Operation failed", cause);
        this.sqlErrorCode = getSqlErrorCode(cause);
    }

    public boolean isRetryable() {
        return retryableSqlErrorCodes.contains(sqlErrorCode);
    }

    private int getSqlErrorCode(Throwable cause) {
        if (cause instanceof SQLException) {
            return ((SQLException) cause).getErrorCode();
        } else if (cause instanceof QueryException) {
            return ((SQLException) cause.getCause()).getErrorCode();
        }

        return 0;
    }

}
