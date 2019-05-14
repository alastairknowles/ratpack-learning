package real.al.knowles.ratpack.learning;

import ratpack.server.RatpackServer;
import real.al.knowles.ratpack.learning.blocking.BlockingChain;
import real.al.knowles.ratpack.learning.nonblocking.NonBlockingChain;

import static ratpack.guice.Guice.registry;

public class Main {

    public static void main(String[] args) throws Exception {
        RatpackServer.start(server -> server
                .registry(registry(binding ->
                        binding.module(Configuration.class)))
                .handlers(chain -> chain
                        .get(context -> context.render("homepage"))
                        .prefix("blocking", BlockingChain.class)
                        .prefix("non-blocking", NonBlockingChain.class)));
    }

}
