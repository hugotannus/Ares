///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package obsolete.tests;
//
//import data.DataBaseManager;
//import java.util.Scanner;
//import javax.sql.rowset.CachedRowSet;
//
///**
// *
// * @author hugo
// */
//public class DataManagerTest {
//    static final String USERNAME = "ares";
//    static final char[] PASSWORD = {'v', 'e', 'r', 'n', 'a', 'c', 'u', 'l', 'a'};
//
//    public static void main(String[] args) throws Exception {
//        Scanner input = new Scanner(System.in);
//        DataBaseManager dbManager = new DataBaseManager(USERNAME, PASSWORD);
//        CachedRowSet crs;
//
//        int table_ID, service_ID;
//        System.out.print("Numero de servico e tabela: ");
//        service_ID = input.nextInt();
//        table_ID = input.nextInt();
//
//        dbManager.executeQuery((short)service_ID, table_ID);
//        //dbManager.executeProjectQuery((short)service_ID);
//        crs = dbManager.getRowSet(table_ID);
//        //crs = dbManager.getProjectRowSet();
//        crs.beforeFirst();
//
//        dbManager.addRow(table_ID, (short)service_ID, "funcionar", "hugo");
//        //dbManager.addProjectRow((short)service_ID, "funcionar", "hugo");
//        dbManager.acceptChanges(table_ID);
//        System.out.println("--------------------");
//        while(crs.next()) {
//            System.out.print("ID: " + crs.getString(1) + "\t");
//            System.out.println("Name: " + crs.getString("name"));
//        }
//    }
//}
