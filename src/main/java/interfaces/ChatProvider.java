package interfaces;

import client.Client;
import utility.Message;

import java.sql.Connection;
import java.util.List;

public interface ChatProvider {

    long countAllClient();

    List<Client> findAllCLient(int offset, int limit);

    void createClient(Client client);

    void updateClient(Client client);

    void deleteClient(Client client);

    void deleteClientById(int id);

    Client findByName( String name);

    Client findById( String name);

    List<Message> findAllMessage(int offset, int limit);

    void createMessage(Message message);

    void updateMessage(Message message);

    void deleteMessage(Message message);

    void deleteMessageById(int id);
}
