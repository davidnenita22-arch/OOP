public class Main {
    public static void main(String[] args) {
        ParenthesesValidator validator = new ParenthesesValidator();

        String[] inputs = {"()", ")((()))", "(", "(())((()())())", ")(", "(()", "())",
                "()()"};

        for (String input : inputs) {
            boolean result = validator.isValid(input);

            System.out.println("Input: \"" + input + "\" Result: " + result);
        }
    }
}
