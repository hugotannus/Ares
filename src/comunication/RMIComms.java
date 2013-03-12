package comunication;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.*;
import java.rmi.registry.*;

/**
 *
 * @author guilherme
 */
public class RMIComms {

    private Registry r;

    public RMIComms() {
        super();
    }

    public void setUpServer(String serverIP, int registryPort) {
        try {
            File f = new File("server.policy");
            
            FileWriter datilografador = new FileWriter(f);
            datilografador.write("grant {\n	permission java.security.AllPermission;\n};");
            
            datilografador.close();
            f.deleteOnExit();
            
            System.setProperty("java.rmi.server.hostname", serverIP);
            System.setProperty("java.security.policy", "server.policy");
            System.setSecurityManager(new RMISecurityManager());
            r = LocateRegistry.createRegistry(registryPort);
        } catch (IOException e) {
            System.out.println("setUpServer - " + e.getMessage());
        }
    }

    public void setUpClient(String registryIP, int registryPort) {
        try {
            File f = new File("client.policy");
            
            FileWriter datilografador = new FileWriter(f);
            datilografador.write("grant {\n	permission java.security.AllPermission;\n};");
            
            datilografador.close();
            f.deleteOnExit();
            
            System.setProperty("java.security.policy", "client.policy");
            System.setSecurityManager(new RMISecurityManager());
            r = LocateRegistry.getRegistry(registryIP, registryPort);
        } catch (IOException e) {
            System.out.println("setUpClient - " + e.getMessage());
        }
    }
    
    /*
     * Usado pelo Server. Faz o bind do objeto que implementa a interface remoto
     * ao nome pelo qual será chamado pelo Client
     */

    public void bind(String bindName, Object obj) throws RemoteException {
        try {
            r.bind(bindName, (Remote) obj);
        } catch (AccessException e) {
            throw new RemoteException("Bind (AccessException) - " + e.getMessage());
        } catch (AlreadyBoundException e) {
            throw new RemoteException("Bind (AlreadyBoundException) - " + e.getMessage());
        }
    }

    /*
     * Usado pelo Client. Procura pelo objeto que implementa a interface remota 
     * no servidor de nomes, utilizando a string name como chave de busca.
     */
    
    public Object lookup(String name) throws RemoteException {
        try {
            return r.lookup(name);
        } catch (AccessException e) {
            throw new RemoteException("Lookup (AcessException) - " + e.getMessage());
        } catch (NotBoundException e) {
            throw new RemoteException("Lookup (NotBoundException) - " + e.getMessage());
        }
    }
}