/* (05/03/2012)
 * Essa classe servirá para ajudar a manipular os dados em cada
 * uma das quatro jTables existentes no Ares, que estão desti-
 * nadas às tabelas MySql: "Material", "Logistics", "Project" e
 * "Workmanship".
 *
 * Para cada uma dessas tabelas, a QUANTIDADE e o NOME das co-
 * lunas permanecerão estáticos. Contudo, para cada nó "Service"
 * selecionado, uma nova consulta MySQL deverá ser refeita, o
 * que implica em um novo conjunto de valores para o objeto
 * "ResultSet" ou para o objeto "RowSet". Cada uma dessas tabe-
 * las deverá, ainda, permitir que sejam alteradas, e serem ca-
 * pazes de enviar sinais de atualização para o objeto "Rowset",
 * que fará a comunicação com o banco de dados MySQL.
 *
 * Avaliando melhor, talvez não precise utilizar um AbstractTableModel.
 * Tudo o que eu preciso garantir é que as colunas sejam
 * atualizadas de acordo com cada consulta.
 *
 * ALTERNATIVAS:
 * 1) Para cada uma das tabelas, retornar sempre a mesma
 * quantidade de colunas, e os mesmos nomes para colunas. Estes
 * nomes podem ser definidos no construtor da classe.
 * 2) Informações que seriam aproveitadas de cada consulta SQL:
 *  - quantidade de linhas existentes.
 *  - valor de cada célula
 *
 * (30/05/2012)
 * A implementação de uma TableModel será, de fato, necessária, já que essa é a
 * maneira mais fácil de se implementar a inserção e a atualização de dados de
 * células na tabela. Para tanto, basta sobrescrever os métodos 'setValueAt' e
 * 'addRow'.
 */
package gui;

import com.sun.rowset.WebRowSetImpl;
import comunication.ServerServicesInterface;
import java.io.StringReader;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.WebRowSet;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author hugo
 */
public class AresTableModel extends AbstractTableModel {

    private final String[] columnNames;
    private ServerServicesInterface aresServices;
    private WebRowSet rowSet;
    private Class[] types;
    private Vector<Object[]> values;
    private int numberOfRows;
    private int TABLE_ID;
    private final int SQL_ROW_CORRECTION = 1;
    // Como não estamos pegando as colunas 'service_ID' e 'ID', então,
    // é preciso deslocar a referência de colunas em mais duas unidades.
    private final int SQL_COL_CORRECTION = 3;
    private int userID;

    public AresTableModel(String[] columnNames, Class[] types, ServerServicesInterface aresServices, int tableID, int user_id) {
        //super(new Object[][]{}, columnNames);
        this.columnNames = columnNames;
        this.types = types;
        this.TABLE_ID = tableID;
        this.aresServices = aresServices;
        this.userID = user_id;
        try {
            this.rowSet = new WebRowSetImpl();
        } catch (SQLException ex) {
            Logger.getLogger(AresTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addRow(int user_id, short service_ID) {
        values.addElement(new Object[]{"nome", "responsável", false, false, false});
        System.out.println("Tentou adicionar um projeto...");
        System.out.printf("Tamanho do rowSet antes: %d...\n", rowSet.size());
        try {
            aresServices.addRow(user_id, TABLE_ID, service_ID, "nome", "responsavel");
        } catch (SQLException ex) {
            Logger.getLogger(AresTableModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(AresTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.printf("... e depois: %d.\n", rowSet.size());

        numberOfRows++;
        this.fireTableRowsInserted(numberOfRows, numberOfRows);
    }

    public void executeQuery(int user_id, short serviceID) throws SQLException, RemoteException {
        System.out.printf("executeQuery: user: %d  service: %d\n", user_id, serviceID);
        String xmlData = aresServices.executeQuery(user_id, serviceID, TABLE_ID);
        StringReader reader = new StringReader(xmlData);
        rowSet.release();
        rowSet.readXml(reader);
        
        numberOfRows = rowSet.size();
        
        values = new Vector<Object[]>(numberOfRows);
        
        for (int row = 0; row < numberOfRows; row++) {
        
            rowSet.absolute(row + SQL_ROW_CORRECTION);
            Object obj[] = new Object[getColumnCount()];
            
            for (int col = 0; col < getColumnCount(); col++) {
                obj[col] = rowSet.getObject(col + SQL_COL_CORRECTION);
            }
            
            values.addElement(obj);
        }
        System.out.printf("numberOfRows: %d\n", numberOfRows);
        fireTableDataChanged();
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        return types[columnIndex];
    }

    @Override
    public int getColumnCount() {
        return types.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public int getRowCount() {
        return numberOfRows;
    }

    @Override
    public Object getValueAt(int row, int column) {
        Object obj[] = values.elementAt(row);
        if (obj[column] != null) {
            return obj[column];
        }
        return "";
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        if (column <= 2) {
            return true;
        }
        return (Boolean) getValueAt(row, column - 1);
    }

    public void removeRow(int user_id, int row) throws SQLException, RemoteException {
        values.remove(row);

        aresServices.updateCellTable(user_id, TABLE_ID,
                (row + SQL_ROW_CORRECTION), 7, Boolean.FALSE);

        numberOfRows--;
        this.fireTableRowsDeleted(row, row);
    }

    @Override
    public void setValueAt(Object aValue, int row, int column){
        Object obj[] = values.elementAt(row);
        obj[column] = aValue;
        try {
            aresServices.updateCellTable(userID, TABLE_ID,
                    row + SQL_ROW_CORRECTION,
                    column + SQL_COL_CORRECTION,
                    aValue);
        } catch (SQLException ex) {
            Logger.getLogger(AresTableModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(AresTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        fireTableCellUpdated(row, column);
    }

    public void setRowCount(int i) {
        numberOfRows = i;
        fireTableDataChanged();
    }
}
