package real.al.knowles.ratpack.learning.retry;

public abstract class RetryableException extends RuntimeException {

    public RetryableException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract boolean isRetryable();

}
