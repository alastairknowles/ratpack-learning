package real.al.knowles.ratpack.learning;

import ratpack.config.ConfigData;
import ratpack.func.Action;
import real.al.knowles.ratpack.learning.database.DatabaseModule;
import real.al.knowles.ratpack.learning.database.DatabaseProperties;
import real.al.knowles.ratpack.learning.jackson.JacksonModule;
import real.al.knowles.ratpack.learning.project.ProjectChain;
import real.al.knowles.ratpack.learning.project.ProjectModule;

import static ratpack.guice.Guice.registry;
import static ratpack.server.RatpackServer.start;
import static real.al.knowles.ratpack.learning.database.DatabaseProperties.loadDatabaseProperties;

public class Main {

    public static void main(String[] args) throws Exception {
        ConfigData configData =
                ConfigData.builder()
                        .props(Main.class.getResource("/db/database.properties"))
                        .build();

        Action<DatabaseProperties> databaseProperties = loadDatabaseProperties(configData);

        start(server -> server
                .registry(registry(binding -> binding
                        .module(JacksonModule.class)
                        .module(DatabaseModule.class, databaseProperties)
                        .module(ProjectModule.class)))
                .handlers(chain -> chain
                        .get(context -> context.render("homepage"))
                        .prefix("project", ProjectChain.class)));
    }

}
