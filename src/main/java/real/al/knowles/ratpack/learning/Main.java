package real.al.knowles.ratpack.learning;

import ratpack.guice.Guice;
import ratpack.server.RatpackServer;
import real.al.knowles.ratpack.learning.blocking.BlockingService;
import real.al.knowles.ratpack.learning.nonblocking.NonBlockingService;

public class Main {

    public static void main(String[] args) throws Exception {
        RatpackServer.start(server -> server
                .registry(Guice.registry(binding ->
                        binding.module(DependencyModule.class)))
                .handlers(chain -> chain
                        .get(context -> context.render("homepage"))
                        .get("blocking", context -> {
                            BlockingService blockingService = context.get(BlockingService.class);
                            blockingService.render(context);
                        })
                        .get("non-blocking", context -> {
                            NonBlockingService nonBlockingService = context.get(NonBlockingService.class);
                            context.render(nonBlockingService.render());
                        })));
    }

}
