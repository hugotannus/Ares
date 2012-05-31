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

import data.DataBaseManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.CachedRowSet;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hugo
 */
public class AresTableModel extends DefaultTableModel {

    private DataBaseManager dbManager;
    private CachedRowSet rowSet;
    private Class[] types;
    private int numberOfRows;
    private PreparedStatement queryStmt;
    private PreparedStatement addStmt;
    private final int TABLE_ID;

    public AresTableModel(String[] columnNames, Class[] types, DataBaseManager dbManager, int tableID) {
        super(new Object[][]{}, columnNames);
        this.types = types;
        this.TABLE_ID = tableID;
        this.dbManager = dbManager;
        this.rowSet = dbManager.getRowSet(tableID);
    }

    @Override
    public void addRow(Object obj[]) {
        try {
            dbManager.addRow(TABLE_ID, (Short) obj[0], (String) obj[1], (String) obj[2]);
        } catch (SQLException ex) {
            Logger.getLogger(AresTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        fireTableRowsInserted(numberOfRows, numberOfRows);
        numberOfRows++;
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
    public int getRowCount() {
        return numberOfRows;
    }

    @Override
    public Object getValueAt(int row, int column) {
        try {
            rowSet.absolute(row + 1);
            return rowSet.getObject(column + 1);
        }
        catch (SQLException sqlException) {
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

    @Override
    public void setValueAt(Object aValue, int row, int column) {
        try {
            rowSet.absolute(row);
            rowSet.updateObject(column, aValue);
        } catch (SQLException ex) {
            Logger.getLogger(AresTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        fireTableCellUpdated(row, column);
    }

    public void setQuery(short serviceID, int tableID) throws SQLException{
        rowSet = dbManager.executeQuery(serviceID, tableID);
        numberOfRows = rowSet.size();
        fireTableDataChanged();
    }
}
