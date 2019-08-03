package real.al.knowles.ratpack.learning.database;

import com.codahale.metrics.MetricRegistry;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.querydsl.sql.Configuration;
import com.querydsl.sql.MySQLTemplates;
import com.querydsl.sql.SQLQueryFactory;
import ratpack.guice.ConfigurableModule;
import ratpack.jdbctx.Transaction;
import real.al.knowles.ratpack.learning.retry.RetryHandler;

import javax.sql.DataSource;

public class DatabaseModule extends ConfigurableModule<DatabaseProperties> {

    @Override
    protected void configure() {

    }

    @Provides
    @Singleton
    public DataSource dataSource(DatabaseProperties databaseProperties) {
        String connectionUrl = databaseProperties.getUrl();
        DataSource dataSource = new DefaultSupportingDataSource(connectionUrl, true, true);
        return Transaction.dataSource(dataSource);
    }

    @Provides
    @Singleton
    public DatabaseExecutor databaseExecutor(DatabaseProperties databaseProperties, DataSource dataSource,
                                             MetricRegistry metricRegistry) {
        int retryCount = databaseProperties.getRetryCount();
        int retryIntervalMillis = databaseProperties.getRetryBaseMillis();

        RetryHandler retryHandler =
                new RetryHandler(
                        new DatabaseRetryEvaluator());

        return new DatabaseExecutor(retryCount, retryIntervalMillis, dataSource, retryHandler, metricRegistry);
    }

    @Provides
    @Singleton
    public SQLQueryFactory queryFactory(DataSource dataSource) {
        Configuration configuration = new Configuration(MySQLTemplates.DEFAULT);
        return new SQLQueryFactory(configuration, dataSource);
    }

}

