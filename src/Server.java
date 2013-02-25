import comunication.RMIComms;
import comunication.ServerServices;
import java.rmi.RemoteException;
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
		}

	}

	public static void main(String[] args) {
		Server serv = new Server("localhost", 8001);

		System.out.println("Server created. Running...");
	}
}