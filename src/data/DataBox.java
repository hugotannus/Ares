package data;

import com.sun.rowset.WebRowSetImpl;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.rowset.WebRowSet;
import javax.sql.rowset.spi.SyncProviderException;

/**
 *
 * @author guilherme
 */
class DataBox {

    private WebRowSet serviceRowSet;
    private WebRowSet projectRowSet;
    private WebRowSet materialRowSet;
    private WebRowSet logisticRowSet;
    private WebRowSet workmanRowSet;

    public DataBox() throws SQLException {
        this.serviceRowSet = new WebRowSetImpl();
        this.projectRowSet = new WebRowSetImpl();
        this.materialRowSet = new WebRowSetImpl();
        this.logisticRowSet = new WebRowSetImpl();
        this.workmanRowSet = new WebRowSetImpl();
    }

    public WebRowSet getServiceRowSet() {
        return serviceRowSet;
    }

    public WebRowSet getProjectRowSet() {
        return projectRowSet;
    }

    public WebRowSet getMaterialRowSet() {
        return materialRowSet;
    }

    public WebRowSet getLogisticRowSet() {
        return logisticRowSet;
    }

    public WebRowSet getWorkmanRowSet() {
        return workmanRowSet;
    }
    
    public void cleanData() throws SQLException{
        this.logisticRowSet.release();
        this.materialRowSet.release();
        this.projectRowSet.release();
        this.serviceRowSet.release();
        this.workmanRowSet.release();
    }
    
    public void saveData(Connection conn) throws SyncProviderException, SQLException{
        
        try{
            logisticRowSet.acceptChanges(conn);
            workmanRowSet.acceptChanges(conn);
            materialRowSet.acceptChanges(conn);
            projectRowSet.acceptChanges(conn);
        }catch(SyncProviderException e){
            throw new SyncProviderException("SPE: Could not save the changes.");
        }
        
        
        logisticRowSet.close();
        workmanRowSet.close();
        materialRowSet.close();
        projectRowSet.close();
        
    }
}
