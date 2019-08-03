package real.al.knowles.ratpack.learning.database;

import lombok.Getter;
import lombok.Setter;
import ratpack.config.ConfigData;
import ratpack.func.Action;

@Getter
@Setter
public class DatabaseProperties {

    private String url;

    private int retryCount;

    private int retryBaseMillis;

    public static Action<DatabaseProperties> loadDatabaseProperties(ConfigData configData) {
        return databaseProperties -> {
            databaseProperties.setUrl(configData.get("/database/url", String.class));
            databaseProperties.setRetryCount(configData.get("/database/retryCount", Integer.class));
            databaseProperties.setRetryBaseMillis(configData.get("/database/retryBaseMillis", Integer.class));
        };
    }

}
