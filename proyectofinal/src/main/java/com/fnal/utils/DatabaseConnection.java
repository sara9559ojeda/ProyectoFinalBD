package com.fnal.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3307/postventa1";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    private static Connection myConnection;

    public static Connection getInstance()throws SQLException{
        if(myConnection == null){
            myConnection = DriverManager.getConnection(URL, USER,PASSWORD);

        }
        return myConnection;
    }

}
