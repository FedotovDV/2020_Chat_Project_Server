package utility;

import interfaces.ResultSetHandler;

import java.sql.*;

public class JDBCUtils {


    public static <T> T select(Connection c, String sql, ResultSetHandler<T> resultSetHandler, Object... parameters)
            throws SQLException {
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            populatePreparedStatement(ps, parameters);
            try (ResultSet rs = ps.executeQuery()) {
                return resultSetHandler.handle(rs);
            }
        }
    }

    public static <T> T insert(Connection c, String sql, ResultSetHandler<T> resultSetHandler, Object... parameters)
            throws SQLException {
        try (PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            populatePreparedStatement(ps, parameters);
            int result = ps.executeUpdate();
            if (result != 1) {
                throw new SQLException("Can't insert row to database. Result=" + result);
            }
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return resultSetHandler.handle(rs);
            }
        }
    }

    public static int executeUpdate(Connection c, String sql, Object... parameters) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            populatePreparedStatement(ps, parameters);
            return ps.executeUpdate();
        }
    }

    private static void populatePreparedStatement(PreparedStatement ps, Object... parameters) throws SQLException {
        if (parameters != null) {
            for (int i = 0; i < parameters.length; i++) {
                ps.setObject(i+1, parameters[i]);
            }
        }
    }
}