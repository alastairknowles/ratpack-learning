package real.al.knowles.ratpack.learning.project;

import com.querydsl.sql.SQLQueryFactory;

import javax.inject.Inject;

public class ProjectRepository {

    private final SQLQueryFactory queryFactory;

    @Inject
    public ProjectRepository(SQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }



}
