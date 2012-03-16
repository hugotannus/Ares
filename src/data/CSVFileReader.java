/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hugo
 */
public class CSVFileReader {

    private File file;
    private FileReader fileReader;
    private BufferedReader buffer;
    private List<String> lines = new ArrayList();

    public CSVFileReader(String filename) {
        file = new File(filename);
        System.out.println(file.getAbsolutePath());
        try {
            openCSVFile();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CSVFileReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void openCSVFile() throws FileNotFoundException {
        fileReader = new FileReader(file.getAbsolutePath());
        buffer = new BufferedReader(fileReader);
    }

    public List<String> readCSVFile() throws IOException {
        String line = null;
        // System.out.println(buffer.readLine());
        while (buffer.ready()) {
            line = buffer.readLine();
            lines.add(line);
        }
        closeCSVFile();
        return this.lines;
    }

    private void closeCSVFile() throws IOException {
        buffer.close();
        fileReader.close();
    }
}

// 

