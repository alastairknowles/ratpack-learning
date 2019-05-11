package real.al.knowles.ratpack.learning;

import com.google.inject.AbstractModule;
import real.al.knowles.ratpack.learning.blocking.BlockingService;
import real.al.knowles.ratpack.learning.blocking.BlockingServiceImpl;
import real.al.knowles.ratpack.learning.nonblocking.NonBlockingService;
import real.al.knowles.ratpack.learning.nonblocking.NonBlockingServiceImpl;

import static com.google.inject.Scopes.SINGLETON;

public class DependencyModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(BlockingService.class).to(BlockingServiceImpl.class).in(SINGLETON);
        bind(NonBlockingService.class).to(NonBlockingServiceImpl.class).in(SINGLETON);
    }

}
