package utility;

import client.Client;

import java.sql.*;

public class JDBCUtils {
    public static Integer insertMessageIntoDB(Connection c, String message, int idUser, int idRecipient ) throws SQLException {
        return insert(c, "INSERT INTO message (Message_Text, idUser, idRecipient) values (?, ?,?);", new ResultSetHandler<Integer>() {
            @Override
            public Integer handle(ResultSet rs) throws SQLException {
                int inserted = -1;
                if (rs.next()) {
                    inserted = rs.getInt(1);
                }
                return inserted;
            }
        }, message, idUser, idRecipient);
    }

    public static Integer insertClientIntoDB(Connection c, String name, String hashPass) throws SQLException {
        return insert(c, "INSERT INTO users (NAME,PASSWORD) values (?, ?);",new ResultSetHandler<Integer>(){
            @Override
            public Integer handle(ResultSet rs) throws SQLException {
                int inserted = -1;
                if(rs.next()) {
                    inserted = rs.getInt(1);
                }
                return inserted;
            }
        }, name, hashPass);
    }

    public static Client selectClientFromDB(Connection c, String name) throws SQLException {
        return select(c, "select * from users where NAME = ?; ",
                new ResultSetHandler<Client>() {
                    @Override
                    public Client handle(ResultSet rs) throws SQLException {
                        if (rs.next()) {
                            Client client = new Client();
                            client.setUserId(rs.getInt(1));
                            client.setUserName(rs.getString(2));
                            client.setHashPass(rs.getString(4));
                            return client;
                        } else
                            return null;
                    }
                }, name);
    }

    public static Client selectClientFromDB(Connection c, int idUser) throws SQLException {
        return select(c, "select * from users where idUser = ?; ",
                new ResultSetHandler<Client>() {
                    @Override
                    public Client handle(ResultSet rs) throws SQLException {
                        if (rs.next()) {
                            Client client = new Client();
                            client.setUserId(rs.getInt(1));
                            client.setUserName(rs.getString(2));
                            client.setHashPass(rs.getString(4));
                            return client;
                        } else
                            return null;
                    }
                }, idUser);
    }

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