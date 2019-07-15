package real.al.knowles.ratpack.learning.project;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.querydsl.sql.SQLQueryFactory;
import real.al.knowles.ratpack.learning.database.TransactionWrapper;

public class ProjectModule extends AbstractModule {

    @Override
    protected void configure() {

    }

    @Provides
    @Singleton
    public ProjectRepository projectRepository(SQLQueryFactory queryFactory) {
        return new ProjectRepository(queryFactory);
    }

    @Provides
    @Singleton
    public ProjectService projectService(ProjectRepository projectRepository, TransactionWrapper transactionWrapper) {
        return new ProjectService(projectRepository, transactionWrapper);
    }

    @Provides
    @Singleton
    public ProjectChain projectChain(ProjectService projectService) {
        return new ProjectChain(projectService);
    }

}
