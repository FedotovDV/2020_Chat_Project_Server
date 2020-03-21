import com.google.gson.Gson;
import lombok.SneakyThrows;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
            if (clientMessage.startsWith("#$#pass#")) {
                String[] logPass = clientMessage.split("/");
                Gson gson = new Gson();
                client = gson.fromJson(logPass[1], Client.class);
                client.setUserPassword(logPass[2]);
                System.out.println(client.toString());
//            System.out.println(client.toString());
//
//            if (clientMessage.startsWith("REGISTRATION")) {
//                String[] logPass = clientMessage.substring(12).split(":");
//                client = new Client(logPass[0], logPass[1].toCharArray());
//                System.out.println("New client connected: " +
//                        logPass[0] + " " + logPass[1]);
                server.addObserver(this);
                server.notifyObservers(client.getUserName() + ": " + clientMessage);
            } else {
                if (clientMessage.equalsIgnoreCase("Exit")) {
                    server.notifyObservers(client.getUserName() + " stop session!");
                    server.stopObserver(this);
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

}
