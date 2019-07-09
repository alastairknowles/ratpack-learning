package real.al.knowles.ratpack.learning.database;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.mysql.cj.jdbc.MysqlDataSource;

public class DatabaseModule extends AbstractModule {

    @Override
    protected void configure() {

    }

    @Provides
    @Singleton
    public MysqlDataSource dataSource() {
        return new MysqlDataSource();
    }


}
