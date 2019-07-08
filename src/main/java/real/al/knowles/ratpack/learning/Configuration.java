package real.al.knowles.ratpack.learning;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import real.al.knowles.ratpack.learning.blocking.BlockingChain;
import real.al.knowles.ratpack.learning.blocking.BlockingService;
import real.al.knowles.ratpack.learning.helloworld.HelloService;
import real.al.knowles.ratpack.learning.helloworld.HelloWorldChain;
import real.al.knowles.ratpack.learning.helloworld.HelloWorldService;
import real.al.knowles.ratpack.learning.helloworld.WorldService;
import real.al.knowles.ratpack.learning.metric.LoggerService;
import real.al.knowles.ratpack.learning.metric.MetricService;
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

    @Provides
    @Singleton
    public LoggerService loggerService() {
        return new LoggerService();
    }

    @Provides
    @Singleton
    public MetricService metricService(LoggerService loggerService) {
        return new MetricService(loggerService);
    }

    @Provides
    @Singleton
    public HelloService helloService() {
        return new HelloService();
    }

    @Provides
    @Singleton
    public WorldService worldService() {
        return new WorldService();
    }

    @Provides
    @Singleton
    public HelloWorldService helloWorldService(HelloService helloService, WorldService worldService,
                                               MetricService metricService) {
        return new HelloWorldService(helloService, worldService, metricService);
    }

    @Provides
    @Singleton
    public HelloWorldChain helloWorldChain(HelloWorldService helloWorldService) {
        return new HelloWorldChain(helloWorldService);
    }

}
