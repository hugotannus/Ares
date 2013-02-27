package tests;


import gui.ObraTreeModel;
import data.CSVFileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hugo
 */
public class Main
{
    public static void main(String[] args) {
        List<String> lines = new ArrayList<String>();
        CSVFileReader csvReader = new CSVFileReader("utf8-AGO-sede-R04.csv");
        try {
            lines = csvReader.readCSVFile();
        } catch (IOException ex) {
            Logger.getLogger(Estrutura.class.getName()).log(Level.SEVERE, null, ex);
        }
        Iterator<String> it = lines.iterator();
        String line = it.next();
        ObraTreeModel obra = new ObraTreeModel(new commons.Service(line));
        
        while(it.hasNext())
        {
            line = it.next();
            obra.addNode(line);
        }
        Estrutura tela = new Estrutura(obra);
        tela.setVisible(true);
    }
}