package real.al.knowles.ratpack.learning.schema;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QProject is a Querydsl query type for QProject
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QProject extends com.querydsl.sql.RelationalPathBase<QProject> {

    private static final long serialVersionUID = 2115320071;

    public static final QProject project = new QProject("project");

    public final DateTimePath<java.sql.Timestamp> createdOn = createDateTime("createdOn", java.sql.Timestamp.class);

    public final StringPath externalId = createString("externalId");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.sql.Timestamp> updatedOn = createDateTime("updatedOn", java.sql.Timestamp.class);

    public final com.querydsl.sql.PrimaryKey<QProject> primary = createPrimaryKey(id);

    public final com.querydsl.sql.ForeignKey<QTestCase> _testCaseIbfk1 = createInvForeignKey(id, "project_id");

    public QProject(String variable) {
        super(QProject.class, forVariable(variable), "null", "project");
        addMetadata();
    }

    public QProject(String variable, String schema, String table) {
        super(QProject.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QProject(String variable, String schema) {
        super(QProject.class, forVariable(variable), schema, "project");
        addMetadata();
    }

    public QProject(Path<? extends QProject> path) {
        super(path.getType(), path.getMetadata(), "null", "project");
        addMetadata();
    }

    public QProject(PathMetadata metadata) {
        super(QProject.class, metadata, "null", "project");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdOn, ColumnMetadata.named("created_on").withIndex(3).ofType(Types.TIMESTAMP).withSize(23).notNull());
        addMetadata(externalId, ColumnMetadata.named("external_id").withIndex(2).ofType(Types.VARCHAR).withSize(25).notNull());
        addMetadata(id, ColumnMetadata.named("id").withIndex(1).ofType(Types.BIGINT).withSize(20).notNull());
        addMetadata(updatedOn, ColumnMetadata.named("updated_on").withIndex(4).ofType(Types.TIMESTAMP).withSize(23).notNull());
    }

}

