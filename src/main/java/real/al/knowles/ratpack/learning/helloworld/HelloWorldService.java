package real.al.knowles.ratpack.learning.helloworld;

import com.google.common.base.Joiner;
import com.google.inject.Inject;
import ratpack.exec.util.ParallelBatch;
import ratpack.handling.Handler;
import real.al.knowles.ratpack.learning.metric.MetricService;

public class HelloWorldService {

    private final HelloService helloService;

    private final WorldService worldService;

    private final MetricService metricService;

    @Inject
    public HelloWorldService(HelloService helloService, WorldService worldService, MetricService metricService) {
        this.helloService = helloService;
        this.worldService = worldService;
        this.metricService = metricService;
    }

    Handler sayHelloWorld() {
        return (context ->
                ParallelBatch.of(helloService.sayHello(), worldService.sayWorld())
                        .yield()
                        .map(words -> Joiner.on(" ").join(words))
                        .map(words -> metricService.record(getClass().getDeclaredMethod("sayHelloWorld"), words))
                        .then(context::render));
    }

}
