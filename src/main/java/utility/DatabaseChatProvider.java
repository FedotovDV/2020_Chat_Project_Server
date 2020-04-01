package utility;

import client.Client;
import interfaces.ChatProvider;

import java.util.List;

public class DatabaseChatProvider implements ChatProvider {
    @Override
    public long countAllClient() {
        return 0;
    }

    @Override
    public List<Client> findAllCLient(int offset, int limit) {
        return null;
    }

    @Override
    public void createClient(Client client) {

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
    public Client findByName(String name) {
        return null;
    }

    @Override
    public Client findById(String name) {
        return null;
    }

    @Override
    public List<Message> findAllMessage(int offset, int limit) {
        return null;
    }

    @Override
    public void createMessage(Message message) {

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
