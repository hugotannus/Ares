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
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.CachedRowSet;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hugo
 */
public class AresTableModel extends DefaultTableModel {

    private final String[] columnNames;
    private DataBaseManager dbManager;
    private CachedRowSet rowSet;
    private Class[] types;
    private int numberOfRows;
    private final int TABLE_ID;
    private final int SQL_ROW_CORRECTION = 1;
    // Como não estamos pegando as colunas 'service_ID' e 'ID', então,
    // é preciso deslocar a referência de colunas em mais duas unidades.
    private final int SQL_COL_CORRECTION = 3;

    public AresTableModel(String[] columnNames, Class[] types, DataBaseManager dbManager, int tableID) {
        //super(new Object[][]{}, columnNames);
        this.columnNames = columnNames;
        this.types = types;
        this.TABLE_ID = tableID;
        this.dbManager = dbManager;
        this.rowSet = dbManager.getRowSet(tableID);
    }

 
    public void addRow(short service_ID) {
        try {
            dbManager.addRow(TABLE_ID, service_ID, "nome", "responsavel");
            rowSet = dbManager.getRowSet(TABLE_ID);
        } catch (SQLException ex) {
            Logger.getLogger(AresTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        int line = numberOfRows++;
        this.setRowCount(line+1);
        this.fireTableRowsInserted(line, line);
        //this.fireTableDataChanged();
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
        try {
            // Deslocamento de índices requerido para obter corretamente os dados
            // desejados.
            rowSet.absolute(row + SQL_ROW_CORRECTION);
            return rowSet.getObject(column + SQL_COL_CORRECTION);
        } catch (SQLException sqlException) {
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
            // Aqui também precisamos realizar o deslocamento de índices;
            /*
            rowSet.absolute(row + SQL_ROW_CORRECTION);
            rowSet.updateObject(column + SQL_COL_CORRECTION, aValue);
            rowSet.updateRow();
             *
             */
            dbManager.updateCellTable(TABLE_ID,
                    row + SQL_ROW_CORRECTION,
                    column + SQL_COL_CORRECTION,
                    aValue);
        } catch (SQLException ex) {
            Logger.getLogger(AresTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        fireTableCellUpdated(row, column);
    }

    public void setQuery(short serviceID, int tableID) throws SQLException {
        rowSet = dbManager.executeQuery(serviceID, tableID);
        numberOfRows = rowSet.size();
        System.out.printf("numberOfRows: %d\n", numberOfRows);
        fireTableDataChanged();
    }
}
