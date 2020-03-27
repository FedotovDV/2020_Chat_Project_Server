package utility;

import java.sql.*;

public class ConnectorSQL {
    private static Connection connectionSQL  = null;
    private static String username = "root";
    private static String password = "root";
    private static String URL = "jdbc:MySQL://localhost:3306/chat_schema?serverTimezone=UTC";
    private static String sqlInsertUser ="INSERT INTO users(name, E_mail, Password) values(%s, %s, %s);";

    public static void main(String[] args) throws SQLException
    {

        connectionSQL = DriverManager.getConnection(URL, username, password);

        if(connectionSQL != null)
            System.out.println("Connection Successful !\n");
        else
            System.exit(0);
        try (Statement st = connectionSQL.createStatement()) {


            PreparedStatement resultSet = connectionSQL.prepareStatement(sqlInsertUser);
            final int i1 = resultSet.executeUpdate();
            ResultSet rs = st.executeQuery("select * from users");
            int columns = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columns; i++) {
                    System.out.print(rs.getString(i) + "\t");
                }
                System.out.println();
            }
            System.out.println();
            rs.close();
            st.close();
        }
        if(connectionSQL != null)
            connectionSQL.close();
    }
}