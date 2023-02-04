import game.Protocol;
import server.Server;

import java.io.IOException;

/**
 * @author BlueTeaWolf (Ole)
 */
public class ServerMain implements Protocol {


    public static void main(String[] args) throws IOException {
        Server server = new Server(PORT);
        server.start();
    }
}
