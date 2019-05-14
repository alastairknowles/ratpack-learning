package real.al.knowles.ratpack.learning;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import real.al.knowles.ratpack.learning.blocking.BlockingChain;
import real.al.knowles.ratpack.learning.blocking.BlockingService;
import real.al.knowles.ratpack.learning.nonblocking.NonBlockingChain;
import real.al.knowles.ratpack.learning.nonblocking.NonBlockingService;

public class Configuration extends AbstractModule {

    @Override
    protected void configure() {

    }

    @Provides
    @Singleton
    public BlockingService blockingService() {
        return new BlockingService();
    }

    @Provides
    @Singleton
    public BlockingChain blockingChain(BlockingService blockingService) {
        return new BlockingChain(blockingService);
    }

    @Provides
    @Singleton
    public NonBlockingService nonBlockingService() {
        return new NonBlockingService();
    }

    @Provides
    @Singleton
    public NonBlockingChain nonBlockingChain(NonBlockingService nonBlockingService) {
        return new NonBlockingChain(nonBlockingService);
    }

}
