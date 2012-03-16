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
 */

package gui;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author hugo
 */
public class AresTableModel extends AbstractTableModel {

    public int getRowCount() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getColumnCount() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
