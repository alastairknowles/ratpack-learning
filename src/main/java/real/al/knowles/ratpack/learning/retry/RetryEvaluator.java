package real.al.knowles.ratpack.learning.retry;

public interface RetryEvaluator {

    boolean isRetryable(Throwable error);

}
