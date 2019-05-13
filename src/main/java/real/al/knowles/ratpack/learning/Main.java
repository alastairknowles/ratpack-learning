package real.al.knowles.ratpack.learning;

import ratpack.guice.Guice;
import ratpack.server.RatpackServer;
import real.al.knowles.ratpack.learning.blocking.BlockingChain;
import real.al.knowles.ratpack.learning.nonblocking.NonBlockingChain;

public class Main {

    public static void main(String[] args) throws Exception {
        RatpackServer.start(server -> server
                .registry(Guice.registry(binding ->
                        binding.module(DependencyModule.class)))
                .handlers(chain -> chain
                        .get(context -> context.render("homepage"))
                        .prefix("blocking", BlockingChain.class)
                        .prefix("non-blocking", NonBlockingChain.class)));
    }

}
