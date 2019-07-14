package real.al.knowles.ratpack.learning.database;

import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.querydsl.sql.Configuration;
import com.querydsl.sql.MySQLTemplates;
import com.querydsl.sql.SQLQueryFactory;
import com.zaxxer.hikari.HikariDataSource;
import ratpack.guice.ConfigurableModule;
import ratpack.jdbctx.Transaction;

import javax.sql.DataSource;

public class DatabaseModule extends ConfigurableModule<DatabaseProperties> {

    @Override
    protected void configure() {

    }

    @Provides
    @Singleton
    public DataSource dataSource(DatabaseProperties databaseProperties) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(databaseProperties.getDriver());
        dataSource.setJdbcUrl(databaseProperties.getUrl());
        dataSource.setUsername(databaseProperties.getUser());
        dataSource.setPassword(databaseProperties.getPassword());
        return Transaction.dataSource(dataSource);
    }

    @Provides
    @Singleton
    public SQLQueryFactory queryFactory(DataSource dataSource) {
        Configuration configuration = new Configuration(MySQLTemplates.DEFAULT);
        return new SQLQueryFactory(configuration, dataSource);
    }

}
