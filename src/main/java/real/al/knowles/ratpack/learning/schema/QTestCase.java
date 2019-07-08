package real.al.knowles.ratpack.learning.schema;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QTestCase is a Querydsl query type for QTestCase
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QTestCase extends com.querydsl.sql.RelationalPathBase<QTestCase> {

    private static final long serialVersionUID = 1001810612;

    public static final QTestCase testCase = new QTestCase("test_case");

    public final DateTimePath<java.sql.Timestamp> createdOn = createDateTime("createdOn", java.sql.Timestamp.class);

    public final StringPath externalId = createString("externalId");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final NumberPath<Long> projectId = createNumber("projectId", Long.class);

    public final StringPath purpose = createString("purpose");

    public final DateTimePath<java.sql.Timestamp> updatedOn = createDateTime("updatedOn", java.sql.Timestamp.class);

    public final com.querydsl.sql.PrimaryKey<QTestCase> primary = createPrimaryKey(id);

    public final com.querydsl.sql.ForeignKey<QProject> testCaseIbfk1 = createForeignKey(projectId, "id");

    public QTestCase(String variable) {
        super(QTestCase.class, forVariable(variable), "null", "test_case");
        addMetadata();
    }

    public QTestCase(String variable, String schema, String table) {
        super(QTestCase.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QTestCase(String variable, String schema) {
        super(QTestCase.class, forVariable(variable), schema, "test_case");
        addMetadata();
    }

    public QTestCase(Path<? extends QTestCase> path) {
        super(path.getType(), path.getMetadata(), "null", "test_case");
        addMetadata();
    }

    public QTestCase(PathMetadata metadata) {
        super(QTestCase.class, metadata, "null", "test_case");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdOn, ColumnMetadata.named("created_on").withIndex(6).ofType(Types.TIMESTAMP).withSize(23).notNull());
        addMetadata(externalId, ColumnMetadata.named("external_id").withIndex(2).ofType(Types.VARCHAR).withSize(50).notNull());
        addMetadata(id, ColumnMetadata.named("id").withIndex(1).ofType(Types.BIGINT).withSize(20).notNull());
        addMetadata(name, ColumnMetadata.named("name").withIndex(4).ofType(Types.VARCHAR).withSize(190).notNull());
        addMetadata(projectId, ColumnMetadata.named("project_id").withIndex(3).ofType(Types.BIGINT).withSize(20).notNull());
        addMetadata(purpose, ColumnMetadata.named("purpose").withIndex(5).ofType(Types.VARCHAR).withSize(100).notNull());
        addMetadata(updatedOn, ColumnMetadata.named("updated_on").withIndex(7).ofType(Types.TIMESTAMP).withSize(23).notNull());
    }

}

