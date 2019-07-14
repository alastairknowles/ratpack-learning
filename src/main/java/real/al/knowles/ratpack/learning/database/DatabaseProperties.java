package real.al.knowles.ratpack.learning.database;

import lombok.Getter;
import lombok.Setter;
import ratpack.config.ConfigData;
import ratpack.func.Action;

@Getter
@Setter
public class DatabaseProperties {

    private String driver;

    private String url;

    private String user;

    private String password;

    public static Action<DatabaseProperties> loadDatabaseProperties(ConfigData configData) {
        return databaseProperties -> {
            databaseProperties.setDriver(configData.get("/database/driver", String.class));
            databaseProperties.setUrl(configData.get("/database/url", String.class));
            databaseProperties.setUser(configData.get("/database/user", String.class));
            databaseProperties.setPassword(configData.get("/database/password", String.class));
        };
    }

}
