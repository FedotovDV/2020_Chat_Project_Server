import lombok.SneakyThrows;

import java.io.OutputStreamWriter;
import java.net.*;

public class MyServer2 {

    @SneakyThrows
    public static void main(String[] args) {

        ServerSocket serverSocket = new ServerSocket(8300);
        System.out.println("==START SERVER==");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            OutputStreamWriter writer = new OutputStreamWriter(clientSocket.getOutputStream());
                      writer.write(
                    "HTTP/1.0 200 OK\r\n" +
                            "Content-type: text/html\r\n" +
                            "\r\n" +
                            "<h2>Hello World</h2>\r\n");
            writer.flush();
            writer.close();
            clientSocket.close();
        }
    }
}
