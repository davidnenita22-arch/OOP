import date.time.LocalTime;
import date.time.format.DateTimeFormatter;

public class Formatter {
    private final String format;

    public Formatter(String format){
        this.format = format;
    }

    public String(LogLevele.level, String name, String message) {
        String time = LocalTime.now().format(
                DateTimeFormatter.ofPattern("HH:mm:ss")
        );
        String randomText = format.contains("r") ? level.randomMessage() : "" ;

        StringBuilder output = new StringBuilder;

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
         return output.toString();
    }
}