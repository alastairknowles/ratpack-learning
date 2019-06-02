package real.al.knowles.ratpack.learning.metric;

import com.google.inject.Inject;
import ratpack.exec.Execution;

import java.lang.reflect.Method;

public class MetricService {

    private static final String MESSAGE = "%s - %s: %s";

    private final LoggerService loggerService;

    @Inject
    public MetricService(LoggerService loggerService) {
        this.loggerService = loggerService;
    }

    public <T> T record(Method method, T returnValue) {
        Execution.fork().start(context -> {
            Class<?> clazz = method.getDeclaringClass();
            loggerService.info(String.format(MESSAGE, clazz, method, returnValue));
        });

        return returnValue;
    }

}
