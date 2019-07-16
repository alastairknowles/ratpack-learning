package real.al.knowles.ratpack.learning.database;

import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.querydsl.sql.Configuration;
import com.querydsl.sql.MySQLTemplates;
import com.querydsl.sql.SQLQueryFactory;
import com.zaxxer.hikari.HikariDataSource;
import net.jodah.failsafe.RetryPolicy;
import ratpack.exec.Promise;
import ratpack.guice.ConfigurableModule;

import javax.inject.Provider;
import javax.sql.DataSource;
import java.sql.Connection;

import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;
import static ratpack.jdbctx.Transaction.connection;

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
        dataSource.setAutoCommit(false);
        return dataSource;
    }

    @Provides
    @Singleton
    public RetryPolicy<Promise> retryPolicy(DatabaseProperties databaseProperties) {
        return new RetryPolicy<Promise>()
                .withMaxAttempts(databaseProperties.getRetryCount())
                .withDelay(ofSeconds(databaseProperties.getRetryIntervalSeconds()))
                .withJitter(ofMillis(databaseProperties.getRetryJitterMillis()))
                .onFailure(completedEvent -> {
                    throw new DatabaseException("Operation failed", completedEvent.getFailure());
                });
    }

    @Provides
    @Singleton
    public TransactionWrapper transactionWrapper(DataSource dataSource, RetryPolicy<Promise> retryPolicy) {
        return new TransactionWrapper(dataSource, retryPolicy);
    }

    @Provides
    @Singleton
    public SQLQueryFactory queryFactory() {
        Configuration configuration = new Configuration(MySQLTemplates.DEFAULT);
        return new SQLQueryFactory(configuration, connectionProvider());
    }

    private Provider<Connection> connectionProvider() {
        return () -> connection()
                .orElseThrow(() -> new DatabaseException("No active transaction"));
    }

}
