package data;

import java.sql.*;

import javax.sql.rowset.CachedRowSet;

import com.sun.rowset.CachedRowSetImpl;

public class InsertCachedRowSet {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost/";
    static final String USERNAME = "ares";
    static final char[] PASSWORD = {'v', 'e', 'r', 'n', 'a', 'c', 'u', 'l', 'a'};

    public static void main(String[] args) throws Exception {
        DataBaseManager dbManager = new DataBaseManager(USERNAME, PASSWORD);
        CachedRowSet crs = new CachedRowSetImpl();
        //Class.forName("org.sqlite.JDBC");
        //Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
        Class.forName(JDBC_DRIVER);
        Connection conn= DriverManager.getConnection(DATABASE_URL + "ares","ares","vernacula");
        //crs.setCommand("drop table if exists people;");
        //crs.execute(conn);
        //crs.setCommand("create table people (name)");
        //crs.execute(conn);
        /*
        // First way to insert
        crs.setCommand("insert into people(name) values(?)");
        crs.setString(1, "Arunkumar");
        crs.execute(conn);
        // end of first way
        */
        conn.setAutoCommit(false);
        crs.setCommand("select * from project where service_ID=3");
        crs.execute(conn);
         while (crs.next()) {
            System.out.println("Name : " + crs.getString("name"));
        }

        // Second way to insert
        crs.moveToInsertRow();
        //crs.updateString(1, "Arunkumar Subramaniam");
        crs.updateNull(1);
        crs.updateShort(2, (short)3);
        crs.updateString(3, "teste2");
        crs.updateString(4, "João");
        crs.updateBoolean(5, false);
        crs.updateBoolean(6, false);
        crs.updateBoolean(7, true);
        crs.insertRow();
        crs.moveToCurrentRow();
        crs.acceptChanges(conn);
        // end of second way

        while (crs.next()) {
            System.out.print("ID : " + crs.getString(1) + "\t");
            System.out.println("Name : " + crs.getString("name"));
        }

       
        conn.close();
    }
} 