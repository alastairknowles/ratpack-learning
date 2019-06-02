package real.al.knowles.ratpack.learning.metric;

import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class LoggerService {

    private static final Logger LOGGER = getLogger(LoggerService.class);

    void info(String message) {
        LOGGER.info(message);
    }

}
