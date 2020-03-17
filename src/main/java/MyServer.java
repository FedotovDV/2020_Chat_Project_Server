
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
                    BufferedReader clientReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String clientMessage = clientReader.readLine();
                    System.out.println(clientMessage);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
