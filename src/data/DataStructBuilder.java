/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import com.sun.rowset.JdbcRowSetImpl;
import commons.Node;
import commons.Service;
import gui.ObraTreeModel;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.sql.rowset.JdbcRowSet;

/**
 *
 * @author Hugo
 */
public class DataStructBuilder {

    private ObraTreeModel obra;
    private JdbcRowSet startingRowSet;
    
    public DataStructBuilder() throws ClassNotFoundException, SQLException, IOException {
        this("utf8-AGO-sede-R04.csv");
    }
    
    public DataStructBuilder(String fileName)
            throws ClassNotFoundException, SQLException, IOException {
        Class.forName(DataBaseInput.JDBC_DRIVER);
        
        System.out.printf("Username: %s\t Password :%s\n", DataBaseInput.USERNAME, DataBaseInput.PASSWORD);
        System.out.println("");
        startingRowSet = new JdbcRowSetImpl();
        startingRowSet.setUrl(DataBaseInput.DATABASE_URL);
        startingRowSet.setUsername(DataBaseInput.USERNAME);
        startingRowSet.setPassword(DataBaseInput.PASSWORD);
        startingRowSet.setCommand("SELECT * FROM service");
        startingRowSet.execute();

        if (startingRowSet.next()) {
            readFromDataBase();
        } else {
            readFromCSV(fileName);
        }
        startingRowSet.close();

        //setRequirementData((Service) obra.getRoot());
    }

    private void readFromDataBase() throws SQLException {
        boolean flag = true;
        Service service;
        String[] topics;

        do {
            service = new Service(
                    startingRowSet.getString(1),
                    startingRowSet.getShort(2),
                    startingRowSet.getString(3),
                    startingRowSet.getDate(4),
                    startingRowSet.getDate(5),
                    startingRowSet.getString(6),
                    startingRowSet.getString(7));
            if (flag) {
                this.obra = new ObraTreeModel(service);
                flag = false;
            } else {
                topics = service.estTopicos.split(":", 2);
                this.obra.addNode(topics, (Node) obra.getRoot(), service);
            }
        } while (startingRowSet.next());

        System.out.println("DADOS CARREGADOS COM SUCESSO!!!!!");
    }

    private void readFromCSV(String fileName) throws SQLException, IOException {
        List<String> lines = new ArrayList<String>(); // variável que receberá as linhas lidas do arquivo CSV
        CSVFileReader csvReader = new CSVFileReader(fileName);
        lines = csvReader.readCSVFile();
        // Eliminando o cabeçalho CSV da lista:
        String header = lines.remove(0);
        Iterator<String> it = lines.iterator();
        String line = it.next();

        Node lastNode;
        lastNode = new Service(line);
        // A primeira linha - "serviço 0" - como raiz da árvore
        this.obra = new ObraTreeModel(lastNode);
        insertDatabaseRow(lastNode);

        while (it.hasNext()) {
            line = it.next();
            lastNode = obra.addNode(line);
            insertDatabaseRow(lastNode);
        }
    }

    public ObraTreeModel getObra() {
        return obra;
    }

    private void insertDatabaseRow(Node lastNode) throws SQLException {
        Service service = (Service) lastNode;
        startingRowSet.moveToInsertRow();
        startingRowSet.updateString("topic_struct", service.estTopicos);
        startingRowSet.updateShort("ID", service.ID);
        startingRowSet.updateString("name", service.getDescricao());
        startingRowSet.updateDate("begin_date", (Date) service.dataInicio);
        startingRowSet.updateDate("end_date", (Date) service.dataTermino);
        startingRowSet.insertRow();
    }
}
