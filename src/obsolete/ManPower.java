/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package obsolete;

/**
 *
 * @author Hugo
 */
public class ManPower extends Requirement{
    public boolean available;
    public boolean engaged;
    
    public ManPower(String description) {
        super(description);
        available = false;
        engaged = false;
    }
    
}
