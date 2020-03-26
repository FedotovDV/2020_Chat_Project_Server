import com.google.gson.Gson;
import interfaces.Observer;
import lombok.SneakyThrows;

import java.io.*;
import java.net.Socket;


public class ClientEntity implements Runnable, Observer {

    private Client client;
    private Socket socket;
    private MyServer server;


    public ClientEntity(Socket socket, MyServer server) {
        this.socket = socket;
        this.server = server;
    }

    @SneakyThrows
    @Override
    public void run() {
        String clientMessage;
        BufferedReader clientReader = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        while ((clientMessage = clientReader.readLine()) != null) {
            if (clientMessage.startsWith("{")) {
                Gson gson = new Gson();
                client = gson.fromJson(clientMessage, Client.class);
                server.addObserver(this);
                server.notifyObservers(client.getUserName() + ": " + clientMessage);
            } else {
                if (clientMessage.equalsIgnoreCase("Exit")) {
                    this.SendMessage("Exit");
                    server.notifyObservers(client.getUserName() + " stop session!");
                    server.stopObserver(this);
                    System.out.println("Client " + client.getUserName() + ":  " + " stop session!");
                    break;
                }

                System.out.println("Client " + client.getUserName() + ":  " + clientMessage);
                server.notifyObservers(client.getUserName() + ": " + clientMessage);
            }
        }
    }

    @SneakyThrows
    @Override
    public void notifyObservers(String message) {
        BufferedWriter writer = new BufferedWriter(new PrintWriter(socket.getOutputStream()));
        writer.write(message);
        writer.newLine();
        writer.flush();

    }
    @SneakyThrows
    private void SendMessage(String message) {
        BufferedWriter writer = new BufferedWriter(new PrintWriter(socket.getOutputStream()));
        writer.write(message);
        writer.newLine();
        writer.flush();
    }

}
