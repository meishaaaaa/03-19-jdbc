package com.thoughtworks;

import java.sql.*;

public class DbUtil {

    private static Connection con = null;

    public static Connection getConnection() {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (
                    ClassNotFoundException e) {
                System.out.println("Sorry,can`t find the Driver!");
                e.printStackTrace();
            }
            try {
                con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/mydatabase1?serverTimezone" +
                                "=UTC&characterEncoding=utf-8&useSSL=false", "root", "root");

            } catch (
                    SQLException e) {
                e.printStackTrace();
            }
            return con;
    }
}
