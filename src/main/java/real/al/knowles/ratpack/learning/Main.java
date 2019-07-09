package real.al.knowles.ratpack.learning;

import real.al.knowles.ratpack.learning.blocking.BlockingChain;
import real.al.knowles.ratpack.learning.database.DatabaseModule;
import real.al.knowles.ratpack.learning.helloworld.HelloWorldChain;
import real.al.knowles.ratpack.learning.nonblocking.NonBlockingChain;
import real.al.knowles.ratpack.learning.project.ProjectChain;
import real.al.knowles.ratpack.learning.project.ProjectModule;

import static ratpack.guice.Guice.registry;
import static ratpack.server.RatpackServer.start;

public class Main {

    public static void main(String[] args) throws Exception {
        start(server -> server
                .registry(registry(binding -> binding
                        .module(Configuration.class)
                        .module(DatabaseModule.class)
                        .module(ProjectModule.class)))
                .handlers(chain -> chain
                        .get(context -> context.render("homepage"))
                        .insert(BlockingChain.class)
                        .insert(NonBlockingChain.class)
                        .insert(HelloWorldChain.class)
                        .prefix("project", ProjectChain.class)));
    }

}
