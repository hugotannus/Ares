package tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.rowset.CachedRowSet;

import com.sun.rowset.CachedRowSetImpl;


public class CachedRowSetTester {

    public static void main(String[] args) throws Exception {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con= DriverManager.getConnection("jdbc:oracle:thin:@javaorigin.com:1521:orcl","mydb","password123");
        Statement st=con.createStatement();
        st.setMaxRows(10);
        ResultSet rs= st.executeQuery("select * from users");
        CachedRowSet cachedRowSet=new CachedRowSetImpl();
        cachedRowSet.populate(rs);
        
        rs.close();
        st.close();
        con.close();

        while (cachedRowSet.next()) {
            System.out.println(cachedRowSet.getString(1));
        }

    }

}
