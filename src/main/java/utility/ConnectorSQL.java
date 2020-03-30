package utility;

import client.Client;

import java.sql.*;


public class ConnectorSQL {


    public static void main(String[] args) throws SQLException {

//
////        try (Connection c = DataSource.getConnection()) {


////            try (PreparedStatement ps = c.prepareStatement("INSERT INTO users (NAME,PASSWORD) values (?, ?)")) {
////                ps.setString(1, "Jack");
////                ps.setString(2, "55555");
////                int inserted = ps.executeUpdate();
////                System.out.println("Inserted " + inserted + " row");
////            }
////    }
//
        int idBD;
        Client client;
        try (Connection c = DataSource.getConnection()) {
//           idBD = insertClientIntoDB(c);
            client = selectClientFromDB(c);
            System.out.println(client);
        }
//        System.out.println(idBD);
    }

    //
    private static Client selectClientFromDB(Connection c) throws SQLException {
        return JDBCUtils.select(c, "select * from users where NAME = ?; ",
                new ResultSetHandler<Client>() {
                    @Override
                    public Client handle(ResultSet rs) throws SQLException {
                        if (rs.next()) {
                            Client client = new Client();
                            client.setUserName(rs.getString(2));
                            client.setHashPass(rs.getString(4));
                            return client;
                        } else
                            return null;
                    }
                }, "user");
    }
//
//    private static Integer insertClientIntoDB(Connection c) throws SQLException {
//        return JDBCUtils.insert(c, "INSERT INTO users (NAME,PASSWORD) values (?, ?);",new ResultSetHandler<Integer>(){
//             @Override
//             public Integer handle(ResultSet rs) throws SQLException {
//                 int inserted = -1;
//                if(rs.next()) {
//                    inserted = rs.getInt(1);
//                }
//                 return inserted;
//             }
//         }, "Petr2", "122345");
//    }
//
//
////    try (Connection c = DataSource.getConnection()){
////                    Client myClient = JDBCUtils.selectClientFromDB(c, client.getUserName());
////                    if(myClient!=null){
////                        if(myClient.getHashPass() == client.getHashPass()){
////                            System.out.println(myClient.getHashPass() == client.getHashPass());
////                        }
////
}

