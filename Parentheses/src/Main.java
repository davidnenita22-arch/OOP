public class Main {
    public static void main(String[] args) {
        Logger.setLevel(args.length == 0 ? LogLevel.TRACE : LogLevel.valueOf(args[0]));
        ParenthesesValidator validator = new ParenthesesValidator();

        String[] inputs = {"()", ")((()))", "(", "(())((()())())", "())", "()()"};

        for (int i = 0; i < inputs.length; i++) {
            validator.isValid(inputs[i]);
        }

        Formatter shared = new Formatter("[%t] [%l] %n: %m");
        ConsoleSink console = new ConsoleSink();

        Logger parser = new Logger("Parser", shared, console);
        Logger manager = new Logger("Manager", shared, console);
        Logger special = new Logger("Special", new Formatter("%l >> %m"), console);

        parser.info("I share a formatter with Manager");
        manager.warn("Same format as Parser");
        special.error("I have my own format");
    }
}
