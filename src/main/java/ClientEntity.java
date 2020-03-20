import com.google.gson.Gson;
import lombok.SneakyThrows;

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
        BufferedReader clientReader = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        while (true) {

            String clientMessage = clientReader.readLine();
            if(clientMessage!=null) {
                if (clientMessage.startsWith("REGISTRATION")) {
                    String[] logPass = clientMessage.substring(12).split(":");
                    client = new Client(logPass[0], logPass[1].toCharArray());
                    System.out.println("New client connected: " +
                            logPass[0] + " " + logPass[1]);
                    server.addObserver(this);
                    server.notifyObservers(client.getUserName() + ": " + clientMessage);
                } else {
                    System.out.println(clientMessage);
                    server.notifyObservers(client.getUserName() + ": " + clientMessage);
                }
            }
        }
    }
    @SneakyThrows
    @Override
    public void notifyObservers(String message) {
        BufferedWriter writer = new BufferedWriter( new PrintWriter(socket.getOutputStream()));
        writer.write(message);
        writer.newLine();
        writer.flush();

    }

//    public boolean registration(){
//
//    }
}
