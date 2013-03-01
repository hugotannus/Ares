/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comunication;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import javax.sql.rowset.spi.SyncProviderException;

/**
 *
 * @author Hugo
 */
public interface ServerServicesInterface extends Remote {
    // É essa parada aqui que nós temos que resolver!

    public int login(String user, char[] password) throws RemoteException;

    public boolean isConnected() throws RemoteException;

    public void logout(int user_id)
            throws SQLException, RemoteException;

    public String executeQuery(int user_id, short ID, int tableID)
            throws SQLException, RemoteException;

    public void updateService(int user_id, String comment, String budget)
            throws SQLException, RemoteException;

    public void updateCellTable(int user_id, int tableID, int row, int col, Object obj)
            throws SQLException, RemoteException;

    public void addRow(int user_id, int tableID, short serviceID, String name, String sponsor)
            throws SQLException, RemoteException;

    public String getRowSet(int user_id, int tableID) throws SQLException, RemoteException;

    public void acceptChanges(int user_id)
            throws SyncProviderException, SQLException, RemoteException;
}