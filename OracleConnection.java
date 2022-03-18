

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Properties;
    public class OracleConnection {


        public static Connection connect() throws SQLException {
            try {
                Connection res = DriverManager.getConnection("jdbc:oracle:thin:usrnme/password@oracle12c.scs.ryerson.ca:1521:orcl12c");
                if (res != null) {
                    System.out.println("Connection working");
                } else {
                    System.out.println("Failed to make connection!");
                }
                return res;
            } catch (SQLException e) {
                System.out.println("Connection Failed! Check output console");
                e.printStackTrace();
                throw e;
            }
        }




}
