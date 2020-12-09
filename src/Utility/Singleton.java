package Utility;

import java.sql.Connection;

import java.sql.*;

public class Singleton implements Credentials {

    private static Connection conn;
    static {
        if (conn == null) {
            try {
                Class.forName(Credentials.driver);
                conn = DriverManager.getConnection(Credentials.url, Credentials.user, Credentials.passws);
                System.out.println("Connection Established to "+Credentials.sgbd_name);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static Connection getConn() {
        return conn;
    }

}
