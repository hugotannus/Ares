/*
 * A classe 'Service', com herança sobre a classe 'Node', será utilizada para de-
 * signar/discriminar um pacote de trabalho em um planejamento de obra.
 * Cada instância dessa classe armazenará gerenciará dados/aspectos sobre os se-
 * guintes requisitos: 'Projeto', 'Mão de Obra', 'Material' e 'Logística', sendo
 * que uma Service só poder ser considerada "apta" ou "pronta para execução"
 * após cada uma das restrições terem sido devidamente satisfeitas.
 */

package commons;


import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hugo
 */
public class Service extends Node
{
    public Date dataInicio;
    public Date dataTermino;
    public String budget;
    public String comments;
    /*
     * (05/03/2012) Por hora, irei ignorar as listas para objetos "Project", "Logistic",
     * "Material" e "ManPower", uma vez que é possível recuperar todas as infor-
     * mações diretamente do banco de dados. Tais objetos tem importâncias diferentes do obje-
     * to "Service", porque este último faz parte da estrutura em árvore do Ares,
     * e é o esqueleto do programa.
     * Contudo, tais listas serão necessárias futuramente, para que sejam possí-
     * veis fazer bloqueios de preenchimento de campos e estatísicas mais elabo-
     * radas. Isso irá acrescentar certa complexidade ao código.
     */
//    public List <Project> projetos = new ArrayList<Project>();
//    public List <Logistic> logisticas = new ArrayList<Logistic>();
//    public List <Material> materiais = new ArrayList<Material>();
//    public List <ManPower> maoDeObra = new ArrayList<ManPower>();
    
    public Service(String line) {
        String[] tk = line.split(";");

        this.estTopicos = tk[0];
        this.ID = (short)Integer.parseInt(tk[1]);
        this.descricao = tk[2];

        try {
            System.out.print(tk[3] + " ==> ");
            this.dataInicio = convertDate(tk[3]);

            System.out.println(dataInicio);
        } catch (ParseException ex) {
            Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            System.out.print(tk[4] + " ==> ");
            this.dataTermino = convertDate(tk[4]);
            System.out.println(dataTermino);
        } catch (ParseException ex) {
            Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Service(String topicos, int id, String descricao, Date inicio, Date termino, String budget, String comment) {
        this.estTopicos = topicos;
        this.ID = (short)id;
        this.descricao = descricao;
        this.dataInicio = inicio;
        this.dataTermino = termino;
        this.budget = budget;
        this.comments = comment;
    }
    
    @Override
    public boolean isReady() {
        this.ready = true;
        while (childIt.hasNext()) {
            this.ready &= childIt.next().isReady();
        }
        return this.ready;
    }
    
    @Override
    public boolean getAllowsChildren() {
        return true;
    }
/*
    public void addProject(String description) {
        projetos.add(new Project(description));
    }
    
    public void addLogistic(String description) {
        logisticas.add(new Logistic(description));
    }
    
    public void addManPower(String description) {
        maoDeObra.add(new ManPower(description));
    }
    
    public void addMaterial(String description) {
        materiais.add(new Material(description));
    }
*/
    private Date convertDate(String str) throws ParseException {
        String[] tk = str.split(" ");
        String[] tk1 = tk[1].split("/");
        //System.out.printf("tk length: %d\n", tk1.length);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        java.util.Date date = sdf.parse(tk[1]);

        tk = date.toString().split(" ");
        String dateFormat = tk[5] + "-" + tk1[1] + "-" + tk1[0];
        
        return Date.valueOf(dateFormat);
    }
}
