package real.al.knowles.ratpack.learning.database;

public class DatabaseException extends RuntimeException {

    public DatabaseException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
