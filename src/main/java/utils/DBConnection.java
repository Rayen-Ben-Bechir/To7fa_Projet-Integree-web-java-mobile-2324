package utils;

import java.sql.*;
public class DBConnection
{
    private static final String URL = "jdbc:mysql://localhost:3306/piwj_sj";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private Connection cnx;
    private static DBConnection instance;
    private DBConnection()
    {
        try{
            cnx = DriverManager.getConnection(URL,USER,PASSWORD);
            System.out.println("Connected to DB.");
        }catch(SQLException e){
            System.err.println("Error :"+e.getMessage());
        }
    }

    public static DBConnection getInstance()
    {
        if(instance == null)
            instance = new DBConnection();
        return instance;
    }

    /*public Connection getCnx()
    {
        return cnx;
    }*/

    public Connection getCnx() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

}
