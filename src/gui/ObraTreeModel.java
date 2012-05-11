/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import commons.Node;
import commons.Service;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author hugo
 */
public class ObraTreeModel implements TreeModel {

    private String raiz = "Serviços";
    private List<TreeModelListener> listeners = new ArrayList<TreeModelListener>();
    private Node root = null;

    public ObraTreeModel(Node root) {
        this.root = root;
    }

    public Node addNode(String line) {
        Node node = new Service(line);
        String[] topics = node.estTopicos.split(":",2);
        //System.out.printf("Estrutura de topicos do node: %s\n", estTopic);
        //System.out.println(topics.toString());
        return addNode(topics, root, node);
    }

    public Node addNode(String[] topics, Node parent, Node node) {
        /*
        System.out.print("topics[ ");
        for(int i=0; i<topics.length; i++)
           System.out.printf("%s, ",topics[i]);
        System.out.println("]");
        */
        if (topics.length == 1)
        {
            node.setParent(parent);
            parent.addChild(node);
        }
        else
        {
            Node p = parent.getChildAt(Integer.parseInt(topics[0])-1);
            String[] t = topics[1].split(":", 2);
            addNode(t, p, node);
        }
        return node;
    }
    
    public Object getRoot() {
        return this.root;
    }

    public Object getChild(Object parent, int index) {
        Node p = (Node)parent;
        return p.getChildAt(index);
    }

    public int getChildCount(Object parent) {
        if (parent instanceof Node)
        {
            return ((Node) parent).getChildrem().size();
        }
        
        throw new IllegalArgumentException("Invalid parent class"
                + parent.getClass().getSimpleName());
    }

    public boolean isLeaf(Object node) {
        Node n = (Node) node;
        return n.isLeaf();
    }

    public void valueForPathChanged(TreePath path, Object newValue) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getIndexOfChild(Object parent, Object child) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addTreeModelListener(TreeModelListener l) {
        listeners.add(l);
    }

    public void removeTreeModelListener(TreeModelListener l) {
        listeners.remove(l);
    }
}
