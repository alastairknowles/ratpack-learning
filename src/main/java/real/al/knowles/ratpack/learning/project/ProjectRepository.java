package real.al.knowles.ratpack.learning.project;

import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.dml.SQLInsertClause;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.Optional;

import static com.querydsl.core.types.Projections.constructor;
import static real.al.knowles.ratpack.learning.schema.QProject.project;

public class ProjectRepository {

    private final SQLQueryFactory queryFactory;

    @Inject
    public ProjectRepository(SQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public Long createProject(ProjectRepresentation projectRepresentation) {
        SQLInsertClause insert = queryFactory.insert(project);
        insert.set(project.externalId, projectRepresentation.getExternalId());
        insert.set(project.createdOn, Timestamp.valueOf(projectRepresentation.getCreatedOn()));
        insert.set(project.updatedOn, Timestamp.valueOf(projectRepresentation.getUpdatedOn()));
        return insert.executeWithKey(project.id);
    }

    public Optional<ProjectRepresentation> getProject(Long id) {
        return Optional.ofNullable(
                queryFactory.select(
                        constructor(
                                ProjectRepresentation.class,
                                project.id,
                                project.externalId,
                                project.createdOn,
                                project.updatedOn))
                        .from(project)
                        .where(project.id.eq(id))
                        .fetchFirst());
    }

}
