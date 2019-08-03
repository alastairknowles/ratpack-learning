package real.al.knowles.ratpack.learning.database;

import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.Connection;
import java.sql.SQLException;

class DefaultSupportingDataSource extends MariaDbDataSource {

    private final boolean readOnlyDefault;

    private final boolean autoCommitSupport;

    DefaultSupportingDataSource(String url, boolean readOnlyDefault, boolean autoCommitSupport) {
        super(url);
        this.readOnlyDefault = readOnlyDefault;
        this.autoCommitSupport = autoCommitSupport;
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection connection = super.getConnection();
        return toReadConnection(connection);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        Connection connection = super.getConnection(username, password);
        return toReadConnection(connection);
    }

    private Connection toReadConnection(Connection connection) throws SQLException {
        connection.setReadOnly(readOnlyDefault);
        connection.setAutoCommit(autoCommitSupport);
        return connection;
    }

}
