
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {

    public final static int PORT = 8290;

    public void start() {

        System.out.println("====Server starts====");
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                if (socket != null) {
                    ObjectInputStream clientReader = new ObjectInputStream(socket.getInputStream());
                    Client user1 = (Client) clientReader.readObject();
                    System.out.println(user1);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
