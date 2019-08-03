package real.al.knowles.ratpack.learning.retry;

import ratpack.exec.Promise;
import ratpack.util.Exceptions;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

import static com.google.common.math.IntMath.pow;
import static java.util.Objects.requireNonNull;

public class RetryHandler {

    private final RetryEvaluator retryEvaluator;

    public RetryHandler(RetryEvaluator retryEvaluator) {
        requireNonNull(retryEvaluator, "Retry evaluator must be provided");
        this.retryEvaluator = retryEvaluator;
    }

    public Promise<Duration> withFullJitter(int retryBaseMillis, int attempt, ThreadLocalRandom random,
                                            Throwable error) {
        if (retryEvaluator.isRetryable(error)) {
            int retryInterval = retryBaseMillis * pow(attempt, 2);
            int retryIntervalWithJitter = random.nextInt(retryInterval);

            Duration retryIntervalDuration = Duration.ofMillis(retryIntervalWithJitter);
            return Promise.value(retryIntervalDuration);
        }

        throw Exceptions.uncheck(error);
    }

}
