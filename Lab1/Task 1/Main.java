public class Main {
    public static void main(String[] args) {
        Logger first = new Logger("First");
        Logger second = new Logger("Second");

        Logger.setLevel(LogLevel.INFO);
        Logger.setFormat("[%t] [%l] %n: %m");

        first.log(LogLevel.TRACE, "This should be hidden");
        first.log(LogLevel.INFO, "Program started");
        second.log(LogLevel.WARN, "Input is empty");
        second.log(LogLevel.ERROR, "Cannot continue");

        Logger.setLevel(LogLevel.ERROR);
        Logger.setFormat("%n -> %m");

        first.log(LogLevel.INFO, "This should also be hidden");
        second.log(LogLevel.ERROR, "The settings changed");
    }
}