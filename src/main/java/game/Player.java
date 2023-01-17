package game;

/**
 * @author BlueTeaWolf (Ole)
 */
public enum Player {

    RED('R'), YELLOW('Y');

    private final char r;

    Player(char r) {
        this.r = r;
    }

    public char getPlayer() {
        return r;
    }

}
