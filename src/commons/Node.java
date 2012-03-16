package commons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Classe genérica para todos os itens a serem geridos pelo software.
 * A princípio, todas as outras classes em 'commons' deverão herdar a classe
 * 'Node'; e, portanto, esta classe deveria ser do tipo abstrata. Contudo, como
 * não há ainda diferenças substanciais entre a implementação dos métodos de ca-
 * da uma das classes herdeiras, afim de minimizar a quantidade de linhas de có-
 * digo, 'Node' permanecerá sedo uma classe de um tipo "comum".
 *
 */
/**
 *
 * @author Hugo Cabral Tannús
 */
public abstract class Node {

    public String estTopicos;
    protected String descricao;
    public short ID;
    protected boolean ready;
    protected Node parent;
    //public int fatherID;
    protected List<Node> childrem = new ArrayList<Node>();
    protected Iterator<Node> childIt = childrem.iterator();

    public void addChild(Node child) {
        this.childrem.add(child);
    }

    public List<Node> getChildrem() {
        return Collections.unmodifiableList(this.childrem);
    }

    public Node getChildAt(int index) {
        return this.childrem.get(index);
    }

    public String getDescricao() {
        return descricao;
    }

    public Node firstChild() {
        return getChildAt(1);
    }
    
    public boolean isLeaf() {
        return childrem.isEmpty();
    }

    public void setReady(boolean ready) {
        if (isLeaf()) {
            this.ready = ready;
        } else {
            JOptionPane.showMessageDialog(null, "Você não tem permissão para modificar o status desse item",
                    "Oops!!!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Node getParent() {
        return this.parent;
    }

    public void setParent(Node father) {
        this.parent = father;
    }
        
    public abstract boolean getAllowsChildren();
    public abstract boolean isReady();
    
    @Override
    public String toString() {
        return this.descricao.substring(0,1) + this.descricao.substring(1).toLowerCase();
    }
}
