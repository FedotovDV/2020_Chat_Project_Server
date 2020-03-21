
import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer  implements Observable{

    public final static int PORT = 8290;
    private volatile  List<Observer> clients = new ArrayList<>();

    public void start() {
        Socket socket = null;

        try (ServerSocket serverSocket = new ServerSocket(PORT)){
            System.out.println("==START SERVER==");
            while (true) {
                if (socket == null) {
                    socket = serverSocket.accept();
                } else {
                    ClientEntity clientEntity = new ClientEntity(socket, this);
                    new Thread(clientEntity).start();
                    socket = null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addObserver(Observer o) {
        clients.add(o);
    }

    @Override
    public void stopObserver(Observer o) {
       clients.remove(o);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer client : clients) {
            client.notifyObservers(message);
        }
    }
}


//    if (socket != null) {
////                    ObjectInputStream clientReader = new ObjectInputStream(socket.getInputStream());
////                    Client user1 = (Client) clientReader.readObject();
////                    System.out.println(user1);
//
//                    BufferedReader clientReader =
//                            new BufferedReader(new InputStreamReader(socket.getInputStream()));
////                    String clientMessage = clientReader.readLine();
////                    System.out.println(clientMessage);
//                    Gson gson = new Gson();
//                    String json = clientReader.readLine();
//                    Client client = gson.fromJson(json, Client.class);
//                    System.out.println(client.toString());
//                }