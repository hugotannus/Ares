/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import commons.Service;
import data.CSVFileReader;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author hugo
 */
public class Estrutura extends JDialog {

    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JScrollPane scrlTree = null;
    private JTree treeServicos = null;
    private TreeModel treeModel;

    public Estrutura(TreeModel treeModel) {
        super();
        this.treeModel = treeModel;
        initialize();
    }

    private void initialize() {
        Dimension minimumSize = new Dimension(800, 600);
        this.setSize(minimumSize);
        this.setLocationRelativeTo(null);
        this.setTitle("Tree");
        this.setContentPane(getJContentPane());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getScrlTree(), BorderLayout.CENTER);
        }
        return jContentPane;
    }

    private JScrollPane getScrlTree() {
        if (scrlTree == null) {
            scrlTree = new JScrollPane();
            scrlTree.setViewportView(getTreeServicos());
        }
        return scrlTree;
    }

    private JTree getTreeServicos() {
        if (treeServicos == null) {
            treeServicos = new JTree(treeModel);
            //treeServicos.setRootVisible(true);
            treeServicos.setShowsRootHandles(true);
            treeServicos.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

            TreeSelectionListener treeListener = new TreeSelectionListener() {

                @Override
                public void valueChanged(TreeSelectionEvent treeSelectionEvent) {
                    Service node = (Service) treeServicos.getLastSelectedPathComponent();

                    if (node == null) //Nothing is selected.	
                    {
                        return;
                    }
                    System.out.printf("Noh selecionado: %s\n", node);
                    System.out.printf("Data de inicio: %s\n", node.dataInicio);
                    System.out.printf("Data de termino: %s\n", node.dataTermino);
                    System.out.println("-----------------------------");
                    /*
                    Object nodeInfo = node.getUserObject();
                    if (node.isLeaf()) {
                        BookInfo book = (BookInfo) nodeInfo;
                        displayURL(book.bookURL);
                    } else {
                        displayURL(helpURL);
                    }
                     *
                     *
                   
                    JTree tree = (JTree) treeSelectionEvent.getSource();
                    TreeModel object = tree.getModel();

                    System.out.print(object);
                    //System.out.printf(" %d\n", index);
                    System.out.println("getPath:");
                    System.out.println(treeSelectionEvent.getPath());
                    System.out.println("getNewLeadSelectionPath:");
                    System.out.println(treeSelectionEvent.getNewLeadSelectionPath());
                    System.out.println("getOldLeadSelectionPath:");
                    System.out.println(treeSelectionEvent.getOldLeadSelectionPath());
                    //System.out.println(object.getPath());
                     * 
                     */
                }
            };
            treeServicos.addTreeSelectionListener(treeListener);
        }
        return treeServicos;
    }

    public static void main(String[] args) {
        List<String> lines = new ArrayList<String>(); // variável que receberá as linhas lidas do arquivo CSV
        CSVFileReader csvReader = new CSVFileReader("utf8-AGO-sede-R04.csv");
        try {
            lines = csvReader.readCSVFile();
        } catch (IOException ex) {
            Logger.getLogger(Estrutura.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Eliminando o cabeçalho CSV da lista:
        String header = lines.remove(0);
        //System.out.println(header);
        Iterator<String> it = lines.iterator();
        String line = it.next();
        // A primeira linha - "serviço 0" - como raiz da árvore
        ObraTreeModel obra = new ObraTreeModel(new commons.Service(line));

        while (it.hasNext()) {
            line = it.next();
            obra.addNode(line);
        }
        Estrutura tela = new Estrutura(obra);
        tela.setVisible(true);
    }
}

// Número_da_estrutura_de_tópicos;Id_exclusiva;Nome;Início;Término
