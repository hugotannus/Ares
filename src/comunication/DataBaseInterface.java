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
public interface DataBaseInterface extends Remote{

    public boolean isConnected() throws RemoteException;
    
    public CachedRowSet executeQuery(String query)
            throws SQLException, RemoteException;
    
    public CachedRowSet executeQuery(short ID, int tableID)
            throws SQLException, RemoteException;

    public CachedRowSet executeServiceQuery(int ID)
            throws SQLException, RemoteException;

    public CachedRowSet executeMaterialQuery(short ID)
            throws SQLException, RemoteException;

    public CachedRowSet executeProjectQuery(short ID)
            throws SQLException, RemoteException;

    public CachedRowSet executeLogisticQuery(short ID)
            throws SQLException, RemoteException;

    public CachedRowSet executeWorkmanQuery(short ID)
            throws SQLException, RemoteException;

    public void updateService(String comment, String budget)
            throws SQLException, RemoteException;

    public void updateCellTable(int tableID, int row, int col, Object obj)
            throws SQLException, RemoteException;

    public void updateProjectCell(int row, int col, Object obj)
            throws SQLException, RemoteException;

    public void addRow(int tableID, short serviceID, String name, String sponsor)
            throws SQLException, RemoteException;

    public void addProjectRow(short serviceID, String name, String sponsor)
            throws SQLException, RemoteException;

    public CachedRowSet getRowSet(int tableID) throws RemoteException;

    public CachedRowSet getServiceRowSet() throws RemoteException;

    public CachedRowSet getLogisticRowSet() throws RemoteException;

    public CachedRowSet getMaterialRowSet() throws RemoteException;

    public CachedRowSet getProjectRowSet() throws RemoteException;

    public CachedRowSet getWorkmanRowSet() throws RemoteException;

    public void acceptChanges()
            throws SyncProviderException, SQLException, RemoteException;
    
    public void acceptChanges(int tableID)
            throws SyncProviderException, SQLException, RemoteException;

    public Connection getConnection()
            throws ClassNotFoundException, SQLException, RemoteException;

    public void closeConnection()
            throws SQLException, RemoteException;
}