package me.xdf.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBUtil {
    private static final String JDBC_URL =  "jdbc:mysql://127.0.0.1:3306/User_Database?useSSL=false&serverTimezone=GMT";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "1234";
    private DBUtil() {
    }
    // 获取数据库连接
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }
    // 关闭数据库连接
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}