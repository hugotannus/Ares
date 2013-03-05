/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import com.sun.rowset.WebRowSetImpl;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import javax.sql.rowset.WebRowSet;
import javax.sql.rowset.spi.SyncProviderException;

//TODO GIGANTESCO: Arrumar a classe DataBaseManager, e demais necessárias, para que elas utilizem a DataBox; Quase pronto
/**
 *
 * @author hugo
 */
public final class DataBaseManager {

    private HashMap<Integer, DataBox> dataBag;
    private final int MATERIAL = 0;
    private final int LOGISTIC = 1;
    private final int PROJECT = 2;
    private final int WORKMAN = 3;
    private final int SERVICE = 4;
    private WebRowSet usersRowSet;
    private Connection conn;
    private boolean connected;
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    public DataBaseManager()
            throws ClassNotFoundException, SQLException, RemoteException {

        this.dataBag = new HashMap<Integer, DataBox>();
        usersRowSet = new WebRowSetImpl();
        conn = getConnection(DataBaseInput.USERNAME, DataBaseInput.PASSWORD.toCharArray());
        
        
        connected = true;
    }

    public void login(int user_id, String userName, char[] password) throws SQLException, ClassNotFoundException {
        System.out.printf("userName: %s\tpswd: %s\n", userName, password);
        
        // Lê todos os usuários do banco de dados
//        String cmd = String.format("SELECT * FROM users WHERE username=%s AND password=%s", userName, password);
//        usersRowSet.setCommand(cmd);
//        usersRowSet.execute(conn);
//        
//        int size = usersRowSet.size();
//        
//        if(size != 1){
//            throw new SQLException("Usuário ou senha inválidos!! Sai daí pedreiro!");
//        }
        
        dataBag.put(user_id, new DataBox());
//        usersRowSet.release();

    }

    public WebRowSet executeQuery(int user_id, short ID, int tableID) throws SQLException {

        if (!dataBag.containsKey(user_id)) {
            throw new SQLException("Usuário não existente ou não logado. Sem permissões de realizar esta operação.");
        }

        switch (tableID) {
            case MATERIAL:
                return executeMaterialQuery(user_id, ID);
            case LOGISTIC:
                return executeLogisticQuery(user_id, ID);
            case PROJECT:
                return executeProjectQuery(user_id, ID);
            case WORKMAN:
                return executeWorkmanQuery(user_id, ID);
            case SERVICE:
                return executeServiceQuery(user_id, ID);
            default:
                throw new SQLException("Tabela não encontrada no banco de dados!");
        }
    }

    public WebRowSet executeServiceQuery(int user_id, int ID) throws SQLException {
        String cmd = String.format("SELECT * FROM service WHERE ID=%d", ID);

        WebRowSet rowSet;

        rowSet = dataBag.get(user_id).getServiceRowSet();

        rowSet.setCommand(cmd);
        rowSet.execute(conn);

        return rowSet;
    }

    public WebRowSet executeMaterialQuery(int user_id, short ID) throws SQLException {
        String cmd = String.format("SELECT * FROM material WHERE service_id=%d AND visible=1", ID);

        WebRowSet rowSet;

        rowSet = dataBag.get(user_id).getMaterialRowSet();

        rowSet.setCommand(cmd);
        rowSet.execute(conn);

        return rowSet;
    }

    public WebRowSet executeProjectQuery(int user_id, short ID) throws SQLException {
        String cmd = String.format("SELECT * FROM project WHERE service_id=%d AND visible=1", ID);

        WebRowSet rowSet;

        rowSet = dataBag.get(user_id).getProjectRowSet();

        rowSet.setCommand(cmd);
        rowSet.execute(conn);

        return rowSet;
    }

    public WebRowSet executeLogisticQuery(int user_id, short ID) throws SQLException {
        String cmd = String.format("SELECT * FROM logistic WHERE service_id=%d AND visible=1", ID);

        WebRowSet rowSet;

        rowSet = dataBag.get(user_id).getLogisticRowSet();

        rowSet.setCommand(cmd);
        rowSet.execute(conn);

        return rowSet;
    }

    public WebRowSet executeWorkmanQuery(int user_id, short ID) throws SQLException {
        String cmd = String.format("SELECT * FROM workman WHERE service_id=%d AND visible=1", ID);

        WebRowSet rowSet;

        rowSet = dataBag.get(user_id).getWorkmanRowSet();

        rowSet.setCommand(cmd);
        rowSet.execute(conn);

        return rowSet;
    }

    public void updateService(int user_id, String comment, String budget) throws SQLException {
        System.out.println("-------------------------------------------");
        if (budget.length() == 0) {
            budget = null;
        }
        if (comment.length() == 0) {
            comment = null;
        }
        //System.out.printf("budget: %s.\tService: %s.\n", budget, service);


        if (!dataBag.containsKey(user_id)) {
            throw new SQLException("Usuário não existente ou não logado. Sem permissões de realizar esta operação.");
        }

        WebRowSet serviceRowSet;

        serviceRowSet = dataBag.get(user_id).getServiceRowSet();
        serviceRowSet.first();
        
        System.out.printf("User %d enviou: \t", user_id);
        System.out.printf("Service %d\tbudget: %s.\tComment: %s.\n", serviceRowSet.getInt("ID"), budget, comment);
        
        serviceRowSet.updateString("budget", budget);
        serviceRowSet.updateString("comments", comment);
        System.out.printf("SERVER: Alterou células do serviço (user_id %d)\n", user_id);
        serviceRowSet.updateRow();
        System.out.printf("SERVER: Salvou alterações no WebRowSet (user_id %d)\n", user_id);
        serviceRowSet.acceptChanges(conn);
        System.out.printf("SERVER: Atualizações salvas no banco (user_id %d)\n", user_id);
        serviceRowSet.close();
        System.out.println("------------------------------------");
    }

    public void updateCellTable(int user_id, int tableID, int row, int col, Object obj) throws SQLException {
        System.out.printf("Número da Linha: %d\n", row);

        if (!dataBag.containsKey(user_id)) {
            throw new SQLException("Usuário não existente ou não logado. Sem permissões para realizar esta operação.");
        }

        switch (tableID) {
            case MATERIAL:
                updateMaterialCell(user_id, row, col, obj);
                break;
            case LOGISTIC:
                updateLogisticCell(user_id, row, col, obj);
                break;
            case PROJECT:
                updateProjectCell(user_id, row, col, obj);
                break;
            case WORKMAN:
                updateWorkmanCell(user_id, row, col, obj);
                break;
        }
    }

    private void updateMaterialCell(int user_id, int row, int col, Object obj) throws SQLException {

        WebRowSet rowSet;
        rowSet = dataBag.get(user_id).getMaterialRowSet();

        rowSet.absolute(row);
        rowSet.updateObject(col, obj);
        rowSet.updateRow();
    }

    private void updateLogisticCell(int user_id, int row, int col, Object obj) throws SQLException {
        WebRowSet rowSet;
        rowSet = dataBag.get(user_id).getMaterialRowSet();

        rowSet.absolute(row);
        rowSet.updateObject(col, obj);
        rowSet.updateRow();
    }

    private void updateWorkmanCell(int user_id, int row, int col, Object obj) throws SQLException {
        WebRowSet rowSet;
        rowSet = dataBag.get(user_id).getWorkmanRowSet();

        rowSet.absolute(row);
        rowSet.updateObject(col, obj);
        rowSet.updateRow();
    }

    public void updateProjectCell(int user_id, int row, int col, Object obj) throws SQLException {
        WebRowSet rowSet;
        rowSet = dataBag.get(user_id).getProjectRowSet();

        rowSet.absolute(row);
        rowSet.updateObject(col, obj);
        rowSet.updateRow();
    }

    public void addRow(int user_id, int tableID, short serviceID, String name, String sponsor) throws SQLException {

        if (!dataBag.containsKey(user_id)) {
            throw new SQLException("Usuário não existente ou não logado. Sem permissões para realizar esta operação.");
        }

        switch (tableID) {
            case MATERIAL:
                addMaterialRow(user_id, serviceID, name, sponsor);
                break;
            case LOGISTIC:
                addLogisticRow(user_id, serviceID, name, sponsor);
                break;
            case PROJECT:
                addProjectRow(user_id, serviceID, name, sponsor);
                break;
            case WORKMAN:
                addWorkmanRow(user_id, serviceID, name, sponsor);
                break;
        }
        System.out.printf("ServiceID: %d", serviceID);
    }

    private void addMaterialRow(int user_id, short serviceID, String name, String sponsor) throws SQLException {

        WebRowSet materialRowSet;
        materialRowSet = dataBag.get(user_id).getMaterialRowSet();

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

    private void addLogisticRow(int user_id, short serviceID, String name, String sponsor) throws SQLException {
        WebRowSet logisticRowSet;
        logisticRowSet = dataBag.get(user_id).getLogisticRowSet();

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

    private void addWorkmanRow(int user_id, short serviceID, String name, String sponsor) throws SQLException {
        WebRowSet workmanRowSet;
        workmanRowSet = dataBag.get(user_id).getWorkmanRowSet();

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

    public void addProjectRow(int user_id, short serviceID, String name, String sponsor) throws SQLException {
        WebRowSet projectRowSet;
        projectRowSet = dataBag.get(user_id).getProjectRowSet();

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

    public WebRowSet getRowSet(int user_id, int tableID) throws SQLException {

        if (!dataBag.containsKey(user_id)) {
            throw new SQLException("Usuárionão existente ou não logado. Sem permissões para realizar esta operação.");
        }

        switch (tableID) {
            case MATERIAL:
                return getMaterialRowSet(user_id);
            case LOGISTIC:
                return getLogisticRowSet(user_id);
            case PROJECT:
                return getProjectRowSet(user_id);
            case WORKMAN:
                return getWorkmanRowSet(user_id);
            case SERVICE:
                return getServiceRowSet(user_id);
            default:
                return null;

        }
    }

    public WebRowSet getServiceRowSet(int user_id) {
        return dataBag.get(user_id).getServiceRowSet();
    }

    public WebRowSet getLogisticRowSet(int user_id) {
        return dataBag.get(user_id).getLogisticRowSet();
    }

    public WebRowSet getMaterialRowSet(int user_id) {
        return dataBag.get(user_id).getMaterialRowSet();
    }

    public WebRowSet getProjectRowSet(int user_id) {
        return dataBag.get(user_id).getProjectRowSet();
    }

    public WebRowSet getWorkmanRowSet(int user_id) {
        return dataBag.get(user_id).getWorkmanRowSet();
    }

    public void acceptChanges(int user_id) throws SyncProviderException, SQLException {
        if (!dataBag.containsKey(user_id)) {
            throw new SQLException("Usuário não existente ou não logado. Sem permissões para realizar esta operação.");
        }

        dataBag.get(user_id).saveData(conn);
    }

    public void acceptChanges(int user_id, int tableID) throws SyncProviderException, SQLException {

        if (!dataBag.containsKey(user_id)) {
            throw new SQLException("Usuário não existente ou não logado. Sem permissões para realizar esta operação.");
        }
        
        DataBox userBox = dataBag.get(user_id);

        switch (tableID) {
            case MATERIAL:
                userBox.getMaterialRowSet().acceptChanges(conn);
                break;
            case LOGISTIC:
                userBox.getLogisticRowSet().acceptChanges(conn);
                break;
            case PROJECT:
                userBox.getProjectRowSet().acceptChanges(conn);
                break;
            case WORKMAN:
                userBox.getWorkmanRowSet().acceptChanges(conn);
                break;
            default:
                this.acceptChanges(user_id);
        }
        //}
    }

    public void cleanRowSet(int user_id, int tableID) throws SQLException {
        if(!dataBag.containsKey(user_id)){
            throw new SQLException("Usuário não existente ou não logado. Sem permissões para realizar esta operação.");
        }
        
        DataBox userBox = dataBag.get(user_id);
        
        switch (tableID) {
            case MATERIAL:
                userBox.getMaterialRowSet().release();
                break;
            case LOGISTIC:
                userBox.getLogisticRowSet().release();
                break;
            case PROJECT:
                userBox.getProjectRowSet().release();
                break;
            case WORKMAN:
                userBox.getWorkmanRowSet().release();
                break;
            case SERVICE:
                userBox.getServiceRowSet().release();
        }
    }

    public boolean isConnected() {
        return connected;
    }

    public Connection getConnection(String userName, char[] password)
            throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DataBaseInput.DATABASE_URL,
                DataBaseInput.USERNAME, DataBaseInput.PASSWORD);
        con.setAutoCommit(false);
        connected = true;
        return con;
    }

    public void closeConnection() throws SQLException{
        this.connected = false;
        
        this.conn.close();
        
        for(int i=0; i< dataBag.size(); i++){
            if(dataBag.containsKey(i)){
                this.dataBag.get(i).cleanData();
            }
        }
        
        this.dataBag.clear();
    }
    
    public void logout(int user_id) throws SQLException {
        
        dataBag.get(user_id).cleanData();
        dataBag.remove(user_id);
        
    }
}
