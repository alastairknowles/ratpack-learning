package real.al.knowles.ratpack.learning.retry;

import ratpack.exec.Promise;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

import static com.google.common.math.IntMath.pow;

public class RetryEvaluator {

    public Promise<Duration> withFullJitter(int retryBaseMillis, Integer attempt, ThreadLocalRandom random,
                                            RetryableException error) {
        if (error.isRetryable()) {
            int retryInterval = retryBaseMillis * pow(attempt, 2);
            int retryIntervalWithJitter = random.nextInt(retryInterval);
            return Promise.value(Duration.ofMillis(retryIntervalWithJitter));
        }

        throw error;
    }

}
