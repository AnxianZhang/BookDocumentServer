package consolColor;

public class Color {
    public static final String BLACK_BOLD;  // BLACK
    public static final String RED_BOLD;    // RED
    public static final String GREEN_BOLD;  // GREEN
    public static final String YELLOW_BOLD; // YELLOW
    public static final String BLUE_BOLD;   // BLUE
    public static final String PURPLE_BOLD; // PURPLE
    public static final String CYAN_BOLD;   // CYAN
    public static final String WHITE_BOLD;  // WHITE
    public static final String RESET;  // Text Reset

    static {
        BLACK_BOLD = "\033[1;30m";
        RED_BOLD = "\033[1;31m";
        GREEN_BOLD = "\033[1;32m";
        YELLOW_BOLD = "\033[1;33m";
        BLUE_BOLD = "\033[1;34m";
        PURPLE_BOLD = "\033[1;35m";
        CYAN_BOLD = "\033[1;36m";
        WHITE_BOLD = "\033[1;37m";
        RESET = "\033[0m";
    }
}
