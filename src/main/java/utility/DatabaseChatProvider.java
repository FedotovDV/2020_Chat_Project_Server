package utility;

import client.Client;
import utility.JDBCUtils;
import interfaces.ChatProvider;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DatabaseChatProvider implements ChatProvider {


    @Override
    public long countAllClient() throws SQLException {
        try (Connection c = DataSource.getConnection()) {

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Client> findAllCLient(int offset, int limit) {
        return null;
    }

    @Override
    public int createClient(Client client) {
        try (Connection c = DataSource.getConnection()) {
            client.setUserId(JDBCUtils.insert(c, "INSERT INTO users (NAME,PASSWORD) values (?, ?);", new ResultSetHandler<Integer>() {
                @Override
                public Integer handle(ResultSet rs) throws SQLException {
                    int inserted = -1;
                    if (rs.next()) {
                        inserted = rs.getInt(1);
                    }
                    return inserted;
                }
            }, client.getUserName(), client.getHashPass()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client.getUserId();
    }

    @Override
    public void updateClient(Client client) {
    }

    @Override
    public void deleteClient(Client client) {

    }

    @Override
    public void deleteClientById(int id) {

    }

    @Override
    public Client findByName(Client client) {
        try (Connection c = DataSource.getConnection()) {
            client = JDBCUtils.select(c, "select * from users where NAME = ?; ",
                    new ResultSetHandler<Client>() {
                        @Override
                        public Client handle(ResultSet rs) throws SQLException {
                            if (rs.next()) {
                                Client client = new Client();
                                client.setUserId(rs.getInt(1));
                                client.setUserName(rs.getString(2));
                                client.setUserEmail(rs.getString(3));
                                client.setHashPass(rs.getString(4));
                                return client;
                            } else
                                return null;
                        }
                    }, client.getUserName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    @Override
    public Client findById(Client client) {
        try (Connection c = DataSource.getConnection()) {
            client = JDBCUtils.select(c, "select * from users where idUser = ?; ",
                    new ResultSetHandler<Client>() {
                        @Override
                        public Client handle(ResultSet rs) throws SQLException {
                            if (rs.next()) {
                                Client client = new Client();
                                client.setUserId(rs.getInt(1));
                                client.setUserName(rs.getString(2));
                                client.setUserEmail(rs.getString(3));
                                client.setHashPass(rs.getString(4));
                                return client;
                            } else
                                return null;
                        }
                    }, client.getUserId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    @Override
    public List<Message> findAllMessage(int offset, int limit) {
        return null;
    }

    @Override
    public int createMessage(Message message) {
        try (Connection c = DataSource.getConnection()) {
            message.setIdMessage(JDBCUtils.insert(c, "INSERT INTO message (Message_Text, idUser, idRecipient) values (?, ?,?);", new ResultSetHandler<Integer>() {
                @Override
                public Integer handle(ResultSet rs) throws SQLException {
                    int inserted = -1;
                    if (rs.next()) {
                        inserted = rs.getInt(1);
                    }
                    return inserted;
                }
            }, message.getMessageText(), message.getIdUser(), message.getIdRecipient()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return message.getIdMessage();
    }

    @Override
    public void updateMessage(Message message) {

    }

    @Override
    public void deleteMessage(Message message) {

    }

    @Override
    public void deleteMessageById(int id) {

    }
}
