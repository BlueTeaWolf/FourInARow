package game;

/**
 * @author BlueTeaWolf (Ole)
 */
public interface Protocol {
    int PORT = 25565;
    String IMPOSSIBLE_MOVE = "REJECTED:IMPOSSIBLE_MOVE";
    String OK = "OK:";
    String CONNECTED = "connected";
    String NEXT_MOVE = "NEXT_MOVE";
    String END = "END:";
    String LOSE = "LOST";
    String WON = "WON";

}