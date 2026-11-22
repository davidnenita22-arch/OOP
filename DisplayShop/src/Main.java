import java.util.ArrayList;
import java.util.List;

public class Main {
    private static List<DisplayManager> managers = new ArrayList<>();
    private static List<Display> displays = new ArrayList<>();

    private static Logger parser;
    private static Logger managerLogger;

    public static void main(String[] args) {
        String path = "shop-monday.txt";
        if (args.length > 0) {
            path = args[0];
        }
        LogLevel level = LogLevel.TRACE;
        if (args.length > 1) {
            level = readLevel(args[1]);
        }
        Logger.setLevel(level);

        Formatter formatter = new Formatter("[%l] %n: %m");
        ConsoleSink sink = new ConsoleSink();
        parser = new Logger("Parser", formatter, sink);
        managerLogger = new Logger("Manager", formatter, sink);
        Logger readerLogger = new Logger("Reader", formatter, sink);

        CommandReader reader = new CommandReader(readerLogger);
        List<String> lines = reader.readLines(path);

        for (int i = 0; i < lines.size(); i++) {
            parseCommand(lines.get(i), i + 1);
        }
    }

    private static LogLevel readLevel(String text) {
        LogLevel[] levels = LogLevel.values();
        for (int i = 0; i < levels.length; i++) {
            if (levels[i].name().equals(text)) {
                return levels[i];
            }
        }
        return LogLevel.TRACE;
    }

    public static void parseCommand(String line, int lineNumber) {
        line = line.trim();
        if (line.isEmpty() || line.startsWith("#")) {
            return;
        }

        String[] words = line.split("\\s+");
        String command = words[0];

        if (command.equals("MANAGER")) {
            createManager(words, lineNumber);
        } else if (command.equals("DISPLAY")) {
            createDisplay(words, lineNumber);
        } else if (command.equals("ASSIGN")) {
            assign(words, lineNumber);
        } else if (command.equals("COMPARE")) {
            compare(words, lineNumber);
        } else if (command.equals("ASSIST")) {
            assist(words, lineNumber);
        } else if (command.equals("BUY")) {
            buy(words, lineNumber);
        } else {
            parser.error("Line " + lineNumber + ": unknown command \"" + command + "\"");
        }
    }

    // MANAGER
    private static void createManager(String[] words, int lineNumber) {
        if (!checkWords(words, 2, lineNumber)) {
            return;
        }
        String name = words[1];
        if (findManager(name) != null) {
            parser.warn("Line " + lineNumber + ": manager " + name + " already exists, keeping the old one");
            return;
        }
        managers.add(new DisplayManager(name, managerLogger));
        parser.trace("Manager " + name + " created");
    }

    // DISPLAY
    private static void createDisplay(String[] words, int lineNumber) {
        if (!checkWords(words, 5, lineNumber)) {
            return;
        }
        String model = words[1];
        if (findDisplay(model) != null) {
            parser.warn("Line " + lineNumber + ": display " + model + " already exists, keeping the old one");
            return;
        }

        int width;
        int height;
        float ppi;
        try {
            width = Integer.parseInt(words[2]);
            height = Integer.parseInt(words[3]);
            ppi = Float.parseFloat(words[4]);
        } catch (NumberFormatException e) {
            parser.error("Line " + lineNumber + ": width, height and ppi must be numbers, got "
                    + words[2] + " " + words[3] + " " + words[4]);
            return;
        }

        if (width <= 0 || height <= 0 || ppi <= 0) {
            parser.error("Line " + lineNumber + ": width, height and ppi must be bigger than 0");
            return;
        }

        Display display = new Display(model, width, height, ppi);
        displays.add(display);
        parser.trace("Display " + display.getInfo() + " created");
    }

    // ASSIGN
    private static void assign(String[] words, int lineNumber) {
        if (!checkWords(words, 3, lineNumber)) {
            return;
        }
        Display display = getDisplay(words[1], lineNumber);
        DisplayManager manager = getManager(words[2], lineNumber);
        if (display == null || manager == null) {
            return;
        }
        if (!manager.assignDisplay(display)) {
            parser.warn("Line " + lineNumber + ": " + display.getModel()
                    + " is already assigned to " + manager.getName());
        }
    }

    // COMPARE
    private static void compare(String[] words, int lineNumber) {
        if (!checkWords(words, 3, lineNumber)) {
            return;
        }
        Display first = getDisplay(words[1], lineNumber);
        Display second = getDisplay(words[2], lineNumber);
        if (first == null || second == null) {
            return;
        }
        parser.info(first.compareWithMonitor(second));
    }

    // ASSIST
    private static void assist(String[] words, int lineNumber) {
        if (!checkWords(words, 2, lineNumber)) {
            return;
        }
        DisplayManager manager = getManager(words[1], lineNumber);
        if (manager == null) {
            return;
        }
        manager.assist();
    }

    // BUY
    private static void buy(String[] words, int lineNumber) {
        if (!checkWords(words, 3, lineNumber)) {
            return;
        }
        Display display = getDisplay(words[1], lineNumber);
        DisplayManager manager = getManager(words[2], lineNumber);
        if (display == null || manager == null) {
            return;
        }
        if (manager.buyDisplay(display) == null) {
            parser.error("Line " + lineNumber + ": " + display.getModel() + " is not assigned to "
                    + manager.getName() + ", cannot buy it");
        }
    }

    // checks that the line has the right number of words
    private static boolean checkWords(String[] words, int expected, int lineNumber) {
        if (words.length != expected) {
            parser.error("Line " + lineNumber + ": " + words[0] + " needs " + (expected - 1)
                    + " argument(s), but got " + (words.length - 1));
            return false;
        }
        return true;
    }

    private static DisplayManager findManager(String name) {
        for (DisplayManager manager : managers) {
            if (manager.getName().equals(name)) {
                return manager;
            }
        }
        return null;
    }

    private static Display findDisplay(String model) {
        for (Display display : displays) {
            if (display.getModel().equals(model)) {
                return display;
            }
        }
        return null;
    }

    // same as findManager, but logs an error if the manager does not exist
    private static DisplayManager getManager(String name, int lineNumber) {
        DisplayManager manager = findManager(name);
        if (manager == null) {
            parser.error("Line " + lineNumber + ": manager " + name + " does not exist");
        }
        return manager;
    }

    // same as findDisplay, but logs an error if the display does not exist
    private static Display getDisplay(String model, int lineNumber) {
        Display display = findDisplay(model);
        if (display == null) {
            parser.error("Line " + lineNumber + ": display " + model + " does not exist");
        }
        return display;
    }
}
