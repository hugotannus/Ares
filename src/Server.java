import comunication.RMIComms;
import comunication.ServerServices;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.rmi.RemoteException;
import javax.swing.JOptionPane;
/**
 *
 * @author guilherme
 */
public class Server {

	private RMIComms comms;

	public Server(String serverIP, int registryPort) {
		try {

			comms = new RMIComms();

			comms.setUpServer(serverIP, registryPort);

			comms.bind("AresRemoteAPI", new ServerServices());

		} catch (RemoteException e) {
			System.out.println("Server - " + e.getMessage());
                        System.exit(1);
		}

	}

	public static void main(String[] args) {
            //Server serv = new Server("192.168.25.14", 8001);
            Server serv = new Server("localhost", 8001);
            System.out.println("Server created. Running...");

            javax.swing.JOptionPane.showMessageDialog(null, "Server created. Running...",
                    "Inform", javax.swing.JOptionPane.INFORMATION_MESSAGE);
	}
}