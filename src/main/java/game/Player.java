package game;

/**
 * @author BlueTeaWolf (Ole)
 */
public enum Player {

    RED("R"), YELLOW("Y");

    private final String r;

    Player(String r) {
        this.r = r;
    }

    public String getPlayer() {
        return r;
    }

}
