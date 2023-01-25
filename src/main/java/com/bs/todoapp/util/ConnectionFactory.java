package com.bs.todoapp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Bruno Soares
 */
public class ConnectionFactory {
    
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String URL = "jdbc:mysql://localhost:3306/todoapp";
    public static final String USER = "root";
    public static final String PASS = "";
    
    public static Connection getConnection() {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException("DB connection failed", ex);
        }
    }
    
    public static void closeConnection(Connection con) {
        try {
            if(con != null){
                con.close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Error at closing DB connection");
        }
    }
    
}
