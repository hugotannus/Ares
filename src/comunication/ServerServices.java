/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comunication;

import data.DataBaseManager;
import java.io.StringWriter;
import java.io.Writer;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.WebRowSet;
import javax.sql.rowset.spi.SyncProviderException;

/**
 *
 * @author guilherme
 */
public class ServerServices extends UnicastRemoteObject implements ServerServicesInterface {

    private boolean connected;
    private DataBaseManager dbmanager;
    private static int user_ID = 0; 

    public ServerServices() throws RemoteException {
        super();
        connected = false;
        dbmanager = new DataBaseManager();
    }

    @Override
    public int login(String user, char[] password) throws RemoteException {
        try {
            
            connected = true;
            int id = getUserID();
            boolean logged = dbmanager.login(user, password, id);
            
            if(!logged) {
                throw new RemoteException("Usuário ou senha inválidos!! Sai daí pedreiro!");
            }
            
            return id;
        } catch (Exception ex) {
            Logger.getLogger(ServerServices.class.getName()).log(Level.SEVERE, null, ex);
            throw new RemoteException("Não foi possível realizar conexão com o banco de dados. Usuário ou senha podem estar incorretos.");
        }
    }
    
    private int getUserID(){
        
        return ServerServices.user_ID++;
    }

    @Override
    public boolean isConnected() throws RemoteException {
        return connected;
    }

    @Override
    public void logout(int user_id) throws SQLException, RemoteException {
        if (dbmanager == null || connected != true) {
            return;
        }

        acceptChanges();
        dbmanager.closeConnection(user_id);
        connected = false;
    }

    @Override
    public String executeQuery(int user_id, short ID, int tableID) throws SQLException, RemoteException {
        WebRowSet wrs = dbmanager.executeQuery(user_id, ID, tableID);
        
        StringWriter sw = new StringWriter();
        
        wrs.writeXml(sw);
        
        return sw.toString();
    }

    @Override
    public void updateService(int user_id, String comment, String budget) throws SQLException, RemoteException {
        dbmanager.updateService(user_id, comment, budget);
    }

    @Override
    public void updateCellTable(int user_id, int tableID, int row, int col, Object obj) throws SQLException, RemoteException {
        dbmanager.updateCellTable(user_id, tableID, row, col, obj);
    }

    @Override
    public void addRow(int user_id, int tableID, short serviceID, String name, String sponsor) throws SQLException, RemoteException {
        dbmanager.addRow(user_id, tableID, serviceID, name, sponsor);
    }

    @Override
    public void acceptChanges(int user_id) throws SyncProviderException, SQLException, RemoteException {
        dbmanager.acceptChanges(user_id);
    }

    @Override
    public String getRowSet(int user_id, int tableID) throws SQLException {
        String str = "";
        System.out.printf("tableID %d\n", tableID);
        WebRowSet wrs = dbmanager.getRowSet(user_id, tableID);
        
        System.out.println("Imprimindo linhas da tabela:");
        while (wrs.next()) {
            System.out.println(wrs.getString(3));
        }
        System.out.println("Lista finalizada!");        
        
        if(wrs.size() > 0) {
            Writer sw = new StringWriter();
            wrs.writeXml(sw);
            str = sw.toString();
        }
        return str;
    }
}
