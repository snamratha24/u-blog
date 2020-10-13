package com.upgrad.ublog.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * TODO 6.2: Implement the DatabaseConnection class using the Singleton Pattern (Hint. Should have the
 *  private no-arg constructor, a private static connection attribute of type Connection and a public
 *  static getConnection() method which return the connection attribute).
 * TODO 6.3: The getInstance() method should check if the connection attribute is null. If yes, then
 *  it should create a connection object which is connected with the local database and assign this
 *  connection object to the connection attribute. In the end, return this connection attribute.
 * TODO 6.4: You should handle the ClassNotFoundException and SQLException individually,
 *  and not using the Exception class.
 */

public class DatabaseConnection {

    public static void main(String[] args) throws SQLException{
        try {
        	Connection newConnection = getConnection();
        	if(newConnection != null) {
        	System.out.println("Connected");} else {
        	    throw new Exception();
            }
        } catch (Exception e) {
        	System.out.println("Not Connected");
        }
    }

  /*  private static Connection connection;

    private DatabaseConnection() {
        try {

            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orcl", "hr", "oracle");
            System.out.println("Connected");

        }  catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static Connection getConnection() {
        return connection;
    }
    */

//    HOHOJHO

    private static Connection connection = null;

    public static Connection getConnection () throws SQLException{
        if (connection == null) {
            String url = "jdbc:oracle:thin:@localhost:1521/orcl";
            String username = "hr";
            String password = "oracle";

            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                connection = DriverManager.getConnection(url, username, password);
                System.out.println(connection);
            } catch (ClassNotFoundException e) {
                System.out.println("Oracle Driver not found. Please download and add the driver.");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return connection;
    }

    public DatabaseConnection() {}


}

