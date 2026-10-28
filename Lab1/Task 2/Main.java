public class Main {
    public static void main(String[] args) {
        ParenthesesValidator validator = new ParenthesesValidator();

        String[] inputs = {"()", ")((()))", "(", "(())((()())())", ")(", "(()", "())",
                "()()", "", null, "a()"};

        boolean[] expected = {true, false, false, true, false, false, false, true,
                true, false, false};

        for (int i = 0; i < inputs.length; i++) {
            boolean result = validator.isValid(inputs[i]);

            System.out.println("Input: \"" + inputs[i] + "\"" + " Result: " + result);
        }
    }
}