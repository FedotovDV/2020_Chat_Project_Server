package interfaces;

import client.Client;
import utility.Message;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ChatProvider {

    long countAllClient() throws SQLException;

    List<Client> findAllCLient(int offset, int limit);

    int createClient(Client client);

    void updateClient(Client client);

    void deleteClient(Client client);

    void deleteClientById(int id);

    Client findByName(Client client);

    Client findById(Client client);

    List<Message> findAllMessage(int offset, int limit);

    int createMessage(Message message);

    void updateMessage(Message message);

    void deleteMessage(Message message);

    void deleteMessageById(int id);
}
