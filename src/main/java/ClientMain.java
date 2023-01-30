import client.Client;
import game.Protocol;

/**
 * @author BlueTeaWolf (Ole)
 */
public class ClientMain {

    public static void main(String[] args) {
        Client client = new Client("localhost", Protocol.PORT);
    }

}
