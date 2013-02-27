package deprecated;


import java.util.Date;

/*
 * Classe genérica para todas as variáveis de requisitos da calsse 'Tarefa'.
 *
 */

/**
 *
 * @author hugo
 */
public class Requirement
{
    public int prazo;
    public Date dataEntrega;
    public Date dataModif;
    public String description;

    public Requirement(String description) {
        this.description = description;
    }
}
