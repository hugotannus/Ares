/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deprecated;

import commons.Node;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Hugo
 */
public class RequirementsSet extends Node{
    Requirement projeto;
    Requirement logistica;
    List <Material> materiais = new ArrayList<Material>();
    List <Requirement> other = new ArrayList<Requirement>();
    List <ServiceProvider> provider = new ArrayList<ServiceProvider>();
    
    @Override
    public boolean getAllowsChildren() {
        return false;
    }

    @Override
    public boolean isReady() {
        return this.ready;
    }
}
