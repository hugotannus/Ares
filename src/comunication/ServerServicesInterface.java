/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comunication;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.spi.SyncProviderException;

/**
 *
 * @author Hugo
 */
public interface ServerServicesInterface extends Remote{
    // É essa parada aqui que nós temos que resolver!
    
    public boolean login(String user, String password) throws RemoteException;
    
    public boolean isConnected() throws RemoteException;
    
    public CachedRowSet executeQuery(short ID, int tableID)
            throws SQLException, RemoteException;

    public void updateService(String comment, String budget)
            throws SQLException, RemoteException;

    public void updateCellTable(int tableID, int row, int col, Object obj)
            throws SQLException, RemoteException;

    public void addRow(int tableID, short serviceID, String name, String sponsor)
            throws SQLException, RemoteException;

    public void acceptChanges()
            throws SyncProviderException, SQLException, RemoteException;
    
    public void acceptChanges(int tableID)
            throws SyncProviderException, SQLException, RemoteException;

    public void closeConnection()
            throws SQLException, RemoteException;
}