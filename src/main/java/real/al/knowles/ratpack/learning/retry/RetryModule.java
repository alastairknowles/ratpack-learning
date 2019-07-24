package real.al.knowles.ratpack.learning.retry;

import com.google.inject.Provides;
import com.google.inject.Singleton;
import ratpack.guice.ConfigurableModule;
import real.al.knowles.ratpack.learning.database.DatabaseProperties;

public class RetryModule extends ConfigurableModule<DatabaseProperties> {

    @Override
    protected void configure() {

    }

    @Provides
    @Singleton
    public RetryEvaluator retryEvaluator() {
        return new RetryEvaluator();
    }

}

