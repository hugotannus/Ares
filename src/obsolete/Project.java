/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package obsolete;

/**
 *
 * @author Hugo
 */
public class Project extends Requirement{
    public boolean defined;
    public boolean approved;
    
    public Project(String description) {
        super(description);
        defined = false;
        approved = false;
    }
    
}
