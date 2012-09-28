
import commons.Service;
import data.DataBaseManager;
import data.DataStructBuilder;
import gui.AresTableModel;
import gui.LoginForm;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.spi.SyncProviderException;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * testeGui.java
 *
 * Created on 06/06/2011, 16:12:27
 */
/**
 *
 * @author Hugo
 */
public class GUIStart extends javax.swing.JFrame {

    public GUIStart() {
        LoginForm loginForm;
        int mark = 0;
        do {
            loginForm = new LoginForm(this);
            loginForm.setVisible(true);
            if (loginForm.isCanceled()) {
                System.out.println("Programa finalizado com sucesso! (by Hugo)\n");
                System.exit(0);
            }
            try {
                dbManager = new DataBaseManager(loginForm.getUser(), loginForm.getPassword());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(GUIStart.class.getName()).log(Level.SEVERE, null, ex);
                System.exit(1);
            } catch (SQLException ex) {
                Logger.getLogger(GUIStart.class.getName()).log(Level.SEVERE, null, ex);
                System.exit(1);
            }
        } while (!dbManager.isConnected());

        DataStructBuilder structBuilder;
        try {
            structBuilder = new DataStructBuilder();
            this.treeModel = structBuilder.getObra(); // Faz referência ao objeto de modelagem da árvore de serviços.
            currentService = (Service) treeModel.getRoot();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GUIStart.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        } catch (SQLException ex) {
            Logger.getLogger(GUIStart.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        } catch (IOException ex) {
            Logger.getLogger(GUIStart.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }

        looks = javax.swing.UIManager.getInstalledLookAndFeels();
        lookNames = new String[looks.length];
        for (int i = 0; i < looks.length; i++) {
            lookNames[i] = looks[i].getName();
            if (lookNames[i].equalsIgnoreCase("nimbus")) {
                mark = i;
            }
        }

        handler = new ItemHandler(); // Handler da apar?ncia e do comportamento
        initComponents();
        lookSubMenus[mark].setSelected(true);
        this.addWindowListener(exitListener);
        setComponentsEnabled(servicePanel, false);
    }

    private void changeTheLookAndFeel(int value)
            throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, UnsupportedLookAndFeelException {
        changeTheLookAndFeel(looks[value].getClassName());
    }

    private void changeTheLookAndFeel(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        javax.swing.UIManager.setLookAndFeel(className);
        System.out.println(className);
        javax.swing.SwingUtilities.updateComponentTreeUI(this);
    }

    private class ItemHandler implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent event) {
            System.out.println("Mudou o estado...");
            for (int count = 0; count < lookSubMenus.length; count++) {
                if (lookSubMenus[count].isSelected()) {
                    try {
                        changeTheLookAndFeel(count); // muda apar?ncia e comportamento
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(GUIStart.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InstantiationException ex) {
                        Logger.getLogger(GUIStart.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(GUIStart.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnsupportedLookAndFeelException ex) {
                        Logger.getLogger(GUIStart.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } // fim do if
            } // fim do for
        } // fim do m?todo itemStateChanged
    } // fim da classe interna privada ItemHandler

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        servicePanel = new javax.swing.JPanel();
        subPanelProject = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        projectJTable = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jButton_addProject = new javax.swing.JButton();
        jButton_removeProject = new javax.swing.JButton();
        subPanelLabour = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        workmanJTable = new javax.swing.JTable();
        jButton_addWorkman = new javax.swing.JButton();
        subPanelMaterial = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jButton_addMaterial = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        materialJTable = new javax.swing.JTable();
        subPanelLogistics = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jButton_addLogistic = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        logisticJTable = new javax.swing.JTable();
        serviceDescriptionLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        startDateLabel = new javax.swing.JLabel();
        endDateLabel = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        scrlTree = new javax.swing.JScrollPane();
        treeServicos = new javax.swing.JTree(treeModel);
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        lookMenu = new javax.swing.JMenu();
        helpMenu = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Ares");

        jTabbedPane1.setMaximumSize(new java.awt.Dimension(1024, 640));
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(710, 555));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(710, 555));

        servicePanel.setMinimumSize(new java.awt.Dimension(720, 0));
        servicePanel.setPreferredSize(new java.awt.Dimension(720, 527));

        subPanelProject.setBorder(javax.swing.BorderFactory.createTitledBorder("Projeto"));
        subPanelProject.setMaximumSize(new java.awt.Dimension(342, 32767));
        subPanelProject.setPreferredSize(new java.awt.Dimension(342, 85));

        projectJTable.getSelectionModel().addListSelectionListener(new RowListener());
        projectJTable.getColumnModel().getSelectionModel().addListSelectionListener(new ColumnListener());
        projectJTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        //AresTableModel(String[] columnNames, Class[] types, DataBaseManager dbManager, int tableID)
        projectJTable.setModel(new AresTableModel(
            new String [] {
                "Descrição", "Responsável", "Definido", "Aprovado"
            },
            new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Boolean.class, java.lang.Boolean.class
            },
            dbManager, PROJECT)
    );
    projectJTable.setCellSelectionEnabled(true);
    projectJTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    projectJTable.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            projectJTableMouseClicked(evt);
        }
    });
    jScrollPane1.setViewportView(projectJTable);

    jLabel3.setText("Projetos");

    jButton_addProject.setText("Adicionar Projeto");
    jButton_addProject.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton_addProjectActionPerformed(evt);
        }
    });

    jButton_removeProject.setText("Remover Projeto(s)");
    jButton_removeProject.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton_removeProjectActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout subPanelProjectLayout = new javax.swing.GroupLayout(subPanelProject);
    subPanelProject.setLayout(subPanelProjectLayout);
    subPanelProjectLayout.setHorizontalGroup(
        subPanelProjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(subPanelProjectLayout.createSequentialGroup()
            .addGroup(subPanelProjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(subPanelProjectLayout.createSequentialGroup()
                    .addComponent(jButton_addProject)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jButton_removeProject))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE))
            .addContainerGap())
    );
    subPanelProjectLayout.setVerticalGroup(
        subPanelProjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(subPanelProjectLayout.createSequentialGroup()
            .addComponent(jLabel3)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(subPanelProjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jButton_addProject)
                .addComponent(jButton_removeProject)))
    );

    subPanelLabour.setBorder(javax.swing.BorderFactory.createTitledBorder("Mão de Obra"));
    subPanelLabour.setMaximumSize(new java.awt.Dimension(342, 32767));

    jLabel1.setText("Prestador do serviço:");

    workmanJTable.setModel(new AresTableModel(
        new String [] {
            "Descrição", "Responsável", "Disponível", "Contratada"
        },
        new Class [] {
            java.lang.String.class, java.lang.String.class, java.lang.Boolean.class, java.lang.Boolean.class
        },
        dbManager, WORKMAN)
    );
    workmanJTable.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            workmanJTableMouseClicked(evt);
        }
    });
    jScrollPane3.setViewportView(workmanJTable);

    jButton_addWorkman.setText("Adicionar Mao de Obra");
    jButton_addWorkman.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton_addWorkmanActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout subPanelLabourLayout = new javax.swing.GroupLayout(subPanelLabour);
    subPanelLabour.setLayout(subPanelLabourLayout);
    subPanelLabourLayout.setHorizontalGroup(
        subPanelLabourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(subPanelLabourLayout.createSequentialGroup()
            .addGroup(subPanelLabourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(subPanelLabourLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel1))
                .addComponent(jButton_addWorkman)
                .addGroup(subPanelLabourLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)))
            .addContainerGap())
    );
    subPanelLabourLayout.setVerticalGroup(
        subPanelLabourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(subPanelLabourLayout.createSequentialGroup()
            .addComponent(jLabel1)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton_addWorkman))
    );

    subPanelMaterial.setBorder(javax.swing.BorderFactory.createTitledBorder("Material"));
    subPanelMaterial.setMaximumSize(new java.awt.Dimension(366, 32767));

    jLabel2.setText("Lista de Materiais:");

    jButton_addMaterial.setText("Adicionar Material");
    jButton_addMaterial.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton_addMaterialActionPerformed(evt);
        }
    });

    materialJTable.getSelectionModel().addListSelectionListener(new RowListener());
    materialJTable.getColumnModel().getSelectionModel().addListSelectionListener(new ColumnListener());
    materialJTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    materialJTable.setModel(new AresTableModel(
        new String [] {
            "Descrição", "Responsável", "Solicitado", "in Loco", "Disponível"
        },
        new Class [] {
            java.lang.String.class, java.lang.String.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class
        },
        dbManager, MATERIAL)
    );
    materialJTable.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            materialJTableMouseClicked(evt);
        }
    });
    jScrollPane2.setViewportView(materialJTable);

    javax.swing.GroupLayout subPanelMaterialLayout = new javax.swing.GroupLayout(subPanelMaterial);
    subPanelMaterial.setLayout(subPanelMaterialLayout);
    subPanelMaterialLayout.setHorizontalGroup(
        subPanelMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(subPanelMaterialLayout.createSequentialGroup()
            .addGroup(subPanelMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(subPanelMaterialLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel2))
                .addComponent(jButton_addMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(169, 169, 169))
        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
    );
    subPanelMaterialLayout.setVerticalGroup(
        subPanelMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(subPanelMaterialLayout.createSequentialGroup()
            .addComponent(jLabel2)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jButton_addMaterial))
    );

    subPanelLogistics.setBorder(javax.swing.BorderFactory.createTitledBorder("Logística"));

    jLabel5.setText("Logística:");

    jButton_addLogistic.setText("Adicionar Logistica");
    jButton_addLogistic.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton_addLogisticActionPerformed(evt);
        }
    });

    logisticJTable.setModel(new AresTableModel(
        new String [] {
            "Descrição", "Responsável", "Definido", "Aprovado"
        },
        new Class [] {
            java.lang.String.class, java.lang.String.class, java.lang.Boolean.class, java.lang.Boolean.class
        },
        dbManager, LOGISTIC)
    );
    logisticJTable.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            logisticJTableMouseClicked(evt);
        }
    });
    jScrollPane4.setViewportView(logisticJTable);

    javax.swing.GroupLayout subPanelLogisticsLayout = new javax.swing.GroupLayout(subPanelLogistics);
    subPanelLogistics.setLayout(subPanelLogisticsLayout);
    subPanelLogisticsLayout.setHorizontalGroup(
        subPanelLogisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(subPanelLogisticsLayout.createSequentialGroup()
            .addGroup(subPanelLogisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(subPanelLogisticsLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel5))
                .addComponent(jButton_addLogistic))
            .addContainerGap())
        .addGroup(subPanelLogisticsLayout.createSequentialGroup()
            .addGap(1, 1, 1)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE))
    );
    subPanelLogisticsLayout.setVerticalGroup(
        subPanelLogisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(subPanelLogisticsLayout.createSequentialGroup()
            .addComponent(jLabel5)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton_addLogistic))
    );

    serviceDescriptionLabel.setFont(new java.awt.Font("sansserif", 1, 18));
    serviceDescriptionLabel.setText("OBRA");

    jLabel4.setText("Data prevista para o Inicio:");

    jLabel6.setText("Data prevista para Termino:");

    startDateLabel.setText("    /   /");

    endDateLabel.setText("    /   /");

    jButton5.setText("Salvar");
    jButton5.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton5ActionPerformed(evt);
        }
    });

    jLabel9.setText("Orçamento:");

    jTextField1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField1ActionPerformed(evt);
        }
    });

    jTextArea1.setColumns(20);
    jTextArea1.setLineWrap(true);
    jTextArea1.setRows(5);
    jScrollPane5.setViewportView(jTextArea1);

    javax.swing.GroupLayout servicePanelLayout = new javax.swing.GroupLayout(servicePanel);
    servicePanel.setLayout(servicePanelLayout);
    servicePanelLayout.setHorizontalGroup(
        servicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(servicePanelLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(servicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(serviceDescriptionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 791, Short.MAX_VALUE)
                .addGroup(servicePanelLayout.createSequentialGroup()
                    .addGap(6, 6, 6)
                    .addGroup(servicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(servicePanelLayout.createSequentialGroup()
                            .addGroup(servicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4)
                                .addComponent(jLabel6))
                            .addGap(63, 63, 63)
                            .addGroup(servicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(endDateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(startDateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)))
                        .addGroup(servicePanelLayout.createSequentialGroup()
                            .addComponent(jLabel9)
                            .addGap(18, 18, 18)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(18, 18, 18)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                    .addGap(12, 12, 12)
                    .addComponent(jButton5))
                .addGroup(servicePanelLayout.createSequentialGroup()
                    .addGroup(servicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(subPanelLabour, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(subPanelProject, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(servicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(subPanelLogistics, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(subPanelMaterial, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addContainerGap())
    );
    servicePanelLayout.setVerticalGroup(
        servicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(servicePanelLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(serviceDescriptionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(servicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(servicePanelLayout.createSequentialGroup()
                    .addGroup(servicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(startDateLabel))
                    .addGap(10, 10, 10)
                    .addGroup(servicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(endDateLabel))
                    .addGap(18, 18, 18)
                    .addGroup(servicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(22, 22, 22))
                .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(servicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(subPanelProject, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                .addComponent(subPanelMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(servicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(subPanelLabour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(subPanelLogistics, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap())
    );

    jTabbedPane1.addTab("Serviço", servicePanel);

    jSplitPane1.setRightComponent(jTabbedPane1);

    scrlTree.setAutoscrolls(true);
    scrlTree.setMaximumSize(new java.awt.Dimension(400, 600));
    scrlTree.setMinimumSize(new java.awt.Dimension(120, 200));
    scrlTree.setPreferredSize(new java.awt.Dimension(250, 363));

    treeServicos.setAutoscrolls(true);
    treeServicos.setShowsRootHandles(true);
    treeServicos.setFocusCycleRoot(true);
    treeServicos.setMaximumSize(new java.awt.Dimension(2000, 32000));
    treeServicos.setMinimumSize(new java.awt.Dimension(200, 200));
    treeServicos.setPreferredSize(new java.awt.Dimension(1000, 360));
    treeServicos.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
        public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
            treeServicosValueChanged(evt);
        }
    });
    scrlTree.setViewportView(treeServicos);

    jSplitPane1.setLeftComponent(scrlTree);

    fileMenu.setText("Arquivo");

    jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
    jMenuItem2.setText("Salvar");
    fileMenu.add(jMenuItem2);

    jMenuBar1.add(fileMenu);

    editMenu.setText("Editar");

    lookMenu.setText("Aparencia");
    group = new javax.swing.ButtonGroup(); // grupo de botões para aparência e comportamento

    lookSubMenus = new javax.swing.JRadioButtonMenuItem[lookNames.length];
    for(int count=0; count < lookSubMenus.length; count++)
    {
        lookSubMenus[count] = new javax.swing.JRadioButtonMenuItem(lookNames[count]);
        lookSubMenus[count].addItemListener(handler); // adiciona handler
        group.add(lookSubMenus[count]); // adiciona botões de opão ao grupo
        lookMenu.add(lookSubMenus[count]);
    }
    lookSubMenus[0].setSelected(true);
    editMenu.add(lookMenu);

    jMenuBar1.add(editMenu);

    helpMenu.setText("Ajuda");

    jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
    jMenuItem1.setText("Sobre");
    helpMenu.add(jMenuItem1);

    jMenuBar1.add(helpMenu);

    setJMenuBar(jMenuBar1);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 935, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 595, Short.MAX_VALUE)
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void treeServicosValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_treeServicosValueChanged
        if (currentService != null && currentService.isLeaf()) {
            try {
                dbManager.acceptChanges();
                dbManager.updateService(jTextArea1.getText(), jTextField1.getText());
            } catch (SQLException ex) {
                Logger.getLogger(GUIStart.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        currentService = (Service) treeServicos.getLastSelectedPathComponent();
        System.out.printf("Noh selecionado: %s\n", currentService);

        serviceDescriptionLabel.setText(currentService.getDescricao());
        startDateLabel.setText(currentService.dataInicio.toString());
        endDateLabel.setText(currentService.dataTermino.toString());

        if (currentService == null) {
            return;
        } else {
            serviceDescriptionLabel.setText(currentService.getDescricao());
            if (currentService.isLeaf()) {
                try {
                    setComponentsEnabled(servicePanel, true);
                    loadServiceData(currentService);

                    AresTableModel model;
                    model = (AresTableModel) projectJTable.getModel();
                    model.executeQuery(currentService.ID);
                    model = (AresTableModel) materialJTable.getModel();
                    model.executeQuery(currentService.ID);
                    model = (AresTableModel) logisticJTable.getModel();
                    model.executeQuery(currentService.ID);
                    model = (AresTableModel) workmanJTable.getModel();
                    model.executeQuery(currentService.ID);

                    jTextArea1.setText(currentService.comments);
                    jTextField1.setText(currentService.budget);
                } catch (SQLException ex) {
                    Logger.getLogger(GUIStart.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else { // é apenas um serviço.
                jTextArea1.setText("");
                jTextField1.setText("");
                clearTables();
                setComponentsEnabled(servicePanel, false);
            }
        }
        System.out.println("-----------------------------");

    }//GEN-LAST:event_treeServicosValueChanged

    private void jButton_addWorkmanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_addWorkmanActionPerformed
        clearSelection(WORKMAN);
        AresTableModel model = (AresTableModel) workmanJTable.getModel();
        model.addRow(currentService.ID);
    }//GEN-LAST:event_jButton_addWorkmanActionPerformed

    private void jButton_addProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_addProjectActionPerformed
        clearSelection(PROJECT);
        AresTableModel model = (AresTableModel) projectJTable.getModel();
        model.addRow(currentService.ID);
    }//GEN-LAST:event_jButton_addProjectActionPerformed

    private void workmanJTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_workmanJTableMouseClicked
        clearSelection(WORKMAN);
    }//GEN-LAST:event_workmanJTableMouseClicked

    private void materialJTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_materialJTableMouseClicked
        clearSelection(MATERIAL);
    }//GEN-LAST:event_materialJTableMouseClicked

    private void logisticJTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logisticJTableMouseClicked
        clearSelection(LOGISTIC);
    }//GEN-LAST:event_logisticJTableMouseClicked

    private void projectJTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_projectJTableMouseClicked
        jButton_removeProject.setEnabled(true);
        clearSelection(PROJECT);
    }//GEN-LAST:event_projectJTableMouseClicked

    private void jButton_addMaterialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_addMaterialActionPerformed
        clearSelection(MATERIAL);
        AresTableModel model = (AresTableModel) materialJTable.getModel();
        model.addRow(currentService.ID);
    }//GEN-LAST:event_jButton_addMaterialActionPerformed

    private void jButton_addLogisticActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_addLogisticActionPerformed
        clearSelection(LOGISTIC);
        AresTableModel model = (AresTableModel) logisticJTable.getModel();
        model.addRow(currentService.ID);
    }//GEN-LAST:event_jButton_addLogisticActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        try {
            dbManager.acceptChanges();
        } catch (SyncProviderException ex) {
            Logger.getLogger(GUIStart.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GUIStart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton_removeProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_removeProjectActionPerformed
        int row = projectJTable.getSelectedRow();
        System.out.printf("Linha selecionada: %d\n", row);
        AresTableModel model = (AresTableModel) projectJTable.getModel();
        model.removeRow(row);
    }//GEN-LAST:event_jButton_removeProjectActionPerformed

    private void clearSelection(int tableId) {
        if (tableId != PROJECT) {
            projectJTable.clearSelection();
            jButton_removeProject.setEnabled(false);
        }
        if (tableId != MATERIAL) {
            materialJTable.clearSelection();
        }
        if (tableId != WORKMAN) {
            workmanJTable.clearSelection();
        }
        if (tableId != LOGISTIC) {
            logisticJTable.clearSelection();
        }
    }

    private void setComponentsEnabled(Container component, boolean enabled) {
        Component[] com = component.getComponents();
        for (int a = 0; a < com.length; a++) {
            com[a].setEnabled(enabled);
            if (com[a] instanceof Container) {
                setComponentsEnabled((Container) com[a], enabled);
            }
        }
    }

    private void loadServiceData(Service service) throws SQLException {
        CachedRowSet rowSet = dbManager.executeServiceQuery(service.ID);
        if (rowSet.next()) {
            currentService.budget = rowSet.getString(6);
            currentService.comments = rowSet.getString(7);
        }
    }

    private void clearTables() {
        AresTableModel amodel;

        amodel = (AresTableModel) projectJTable.getModel();
        amodel.setRowCount(0);
        amodel = (AresTableModel) logisticJTable.getModel();
        amodel.setRowCount(0);
        amodel = (AresTableModel) materialJTable.getModel();
        amodel.setRowCount(0);
        amodel = (AresTableModel) workmanJTable.getModel();
        amodel.setRowCount(0);
    }

    private class RowListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent lse) {
            if (lse.getValueIsAdjusting()) {
                return;
            }
            //tableRow = projectJTable.getSelectionModel().getLeadSelectionIndex();
        }
    }

    private class ColumnListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent event) {
            if (event.getValueIsAdjusting()) {
                return;
            }
            //tableCol = projectJTable.getColumnModel().getSelectionModel().getLeadSelectionIndex();
        }
    }

    /**
     * @param args the command line arguments
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws IOException  
     */
    public static void main(String args[]) throws SQLException, ClassNotFoundException, IOException {
        GUIStart tela = new GUIStart();
        tela.setVisible(true);
    }
    private final int MATERIAL = 0;
    private final int LOGISTIC = 1;
    private final int PROJECT = 2;
    private final int WORKMAN = 3;
    private String lookNames[];
    private ItemHandler handler;
    private DataBaseManager dbManager;
    private Service currentService;
    private javax.swing.tree.TreeModel treeModel;
    private javax.swing.ButtonGroup group; // grupo para botões de opção
    private javax.swing.JRadioButtonMenuItem lookSubMenus[]; // submenus para selecionar apar?ncias
    private javax.swing.UIManager.LookAndFeelInfo looks[]; // aparências e comportamentos
    private WindowListener exitListener = new WindowAdapter() {

        @Override
        public void windowClosing(WindowEvent e) {
            int confirm = javax.swing.JOptionPane.showOptionDialog(null,
                    "Você tem certeza que deseja fechar o Ares?", "Tá cedo, sem pressa!!!",
                    javax.swing.JOptionPane.YES_NO_OPTION,
                    javax.swing.JOptionPane.QUESTION_MESSAGE,
                    null, null, null);
            if (confirm == 0) {
                if (currentService.isLeaf()) {
                    try {
                        dbManager.updateService(jTextArea1.getText(), jTextField1.getText());
                        dbManager.acceptChanges();
                        dbManager.closeConnection();
                    } catch (SQLException ex) {
                        Logger.getLogger(GUIStart.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                System.out.println("Programa finalizado com sucesso! (by Hugo)\n");
                System.exit(0);
            }
        }
    };
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu editMenu;
    private javax.swing.JLabel endDateLabel;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton_addLogistic;
    private javax.swing.JButton jButton_addMaterial;
    private javax.swing.JButton jButton_addProject;
    private javax.swing.JButton jButton_addWorkman;
    private javax.swing.JButton jButton_removeProject;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable logisticJTable;
    private javax.swing.JMenu lookMenu;
    private javax.swing.JTable materialJTable;
    private javax.swing.JTable projectJTable;
    private javax.swing.JScrollPane scrlTree;
    private javax.swing.JLabel serviceDescriptionLabel;
    private javax.swing.JPanel servicePanel;
    private javax.swing.JLabel startDateLabel;
    private javax.swing.JPanel subPanelLabour;
    private javax.swing.JPanel subPanelLogistics;
    private javax.swing.JPanel subPanelMaterial;
    private javax.swing.JPanel subPanelProject;
    private javax.swing.JTree treeServicos;
    private javax.swing.JTable workmanJTable;
    // End of variables declaration//GEN-END:variables
}
