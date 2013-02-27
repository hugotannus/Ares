
package deprecated;

/*
 * Classe designada para o requisito "Material" de cada pacote de trabalho.
 * 
 */

/**
 *
 * @author hugo
 */
public class Material extends Requirement
{
    public boolean inLoco;
    public boolean solicited;
    public boolean available;

    public Material(String description) {
        super(description);
        inLoco = false;
        solicited = false;
        available = false;
    }   
}
