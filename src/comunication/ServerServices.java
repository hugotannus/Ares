/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comunication;

import java.rmi.RemoteException;
import java.sql.SQLException;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.spi.SyncProviderException;

import data.DataBaseManager;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
/**
 *
 * @author guilherme
 */
public class ServerServices extends UnicastRemoteObject implements ServerServicesInterface{
    
    private boolean connected;
    private ArrayList<CachedRowSet> cachedRowSetList;
    
    
    public ServerServices() throws RemoteException{
        super();
    }
    
    @Override
    public boolean login(String user, String password) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isConnected() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CachedRowSet executeQuery(short ID, int tableID) throws SQLException, RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateService(String comment, String budget) throws SQLException, RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateCellTable(int tableID, int row, int col, Object obj) throws SQLException, RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addRow(int tableID, short serviceID, String name, String sponsor) throws SQLException, RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void acceptChanges() throws SyncProviderException, SQLException, RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void acceptChanges(int tableID) throws SyncProviderException, SQLException, RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void closeConnection() throws SQLException, RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
