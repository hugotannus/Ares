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
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private PreparedStatement addStmt;
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost/ares";
    static final String USERNAME = "ares";
    static final String PASSWORD = "vernacula";
    private final int MATERIAL = 0;
    private final int LOGISTIC = 1;
    private final int PROJECT = 2;
    private final int WORKMAN = 3;
    private Connection con;

    public DataBaseManager(String userName, String password) throws ClassNotFoundException, SQLException {
        con = getConnection(userName, password);

        serviceStmt = con.prepareStatement("SELECT * FROM service WHERE ID=?");
        projectStmt = con.prepareStatement("SELECT * FROM project WHERE service_id=?");
        materialStmt = con.prepareStatement("SELECT * FROM material WHERE service_id=?");
        logisticStmt = con.prepareStatement("SELECT * FROM logistic WHERE service_id=?");
        workmanStmt = con.prepareStatement("SELECT * FROM workman WHERE service_id=?");

        serviceRowSet = new CachedRowSetImpl();
        projectRowSet = new CachedRowSetImpl();
        materialRowSet = new CachedRowSetImpl();
        logisticRowSet = new CachedRowSetImpl();
        workmanRowSet = new CachedRowSetImpl();
    }

    public DataBaseManager() throws ClassNotFoundException, SQLException {
        this(USERNAME, PASSWORD);
    }

    public CachedRowSet executeQuery(short ID, int tableID) throws SQLException {
        switch (tableID) {
            case MATERIAL:
                return executeMaterialQuery(ID);
            case LOGISTIC:
                return executeLogisticQuery(ID);
            case PROJECT:
                return executeProjectQuery(ID);
            case WORKMAN:
                return executeWorkmanQuery(ID);
            default:
                return executeServiceQuery(ID);
        }
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
        serviceRowSet.acceptChanges(con);
        serviceRowSet.close();
    }

    public void updateCellTable(int tableID, int row, int col, Object obj) throws SQLException {
        switch (tableID) {
            case MATERIAL:
                updateMaterialCell(row, col, obj);
                break;
            case LOGISTIC:
                updateLogisticCell(row, col, obj);
                break;
            case PROJECT:
                updateProjectCell(row, col, obj);
                break;
            case WORKMAN:
                updateWorkmanCell(row, col, obj);
                break;
            default:
        }
    }

    private void updateMaterialCell(int row, int col, Object obj) throws SQLException {
        materialRowSet.absolute(row);
        materialRowSet.updateObject(col, obj);
        materialRowSet.updateRow();
    }

    private void updateLogisticCell(int row, int col, Object obj) throws SQLException {
        logisticRowSet.absolute(row);
        logisticRowSet.updateObject(col, obj);
        logisticRowSet.updateRow();
    }

    private void updateWorkmanCell(int row, int col, Object obj) throws SQLException {
        workmanRowSet.absolute(row);
        workmanRowSet.updateObject(col, obj);
        workmanRowSet.updateRow();
    }

    public void updateProjectCell(int row, int col, Object obj) throws SQLException {
        projectRowSet.absolute(row);
        projectRowSet.updateObject(col, obj);
        projectRowSet.updateRow();
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

    public void addRow(int tableID, short serviceID, String name, String sponsor) throws SQLException {
        String table;
        switch (tableID) {
            case MATERIAL:
                table = "material";
                break;
            case LOGISTIC:
                table = "logistic";
                break;
            case PROJECT:
                table = "project";
                addProjectRow(serviceID, name, sponsor);
                break;
            case WORKMAN:
                table = "workman";
                break;
            default:
                table = "service";
        }
        /*
        addStmt = con.prepareStatement(
                String.format("INSERT INTO %s (service_ID, name, sponsor)"
                + "VALUES (?,?,?)", table));
        addStmt.setShort(1, serviceID);
        addStmt.setString(2, name);
        addStmt.setString(3, sponsor);
        addStmt.executeUpdate();
        this.acceptChanges(tableID);
        */
        System.out.printf("ServiceID: %d", serviceID);
    }

    public void addProjectRow(short serviceID, String name, String sponsor) throws SQLException {
        projectRowSet.moveToInsertRow();
        //projectRowSet.insertRow();
        //projectRowSet.updateInt("ID", 99);
        
        projectRowSet.updateShort("service_ID", serviceID);
        projectRowSet.updateString("name", name);
        projectRowSet.updateString("sponsor", sponsor);
        projectRowSet.updateBoolean("defined", false);
        projectRowSet.updateBoolean("approved", false);
        projectRowSet.updateBoolean("visible", true);
        projectRowSet.insertRow();
        projectRowSet.moveToCurrentRow();
        projectRowSet.last();
    }

    public CachedRowSet getRowSet(int tableID) {
        switch (tableID) {
            case MATERIAL:
                return getMaterialRowSet();
            case LOGISTIC:
                return getLogisticRowSet();
            case PROJECT:
                return getProjectRowSet();
            case WORKMAN:
                return getWorkmanRowSet();
            default:
                return getServiceRowSet();
        }
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

    public Connection getConnection(String userName, String password) throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        Connection conn = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
        conn.setAutoCommit(false);
        return conn;
    }

    public void acceptChanges() throws SyncProviderException, SQLException {
        materialRowSet.acceptChanges(con);
        materialRowSet.close();
        logisticRowSet.acceptChanges(con);
        logisticRowSet.close();
        projectRowSet.acceptChanges(con);
        //projectRowSet.acceptChanges();
        projectRowSet.close();
        workmanRowSet.acceptChanges(con);
        workmanRowSet.close();
    }

    public void acceptChanges(int tableID) throws SyncProviderException, SQLException {
        switch (tableID) {
            case MATERIAL:
                materialRowSet.acceptChanges(con);
                break;
            case LOGISTIC:
                logisticRowSet.acceptChanges(con);
                break;
            case PROJECT:
                projectRowSet.acceptChanges(con);
                break;
            case WORKMAN:
                workmanRowSet.acceptChanges(con);
                break;
            default:
                this.acceptChanges();
        }
    }

    public void closeConnection() throws SQLException {
        con.close();
    }
}
