/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import com.sun.rowset.CachedRowSetImpl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.spi.SyncProviderException;

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
    private CachedRowSet workmanRowSet;
    private PreparedStatement serviceStmt;
    private final PreparedStatement materialStmt;
    private final PreparedStatement logisticStmt;
    private final PreparedStatement projectStmt;
    private final PreparedStatement workmanStmt;
    private final PreparedStatement addStmt;
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
        workmanRowSet = new CachedRowSetImpl();

        serviceStmt = conn.prepareStatement("SELECT * FROM service WHERE ID=?");
        projectStmt = conn.prepareStatement("SELECT * FROM project WHERE service_id=?");
        materialStmt = conn.prepareStatement("SELECT * FROM material WHERE service_id=?");
        logisticStmt = conn.prepareStatement("SELECT * FROM logistic WHERE service_id=?");
        workmanStmt = conn.prepareStatement("SELECT * FROM workman WHERE service_id=?");
        addStmt = conn.prepareStatement("INSERT " +
                "INTO project (service_ID, name, sponsor)" +
                "VALUES (?,?,?)");
    }

    public CachedRowSet executeServiceQuery(int ID) throws SQLException {
        serviceStmt.setInt(1, ID);
        serviceRowSet.populate(serviceStmt.executeQuery());
        return serviceRowSet;
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

    public CachedRowSet executeWorkmanQuery(short ID) throws SQLException {
        workmanStmt.setInt(1, ID);
        workmanRowSet.populate(workmanStmt.executeQuery());
        return workmanRowSet;
    }

    public void updateService(String comment, String budget) throws SQLException {
        //System.out.printf("budget: %s.\tService: %s.\n", budget, service);
        if (budget.length() == 0) {
            budget = null;
        }
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

    public void updateProject(int row, int col, String str) throws SQLException{
        projectRowSet.absolute(row);
        System.out.printf("Serviço ID: %d\t", projectRowSet.getInt("service_ID"));
        System.out.printf("responsavel: %s\n", projectRowSet.getString(col));
        projectRowSet.updateString(col, str);
        projectRowSet.updateRow();
        projectRowSet.acceptChanges(conn);
        //projectRowSet.commit();
        System.out.printf("Serviço ID: %d\t", projectRowSet.getInt("service_ID"));
        System.out.printf("responsavel: %s\n", projectRowSet.getString(col));
    }

    public void updateProject(int row, String name, String sponsor,
            boolean defined, boolean aproved) throws SQLException {
        projectRowSet.absolute(row);
        projectRowSet.setString("name", name);
        projectRowSet.setString("sponsor", sponsor);
        projectRowSet.setBoolean("defined", defined);
        projectRowSet.setBoolean("aproved", aproved);
        projectRowSet.updateRow();
    }

    public void updateLogistic() throws SQLException {
//        logisticRowSet.acceptChanges(conn);
        logisticRowSet.close();
    }

    public void updateWorkman() throws SQLException {
//        workmanRowSet.acceptChanges(conn);
        workmanRowSet.close();
    }
    
    public void addProject(short ID, String name, String sponsor) throws SQLException {
/*      projectRowSet.moveToInsertRow();
        projectRowSet.updateShort("service_ID", ID);
        projectRowSet.updateString("name", name);
        projectRowSet.updateString("sponsor", name);
        projectRowSet.updateBoolean(5, false);
        projectRowSet.updateBoolean(6, false);
        projectRowSet.updateBoolean(7, true);
        projectRowSet.insertRow();
        projectRowSet.moveToCurrentRow();
        projectRowSet.acceptChanges(conn);
 *
 */
        addStmt.setShort(1, ID);
        addStmt.setString(2, name);
        addStmt.setString(3, sponsor);
        addStmt.executeUpdate();
    }
    
    public CachedRowSet getServiceRowSet() {
        return serviceRowSet;
    }

    public CachedRowSet getLogisticRowSet() {
        return logisticRowSet;
    }

    public CachedRowSet getMaterialRowSet() {
        return materialRowSet;
    }

    public CachedRowSet getProjectRowSet() {
        return projectRowSet;
    }

    public CachedRowSet getWorkmanRowSet() {
        return workmanRowSet;
    }

    public void acceptChanges() throws SyncProviderException, SQLException {
        materialRowSet.acceptChanges(conn);
        materialRowSet.close();
        logisticRowSet.acceptChanges(conn);
        logisticRowSet.close();
        projectRowSet.acceptChanges(conn);
        projectRowSet.close();
        workmanRowSet.acceptChanges(conn);
        workmanRowSet.close();
    }

    public void closeConnection() throws SQLException {
        conn.close();
    }
}
