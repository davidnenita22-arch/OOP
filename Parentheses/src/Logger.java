public class Logger {
    private final String name;
    private final Formatter formatter;
    private final ConsoleSink sink;

    private static LogLevel level = LogLevel.INFO;

    //COMPOSITION: the logger builds its own parts
    public Logger(String name) {
        this.name = name;
        this.formatter = new Formatter("[%l] %n: %m | %r");
        this.sink = new ConsoleSink();
    }
    //AGGREGATION: the parts are created outside and handed in
    public Logger(String name, Formatter formatter, ConsoleSink sink) {
        this.name = name;
        this.formatter = formatter;
        this.sink = sink;
    }

    public static void setLevel(LogLevel newLevel) {
        level = newLevel;
    }

    public void log(LogLevel msgLevel, String message) {
        if (msgLevel.ordinal() < level.ordinal()) {
            return;
        }
        String line = formatter.format(msgLevel, name, message);
        sink.write(line);
    }

        public void trace(String message) { log(LogLevel.TRACE, message); }
        public void info(String message) { log(LogLevel.INFO, message); }
        public void warn(String message) { log(LogLevel.WARN, message); }
        public void error(String message) { log(LogLevel.ERROR, message); }
}
