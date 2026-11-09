package quest.engine;

/**
 * Command-line options, passed through Gradle as {@code gradlew.bat quest --args="..."}.
 *
 * @param level a room to replay, or {@code null} for the current room
 */
record Options(boolean hint, boolean map, Integer level, boolean help, boolean noColor) {

    static Options parse(String[] args) {
        boolean hint = false;
        boolean map = false;
        boolean help = false;
        boolean noColor = false;
        Integer level = null;
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            switch (arg) {
                case "--hint" -> hint = true;
                case "--map" -> map = true;
                case "--help", "-h" -> help = true;
                case "--no-color" -> noColor = true;
                case "--level" -> {
                    if (i + 1 >= args.length) {
                        throw new IllegalArgumentException("--level needs a room number, e.g. --level 2");
                    }
                    level = roomNumber(args[++i]);
                }
                default -> {
                    if (arg.startsWith("--level=")) {
                        level = roomNumber(arg.substring("--level=".length()));
                    } else {
                        throw new IllegalArgumentException("Unknown option: " + arg);
                    }
                }
            }
        }
        return new Options(hint, map, level, help, noColor);
    }

    private static int roomNumber(String text) {
        try {
            return Integer.parseInt(text.strip());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("--level needs a room number, not \"" + text + "\"");
        }
    }
}
