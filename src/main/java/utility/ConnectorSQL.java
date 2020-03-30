package utility;

import client.MyClient;

import java.sql.*;
import java.util.*;


public class ConnectorSQL {


    public static void main(String[] args) throws SQLException {


//        try (Connection c = DataSource.getConnection()) {
//            try (PreparedStatement ps = c.prepareStatement("INSERT INTO users (NAME,PASSWORD) values (?, ?)")) {
//                ps.setString(1, "Jack");
//                ps.setString(2, "55555");
//                int inserted = ps.executeUpdate();
//                System.out.println("Inserted " + inserted + " row");
//            }
//    }

        int idBD;
        MyClient client;
        try (Connection c = DataSource.getConnection()){
//           idBD =  JDBCUtils.insert(c, "INSERT INTO users (NAME,PASSWORD) values (?, ?);",new ResultSetHandler<Integer>(){
//                @Override
//                public Integer handle(ResultSet rs) throws SQLException {
//                    int inserted = -1;
//                   if(rs.next()) {
//                       inserted = rs.getInt(1);
//                   }
//                    return inserted;
//                }
//            }, "Petr2", "122345");


           client = JDBCUtils.select(c, "select * from users where NAME = ?; ",
                    new ResultSetHandler<MyClient>() {
                        @Override
                        public MyClient handle(ResultSet rs) throws SQLException {
                            if (rs.next()) {
                                MyClient client = new MyClient();
                                client.setUserName(rs.getString(2));
                                client.setHashPass(rs.getString(4));
                                return client;
                            } else
                                return null;
                        }
                    }, "Ivan");
            System.out.println(client);
        }
//        System.out.println(idBD);
    }

}
