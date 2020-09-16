package sample.jdbc;

import sample.Setting;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManage {
    private static String DRIVER     = "com.mysql.cj.jdbc.Driver";
    private static String DBURL      = Setting.getdbUrl();
    private static String dbLogin    = Setting.getdbLogin();
    private static String dbPassword = Setting.getdbPass();
    //-------------------------------------------------------------------------
    public static Connection getDBConnect() {
        Connection conn = null;
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("DBManage; Connection ClassNotFoundException; MSG: " + e.getMessage());
        }
        try {
            conn = DriverManager.getConnection(DBURL, dbLogin, dbPassword);
        } catch (SQLException e) {
            System.out.println("DBManage; Connection; MSG: " + e.getMessage());
        }
        return conn;
    }
}
