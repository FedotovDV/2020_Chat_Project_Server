import java.sql.*;

public class Main {

    public static void main(String[] args) {
        MyServer myServer = new MyServer();
        myServer.start();

//        try (Connection connectionSQL = DriverManager.getConnection(
//                "jdbc:MySQL://localhost:3306/chat_schema?serverTimezone=UTC",
//                "root", "root")) {
//            String SQL = "   ";
//            ResultSet resultSet = connectionSQL.prepareStatement(SQL);
//
//        } catch (Exception e) {
//            System.out.println(e.getStackTrace());
//        }
    }
}