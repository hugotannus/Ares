/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import com.sun.rowset.CachedRowSetImpl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.spi.SyncProviderException;

/**
 *
 * @author hugo
 */
public final class DataBaseManager {

    private CachedRowSet serviceRowSet;
    private CachedRowSet materialRowSet;
    private CachedRowSet logisticRowSet;
    private CachedRowSet projectRowSet;
    private CachedRowSet workmanRowSet;
    private final int MATERIAL = 0;
    private final int LOGISTIC = 1;
    private final int PROJECT = 2;
    private final int WORKMAN = 3;
    private Connection conn;
    private boolean connected = false;

    public DataBaseManager(String userName, char[] password) throws ClassNotFoundException, SQLException {
        System.out.printf("userName: %s\tpswd: %s\n", userName, password);
        conn = getConnection();
        connected = true;

        serviceRowSet = new CachedRowSetImpl();
        projectRowSet = new CachedRowSetImpl();
        materialRowSet = new CachedRowSetImpl();
        logisticRowSet = new CachedRowSetImpl();
        workmanRowSet = new CachedRowSetImpl();
    }

    public boolean isConnected() {
        return connected;
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
        String cmd = String.format("SELECT * FROM service WHERE ID=%d", ID);
        serviceRowSet.setCommand(cmd);
        serviceRowSet.execute(conn);
        return serviceRowSet;
    }

    public CachedRowSet executeMaterialQuery(short ID) throws SQLException {
        String cmd = String.format("SELECT * FROM material WHERE service_id=%d AND visible=1", ID);
        materialRowSet.setCommand(cmd);
        materialRowSet.execute(conn);
        return materialRowSet;
    }

    public CachedRowSet executeProjectQuery(short ID) throws SQLException {
        String cmd = String.format("SELECT * FROM project WHERE service_id=%d AND visible=1", ID);
        projectRowSet.setCommand(cmd);
        projectRowSet.execute(conn);
        return projectRowSet;
    }

    public CachedRowSet executeLogisticQuery(short ID) throws SQLException {
        String cmd = String.format("SELECT * FROM logistic WHERE service_id=%d AND visible=1", ID);
        logisticRowSet.setCommand(cmd);
        logisticRowSet.execute(conn);
        return logisticRowSet;
    }

    public CachedRowSet executeWorkmanQuery(short ID) throws SQLException {
        String cmd = String.format("SELECT * FROM workman WHERE service_id=%d AND visible=1", ID);
        workmanRowSet.setCommand(cmd);
        workmanRowSet.execute(conn);
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

    public void updateCellTable(int tableID, int row, int col, Object obj) throws SQLException {
        System.out.printf("Número da Linha: %d\n", row);
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

    public void addRow(int tableID, short serviceID, String name, String sponsor) throws SQLException {
        switch (tableID) {
            case MATERIAL:
                addMaterialRow(serviceID, name, sponsor);
                break;
            case LOGISTIC:
                addLogisticRow(serviceID, name, sponsor);
                break;
            case PROJECT:
                addProjectRow(serviceID, name, sponsor);
                break;
            case WORKMAN:
                addWorkmanRow(serviceID, name, sponsor);
                break;
            default:
                ;
        }
        System.out.printf("ServiceID: %d", serviceID);
    }

    private void addMaterialRow(short serviceID, String name, String sponsor) throws SQLException {
        materialRowSet.moveToInsertRow();
        materialRowSet.updateNull(1);
        materialRowSet.updateShort(2, serviceID);
        materialRowSet.updateString(3, name);
        materialRowSet.updateString(4, sponsor);
        materialRowSet.updateBoolean(5, false);
        materialRowSet.updateBoolean(6, false);
        materialRowSet.updateBoolean(7, false);
        materialRowSet.updateBoolean(8, true);
        materialRowSet.insertRow();
        materialRowSet.moveToCurrentRow();
    }

    private void addLogisticRow(short serviceID, String name, String sponsor) throws SQLException {
        logisticRowSet.moveToInsertRow();
        logisticRowSet.updateNull(1);
        logisticRowSet.updateShort(2, serviceID);
        logisticRowSet.updateString(3, name);
        logisticRowSet.updateString(4, sponsor);
        logisticRowSet.updateBoolean(5, false);
        logisticRowSet.updateBoolean(6, false);
        logisticRowSet.updateBoolean(7, true);
        logisticRowSet.insertRow();
        logisticRowSet.moveToCurrentRow();
        logisticRowSet.last();
    }

    private void addWorkmanRow(short serviceID, String name, String sponsor) throws SQLException {
        workmanRowSet.moveToInsertRow();
        workmanRowSet.updateNull(1);
        workmanRowSet.updateShort(2, serviceID);
        workmanRowSet.updateString(3, name);
        workmanRowSet.updateString(4, sponsor);
        workmanRowSet.updateBoolean(5, false);
        workmanRowSet.updateBoolean(6, false);
        workmanRowSet.updateBoolean(7, true);
        workmanRowSet.insertRow();
        workmanRowSet.moveToCurrentRow();
        workmanRowSet.last();
    }

    public void addProjectRow(short serviceID, String name, String sponsor) throws SQLException {
        projectRowSet.moveToInsertRow();
        projectRowSet.updateNull(1);
        projectRowSet.updateShort(2, serviceID);
        projectRowSet.updateString(3, name);
        projectRowSet.updateString(4, sponsor);
        projectRowSet.updateBoolean(5, false);
        projectRowSet.updateBoolean(6, false);
        projectRowSet.updateBoolean(7, true);
        projectRowSet.insertRow();
        projectRowSet.moveToCurrentRow();
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

    public void acceptChanges() throws SyncProviderException, SQLException {
        if (isConnected()) {
            logisticRowSet.acceptChanges(conn);
            workmanRowSet.acceptChanges(conn);
            materialRowSet.acceptChanges(conn);
            projectRowSet.acceptChanges(conn);
            logisticRowSet.close();
            workmanRowSet.close();
            materialRowSet.close();
            projectRowSet.close();
        }
    }

    public void acceptChanges(int tableID) throws SyncProviderException, SQLException {
        if(isConnected()){ 
        switch (tableID) {
            case MATERIAL:
                materialRowSet.acceptChanges(conn);
                break;
            case LOGISTIC:
                logisticRowSet.acceptChanges(conn);
                break;
            case PROJECT:
                projectRowSet.acceptChanges(conn);
                break;
            case WORKMAN:
                workmanRowSet.acceptChanges(conn);
                break;
            default:
                this.acceptChanges();
        }
        }
    }

    public Connection getConnection()
            throws ClassNotFoundException, SQLException {
        Class.forName(DataBaseInput.JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DataBaseInput.DATABASE_URL,
                DataBaseInput.USERNAME, DataBaseInput.PASSWORD);
        con.setAutoCommit(false);
        connected = true;
        return con;
    }

    public void closeConnection() throws SQLException {
        connected = false;
        conn.close();
    }
}
