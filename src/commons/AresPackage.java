/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package commons;

import com.sun.rowset.CachedRowSetImpl;
import com.sun.rowset.WebRowSetImpl;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.WebRowSet;

/**
 *
 * @author hugo
 */
public class AresPackage {

    public String budget;
    public String comments;

    public List <Project> projetos = new ArrayList<Project>();
    public List <Logistic> logisticas = new ArrayList<Logistic>();
    public List <Material> materiais = new ArrayList<Material>();
    public List <ManPower> maoDeObra = new ArrayList<ManPower>();
    
    
    private String data;
    
    public AresPackage(String data){
        this.data = data;
    }
    
    public String getData(){
        return this.data;
    }
    
    public static CachedRowSet aresPackageToCachedRowSet(AresPackage ap) throws SQLException{
        WebRowSet wrs = new WebRowSetImpl();
        
        StringReader sr = new StringReader(ap.getData());
        
        wrs.readXml(sr);
        
        CachedRowSet crs = new CachedRowSetImpl();
        crs.populate(wrs);
        
        return crs;
    }
    
    public static AresPackage cachedRowSetToAresPackage(CachedRowSet crs) throws SQLException {
        WebRowSet wrs = new WebRowSetImpl();
        wrs.populate(crs);

        StringWriter sw = new StringWriter();

        wrs.writeXml(sw);

        return new AresPackage(sw.toString());
    }
}
