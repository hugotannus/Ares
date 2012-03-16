/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;


import com.sun.rowset.CachedRowSetImpl;
import commons.Material;
import java.sql.SQLException;
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
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost/ares";
    static final String USERNAME = "ares";
    static final String PASSWORD = "vernacula";

    public DataBaseManager() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);

        serviceRowSet = new CachedRowSetImpl();
        serviceRowSet.setUrl(DATABASE_URL);
        serviceRowSet.setUsername(USERNAME);
        serviceRowSet.setPassword(PASSWORD);
        //serviceRowSet.a
    }

    public void executeQuery(String query) throws SQLException {
        serviceRowSet.setCommand(query);
        serviceRowSet.execute();
    }

    public CachedRowSet getRowSet() {
        return serviceRowSet;
    }

    public void updateService(String comment, String budget) throws SQLException {
        //System.out.printf("budget: %s.\tService: %s.\n", budget, service);
        if(budget.length() == 0) budget = null;
        //System.out.printf("budget: %s.\tService: %s.\n", budget, service);
        serviceRowSet.moveToCurrentRow();
 /*       rowSet.updateString("topic_struct", service.estTopicos);
        rowSet.updateShort("service_ID", service.ID);
        rowSet.updateString("service_name", service.getDescricao());
        rowSet.updateDate("begin_date", (Date) service.dataInicio);
        rowSet.updateDate("end_date", (Date) service.dataTermino);
  */    serviceRowSet.updateString("comments", comment);
        //rowSet.updateBlob("comments", new SerialBlob(comment.getBytes()));
        serviceRowSet.updateString("budget", budget);
        serviceRowSet.updateRow();
        //serviceRowSet.acceptChanges();
    }

    public void acceptChanges() throws SyncProviderException{
        serviceRowSet.acceptChanges();
    }

    public void updateMaterial(Material material) {
        throw new UnsupportedOperationException("Not supported yet.");

    }

    public void closeConnection() throws SQLException{
        serviceRowSet.close();
    }
}
