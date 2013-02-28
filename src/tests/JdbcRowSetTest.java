/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import com.sun.rowset.JdbcRowSetImpl;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.JdbcRowSet;

/**
 *
 * @author hugo
 */
public class JdbcRowSetTest {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost/ares";
    static final String USERNAME = "ares";
    static final String PASSWORD = "vernacula";

    public JdbcRowSetTest() {
        try {
            Class.forName(JDBC_DRIVER);

            JdbcRowSet rowSet = new JdbcRowSetImpl();
            rowSet.setUrl(DATABASE_URL);
            rowSet.setUsername(USERNAME);
            rowSet.setPassword(PASSWORD);
            rowSet.setCommand("SELECT * FROM material");
            rowSet.execute();

            ResultSetMetaData metaData = rowSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            System.out.println("Lista de Materiais");

            for(int i=1; i<=numberOfColumns; i++)
                System.out.printf("%-4s\t", metaData.getColumnName(i));
            System.out.println();

            while(rowSet.next()){
                for(int i=1; i<=numberOfColumns; i++)
                    System.out.printf("%-10s\t", rowSet.getObject(i));
                System.out.println("");
            }
        } catch (SQLException ex) {
            Logger.getLogger(JdbcRowSetTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JdbcRowSetTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main( String args []){
        JdbcRowSetTest window = new JdbcRowSetTest();
    }
}
