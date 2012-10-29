package gui;

// Fig. 25.28: ResultSetTableModel.java
// A TableModel that supplies ResultSet data to a JTable.
import data.DataBaseManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import javax.sql.rowset.CachedRowSet;
import javax.swing.table.AbstractTableModel;

// ResultSet rows and columns are counted from 1 and JTable
// rows and columns are counted from 0. When processing
// ResultSet rows or columns for use in a JTable, it is
// necessary to add 1 to the row or column number to manipulate
// the appropriate ResultSet column (i.e., JTable column 0 is
// ResultSet column 1 and JTable row 0 is ResultSet row 1).
public class ReportTableModel extends AbstractTableModel {

    private final String[] columnNames;
    private DataBaseManager dbManager;
    private CachedRowSet rowSet;
    private Class[] types;
    private Vector<Object[]> values;
    private int numberOfRows;
    private int TABLE_ID;
    private final int SQL_ROW_CORRECTION = 1;
    // Como não estamos pegando as colunas 'service_ID' e 'ID', então,
    // é preciso deslocar a referência de colunas em mais duas unidades.
    private final int SQL_COL_CORRECTION = 3;

    public ReportTableModel(DataBaseManager dbManager) {
        this(dbManager);
    }
    
    public ReportTableModel(String driver, String url,
            String username, String password, String query)
            throws SQLException, ClassNotFoundException {
        Class.forName(driver);
        connection = DriverManager.getConnection(url, username, password);
        statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        connectedToDatabase = true;
        setQuery(query);
    } 
    
    @Override
    public Class getColumnClass(int column) throws IllegalStateException {
        if (!connectedToDatabase) {
            throw new IllegalStateException("Not Connected to Database");
        }
        
        try {
            String className = metaData.getColumnClassName(column + 1);

            return Class.forName(className);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }

        return Object.class; 
    } 

    public int getColumnCount() throws IllegalStateException {
        // ensure database connection is available
        if (!connectedToDatabase) {
            throw new IllegalStateException("Not Connected to Database");
        }

        // determine number of columns
        try {
            return metaData.getColumnCount();
        } // end try
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } // end catch

        return 0; // if problems occur above, return 0 for number of columns
    } // end method getColumnCount

    // get name of a particular column in ResultSet
    @Override
    public String getColumnName(int column) throws IllegalStateException {
        // ensure database connection is available
        if (!connectedToDatabase) {
            throw new IllegalStateException("Not Connected to Database");
        }

        // determine column name
        try {
            return metaData.getColumnName(column + 1);
        } // end try
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } // end catch

        return ""; // if problems, return empty string for column name
    } // end method getColumnName

    // return number of rows in ResultSet
    public int getRowCount() throws IllegalStateException {
        // ensure database connection is available
        if (!connectedToDatabase) {
            throw new IllegalStateException("Not Connected to Database");
        }

        return numberOfRows;
    } // end method getRowCount

    // obtain value in particular row and column
    public Object getValueAt(int row, int column)
            throws IllegalStateException {
        // ensure database connection is available
        if (!connectedToDatabase) {
            throw new IllegalStateException("Not Connected to Database");
        }

        // obtain a value at specified ResultSet row and column
        try {
            resultSet.absolute(row + 1);
            return resultSet.getObject(column + 1);
        } // end try
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } // end catch

        return ""; // if problems, return empty string object
    } // end method getValueAt

    /* Notas (Hugo):
     * Cada vez que uma nova query é pedida pelo usuário, todos os metadados são
     * também atualizados. São estes metadados que irão permitir que a quantida-
     * de de colunas, bem como os nomes das mesmas, sejam modificadas dinamica-
     * mente.
     */
    // set new database query string
    public void setQuery(String query)
            throws SQLException, IllegalStateException {
        // ensure database connection is available
        if (!connectedToDatabase) {
            throw new IllegalStateException("Not Connected to Database");
        }

        // specify query and execute it
        resultSet = statement.executeQuery(query);

        // obtain meta data for ResultSet
        metaData = resultSet.getMetaData();

        // determine number of rows in ResultSet
        resultSet.last();                   // move to last row
        numberOfRows = resultSet.getRow();  // get row number

        // notify JTable that model has changed
        fireTableStructureChanged();
    } // end method setQuery

    // close Statement and Connection
    public void disconnectFromDatabase() {
        if (!connectedToDatabase) {
            return;
        }

        // close Statement and Connection
        try {
            statement.close();
            connection.close();
        } // end try
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } // end catch
        finally // update database connection status
        {
            connectedToDatabase = false;
        } // end finally
    } // end method disconnectFromDatabase
}  // end class ResultSetTableModel
/**************************************************************************
 * (C) Copyright 1992-2005 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/
