package client;

import com.google.gson.Gson;
import interfaces.Observer;
import lombok.SneakyThrows;
import server.MyServer;
import utility.DataSource;
import utility.JDBCUtils;
import utility.Message;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;

import static utility.JDBCUtils.*;


public class ClientEntity implements Runnable, Observer {

    private Client client;
    private Socket socket;
    private MyServer server;
    private static int clients_count = 0;
    private PrintWriter outMessage;
    private InputStreamReader inMessage;

    @SneakyThrows
    public ClientEntity(Socket socket, MyServer server) {
        clients_count++;
        this.socket = socket;
        this.server = server;
        this.outMessage = new PrintWriter(socket.getOutputStream());
        this.inMessage = new InputStreamReader(socket.getInputStream());
    }

    @SneakyThrows
    @Override
    public void run() {
        String clientMessage;
        BufferedReader clientReader = new BufferedReader(inMessage);
        while (true) {
            server.notifyObservers("New member has joined the chat!");
            server.notifyObservers("members in chat = " + clients_count);
            break;
        }
        while ((clientMessage = clientReader.readLine()) != null) {
            if (clientMessage.startsWith("{")) {
                Gson gson = new Gson();
                client = gson.fromJson(clientMessage, Client.class);
                System.out.println("client = " + client);
                try (Connection c = DataSource.getConnection()) {
                    Client myClient = JDBCUtils.selectClientFromDB(c, client.getUserName());
                    System.out.println("myClient = " + myClient);
                    if (myClient != null) {
                        if (myClient.getUserName().equals(client.getUserName())) {
                            if (myClient.getHashPass().equals(client.getHashPass())) {
                                System.out.println(client + " есть в базе");
                                client = myClient;
                                server.addObserver(this);
                                String jsonClient = gson.toJson(client);
                                SendMessage(jsonClient);
                                System.out.println("client = " + jsonClient);
                            } else {
                                System.out.println(" неправильная пара логин - пароль! ");
                                SendMessage(" неправильная пара логин - пароль! ");
                            }
                        }
                        }else {
                        System.out.println(" добавляем нового клиента ");

                            int id = JDBCUtils.insertClientIntoDB(c, client.getUserName(), client.getHashPass());
                            client.setUserId(id);
                        server.addObserver(this);
                        String jsonClient = gson.toJson(client);
                        SendMessage(jsonClient);
                        System.out.println("client = " + jsonClient);
                    }

                }
                server.notifyObservers("  "+ client.getUserName() + ": вошел в чат !");
            } else {
                if (clientMessage.equalsIgnoreCase("Exit")) {
                    SendMessage("Exit");
                    server.notifyObservers(client.getUserName() + " stop session!");
                    close();
                    System.out.println("Client " + client.getUserName() + ":  " + " stop session!");
                    break;
                } else if (client != null) {
                    System.out.println("Client " + client.getUserName() + ":  " + clientMessage);
                    Message message = new Message(clientMessage);
                    try (Connection c = DataSource.getConnection()) {
                        int idMessage = JDBCUtils.insertMessageIntoDB(c, clientMessage, client.getUserId(), -1);
                        message.setIdMessage(idMessage);
                    }
                    server.notifyObservers(client.getUserName() + ": " + clientMessage);
                }

            }
        }
    }

    @SneakyThrows
    @Override
    public void notifyObservers(String message) {
        BufferedWriter writer = new BufferedWriter(outMessage);
        writer.write(message);
        writer.newLine();
        writer.flush();

    }

    @SneakyThrows
    private void SendMessage(String message) {
        BufferedWriter writer = new BufferedWriter(outMessage);
        writer.write(message);
        writer.newLine();
        writer.flush();
    }

    public void close() {
        server.stopObserver(this);
        clients_count--;
        server.notifyObservers("members in chat = " + clients_count);
    }
}