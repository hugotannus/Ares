/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comunication;

import data.DataBaseManager;
import java.io.StringWriter;
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

    public ServerServices() throws RemoteException {
        super();
        connected = false;
        dbmanager = null;
    }

    @Override
    public boolean login(String user, char[] password) throws RemoteException {
        try {
            dbmanager = new DataBaseManager(user, password);
            
            connected = true;
            return isConnected();
        } catch (Exception ex) {
            Logger.getLogger(ServerServices.class.getName()).log(Level.SEVERE, null, ex);
            throw new RemoteException("Não foi possível realizar conexão com o banco de dados. Usuário ou senha podem estar incorretos.");
        }
    }

    @Override
    public boolean isConnected() throws RemoteException {
        return connected;
    }

    @Override
    public void logout() throws SQLException, RemoteException {
        if (dbmanager == null || connected != true) {
            return;
        }

        acceptChanges();
        dbmanager.closeConnection();
        connected = false;
    }

    @Override
    public String executeQuery(short ID, int tableID) throws SQLException, RemoteException {
        WebRowSet wrs = dbmanager.executeQuery(ID, tableID);
        
        StringWriter sw = new StringWriter();
        
        wrs.writeXml(sw);
        
        return sw.toString();
    }

    @Override
    public void updateService(String comment, String budget) throws SQLException, RemoteException {
        dbmanager.updateService(comment, budget);
    }

    @Override
    public void updateCellTable(int tableID, int row, int col, Object obj) throws SQLException, RemoteException {
        dbmanager.updateCellTable(tableID, row, col, obj);
    }

    @Override
    public void addRow(int tableID, short serviceID, String name, String sponsor) throws SQLException, RemoteException {
        dbmanager.addRow(tableID, serviceID, name, sponsor);
    }

    @Override
    public void acceptChanges() throws SyncProviderException, SQLException, RemoteException {
        dbmanager.acceptChanges();
    }

    @Override
    public void acceptChanges(int tableID) throws SyncProviderException, SQLException, RemoteException {
        dbmanager.acceptChanges(tableID);
    }
}
