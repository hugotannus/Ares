/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deprecated;

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
