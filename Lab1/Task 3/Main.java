public class Main {
    public static void main(String[] args) {
        Logger.setLevel(args.length == 0 ? LogLevel.TRACE : LogLevel.valueOf(args[0]));
        Logger.setFormat("[%l] %n: %m | %r");

        ParenthesesValidator validator = new ParenthesesValidator();

        String[] inputs = {"()", ")((()))", "(", "(())((()())())", ")(", "(()", "())",
                "()()"};

        for (int i = 0; i < inputs.length; i++) {
            String input = inputs[i];
            String display = input;

            System.out.println("Input: " + display);
            System.out.println();

            boolean result = validator.isValid(input);

            System.out.println();
            System.out.println();
        }
    }
}
