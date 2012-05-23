/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;


import com.sun.rowset.CachedRowSetImpl;
import commons.Material;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author hugo
 */
public class DataBaseManager {

    /*
     * (05/03/2012) Serão necessários, no mínimo, dois objetos "RowSet" para o
     * ares, um para realizar a pesquisa para cada entrada da tabela "service",
     * e outro para manipular as quatro tabelas contidos dentro de cada tela de
     * serviço.
     */
    private CachedRowSet serviceRowSet;
    private CachedRowSet materialRowSet;
    private CachedRowSet logisticRowSet;
    private CachedRowSet projectRowSet;
    private CachedRowSet workmanshipRowSet;
    private PreparedStatement serviceStmt;
    private final PreparedStatement materialStmt;
    private final PreparedStatement logisticStmt;
    private final PreparedStatement projectStmt;
    private final PreparedStatement workmanshipStmt;
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost/ares";
    static final String USERNAME = "ares";
    static final String PASSWORD = "vernacula";
    private Connection conn;

    public DataBaseManager() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
        conn.setAutoCommit(false);
        
        serviceRowSet = new CachedRowSetImpl();
        projectRowSet = new CachedRowSetImpl();
        materialRowSet = new CachedRowSetImpl();
        logisticRowSet = new CachedRowSetImpl();
        workmanshipRowSet = new CachedRowSetImpl();

        serviceStmt = conn.prepareStatement("SELECT * FROM service WHERE ID=?");
        projectStmt = conn.prepareStatement("SELECT * FROM project WHERE service_id=?");
        materialStmt = conn.prepareStatement("SELECT * FROM material WHERE service_id=?");
        logisticStmt = conn.prepareStatement("SELECT * FROM logistic WHERE service_id=?");
        workmanshipStmt = conn.prepareStatement("SELECT * FROM workmanship WHERE service_id=?");
    }

    public CachedRowSet executeServiceQuery(int ID) throws SQLException {
        serviceStmt.setInt(1, ID);
        serviceRowSet.populate(serviceStmt.executeQuery());
        return serviceRowSet;
    }

    public CachedRowSet getServiceRowSet() {
        return serviceRowSet;
    }

    public void updateService(String comment, String budget) throws SQLException {
        //System.out.printf("budget: %s.\tService: %s.\n", budget, service);
        if(budget.length() == 0) budget = null;
        //System.out.printf("budget: %s.\tService: %s.\n", budget, service);
        serviceRowSet.moveToCurrentRow();
        serviceRowSet.updateString("comments", comment);
        serviceRowSet.updateString("budget", budget);
        serviceRowSet.updateRow();
        serviceRowSet.acceptChanges(conn);
        serviceRowSet.close();
    }

    public void updateMaterial() throws SQLException {
//        materialRowSet.acceptChanges(conn);
        materialRowSet.close();
    }

    public void updateProject() throws SQLException {
//        projectRowSet.acceptChanges(conn);
        projectRowSet.close();
    }

    public void updateLogistic() throws SQLException {
//        logisticRowSet.acceptChanges(conn);
        logisticRowSet.close();
    }

    public void updateWorkmanship() throws SQLException {
//        workmanshipRowSet.acceptChanges(conn);
        workmanshipRowSet.close();
    }

    public void closeConnection() throws SQLException{
        conn.close();
    }

    public CachedRowSet executeMaterialQuery(short ID) throws SQLException {
        materialStmt.setInt(1, ID);
        materialRowSet.populate(materialStmt.executeQuery());
        return materialRowSet;
    }

    public CachedRowSet executeProjectQuery(short ID) throws SQLException {
        projectStmt.setInt(1, ID);
        projectRowSet.populate(projectStmt.executeQuery());
        return projectRowSet;
    }

    public CachedRowSet executeLogisticQuery(short ID) throws SQLException {
        logisticStmt.setInt(1, ID);
        logisticRowSet.populate(logisticStmt.executeQuery());
        return logisticRowSet;
    }

    public CachedRowSet executeWorkmanshipQuery(short ID) throws SQLException {
        workmanshipStmt.setInt(1, ID);
        workmanshipRowSet.populate(workmanshipStmt.executeQuery());
        return workmanshipRowSet;
    }
}
