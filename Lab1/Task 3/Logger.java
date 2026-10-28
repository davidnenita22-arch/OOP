import java.util.concurrent.ThreadLocalRandom;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private final String name;

    private static LogLevel level = LogLevel.INFO;
    private static String format = "[%t] [%l] %n: %m";

    public Logger(String name) {
        this.name = name;
    }

    public static void setLevel(LogLevel newLevel) {
        level = newLevel;
    }

    public static void setFormat(String newFormat) {
        format = newFormat;
    }

    public void log(LogLevel msgLevel, String message) {
        if (msgLevel.ordinal() < level.ordinal()) {
            return;
        }

        String time = LocalTime.now().format(
                DateTimeFormatter.ofPattern("HH:mm:ss")
        );
        String randomText = format.contains("%r") ? randomMessage(msgLevel) : "";

        StringBuilder output = new StringBuilder();

        for (int i = 0; i < format.length(); i++) {
            char current = format.charAt(i);

            if (current == '%' && i + 1 < format.length()) {
                String replacement = switch (format.charAt(i + 1)) {
                    case 't' -> time;
                    case 'm' -> message;
                    case 'n' -> name;
                    case 'l' -> msgLevel.name();
                    case 'r' -> randomText;
                    default -> null;
                };

                if (replacement != null) {
                    output.append(replacement);
                    i++;
                    continue;
                }
            }
            output.append(current);
        }
        System.out.println(output);
    }

    private String randomMessage(LogLevel msgLevel) {
        String[] choices = switch (msgLevel) {
            case TRACE -> new String[] {
                    "Following program steps",
                    "Inspecting an operation"
            };
            case INFO -> new String[] {
                    "Event recorded",
                    "Here is your result"
            };
            case WARN -> new String[] {
                    "Something needs attention",
                    "Unexpected situation"
            };
            case ERROR -> new String[] {
                    "An operation failed",
                    "Invalid input detected"
            };
        };
        int index = ThreadLocalRandom.current().nextInt(choices.length);
        return choices[index];
    }
}
