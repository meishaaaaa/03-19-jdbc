package com.thoughtworks;

import java.sql.*;

public class DbUtil {

    private static Connection con = null;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/mydatabase1?serverTimezone" +
                    "=UTC&characterEncoding=utf-8&useSSL=false", "root", "root");

        } catch (
                ClassNotFoundException e) {
            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return con;
    }
}
