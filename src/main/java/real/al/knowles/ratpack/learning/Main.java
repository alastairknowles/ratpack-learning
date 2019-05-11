package real.al.knowles.ratpack.learning;

import ratpack.guice.Guice;
import ratpack.server.RatpackServer;
import real.al.knowles.ratpack.learning.blocking.BlockingController;
import real.al.knowles.ratpack.learning.nonblocking.NonBlockingController;

public class Main {

    public static void main(String[] args) throws Exception {
        RatpackServer.start(server -> server
                .registry(Guice.registry(binding ->
                        binding.module(DependencyModule.class)))
                .handlers(chain -> chain
                        .get(context -> context.render("homepage"))
                        .get("blocking", context ->
                                context.get(BlockingController.class).get(context))
                        .get("non-blocking", context ->
                                context.render(context.get(NonBlockingController.class).get()))));
    }

}
