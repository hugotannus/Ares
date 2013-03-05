import comunication.RMIComms;
import comunication.ServerServices;
import java.net.Inet4Address;
import java.net.InetAddress;
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
//            InetAddress ip = new Inet4Address();
            Server serv = new Server("192.168.25.14", 8001);

		System.out.println("Server created. Running...");
	}
}