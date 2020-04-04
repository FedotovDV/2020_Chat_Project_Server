package client;

import com.google.gson.Gson;
import interfaces.ChatProvider;
import interfaces.Observer;
import jdk.nashorn.internal.runtime.regexp.joni.ScanEnvironment;
import lombok.SneakyThrows;
import server.MyServer;
import utility.*;


import java.io.*;
import java.net.Socket;
import java.sql.Connection;


import lombok.extern.log4j.Log4j;

@Log4j
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
            server.notifyObservers(TypeMessage.MESSAGE.name()+"New member has joined the chat!");
            server.notifyObservers(TypeMessage.INFO.name()+"Members in chat = " + clients_count);
            break;
        }
        while ((clientMessage = clientReader.readLine()) != null) {

            if (clientMessage.startsWith(TypeMessage.LOGIN.name())) {
                log.info("clientMessage = " + clientMessage);
                Gson gson = new Gson();
                String jsonClient = clientMessage.replaceFirst(TypeMessage.LOGIN.name(), "");
                client = gson.fromJson(jsonClient, Client.class);
                log.info("client = " + client);
                Client myClient = new DatabaseChatProvider().findByName(client);
                log.info("myClient = " + myClient);
                if (myClient != null) {
                    if (myClient.getUserName().equals(client.getUserName())) {
                        if (myClient.getHashPass().equals(client.getHashPass())) {
                            log.info(client + " есть в базе");
                            client = myClient;
                            server.addObserver(this);
                            String sendJson = TypeMessage.LOGIN.name();
                            jsonClient = gson.toJson(client);
                            sendJson += jsonClient;
                            SendMessage(sendJson);
                            log.info("jsonClient = " + jsonClient);
                        } else {
                            log.info(" неправильная пара логин - пароль! ");
                            SendMessage(TypeMessage.WRONGPASS.name());
                        }
                    } else {
                        log.info(" добавляем нового клиента ");
                        int id = new DatabaseChatProvider().createClient(client);
                        client.setUserId(id);
                        server.addObserver(this);
                        String sendJson = TypeMessage.LOGIN.name();
                        jsonClient = gson.toJson(client);
                        sendJson += jsonClient;
                        SendMessage(sendJson);
                        log.info("client = " + jsonClient);
                    }
                }
                log.info("send message to all client  " + client.getUserName() + ": вошел в чат !");
                server.notifyObservers(TypeMessage.MESSAGE.name()+"  " + client.getUserName() + ": вошел в чат !");
            } else if (clientMessage.equalsIgnoreCase(TypeMessage.LOGOUT.name())) {
                    SendMessage(TypeMessage.LOGOUT.name());
                server.notifyObservers(TypeMessage.MESSAGE.name()+client.getUserName() + " stop session!");
                close();
                log.info("Client " + client.getUserName() + ":  " + " stop session!");
                break;

            } else if (clientMessage.startsWith(TypeMessage.MESSAGE.name())) {
                clientMessage = clientMessage.replaceFirst(TypeMessage.MESSAGE.name(), "");
                log.info("Client send message " + client.getUserName() + ":  " + clientMessage);
                Message message = new Message(clientMessage, client);
                int idMessage = new DatabaseChatProvider().createMessage(message);
                message.setIdMessage(idMessage);
                log.info("the message is recorded in the database :" + message);
                String sendMessage = TypeMessage.MESSAGE.name()+ client.getUserName() + ": " + clientMessage;
                server.notifyObservers(sendMessage);
            }
        }


    }


    @SneakyThrows
    @Override
    public void notifyObservers(String message) {
        SendMessage(message);
    }

    @SneakyThrows
    private void SendMessage(String message) {
        BufferedWriter writer = new BufferedWriter(outMessage);
        writer.write(message);
        writer.newLine();
        writer.flush();
    }

    private void close() {
        server.stopObserver(this);
        clients_count--;
        server.notifyObservers("members in chat = " + clients_count);
    }
}