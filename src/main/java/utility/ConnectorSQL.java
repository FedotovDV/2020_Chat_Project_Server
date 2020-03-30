package utility;

import java.sql.*;
import java.util.*;

public class ConnectorSQL {


    public static void main(String[] args) throws SQLException {

        try (Connection c = DataSource.getConnection()){
            Map<String, Object> map = JDBCUtils.select(c, "select * from users",
                    new ResultSetHandler<Map<String, Object>>() {
                        @Override
                        public Map<String, Object> handle(ResultSet rs) throws SQLException {
                            if (rs.next()) {
                                Map<String, Object> map = new LinkedHashMap<>();
                                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                                    map.put(rs.getMetaData().getColumnLabel(i), rs.getObject(i));
                                }
                                return map;
                            } else
                                return Collections.emptyMap();
                        }
                    });
            System.out.println(map);
        }

    }

}
