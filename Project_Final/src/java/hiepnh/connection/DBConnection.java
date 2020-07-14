package hiepnh.connection;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;


public class DBConnection implements Serializable {

    public static Connection getMyConnection(){
        Connection conn=null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn=DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=XML_Project", "sa", "123456");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}

