public class ParenthesesValidator {
    private final Logger logger = new Logger("Parentheses");

    public boolean isValid(String input) {
        if (input == null) {
            return finish(input, false, "Input is null.");
        }

        int balance = 0;

        for (int i = 0; i < input.length(); i++) {
            char current = input.charAt(i);

            if (current == '(') {
                balance++;

                logger.log(LogLevel.TRACE, "Opening parenthesis found at index " + i
                        + "; unmatched openings: " + balance);
            } else if (current == ')') {
                if (balance == 0) {
                    logger.log(LogLevel.WARN, "No opening is available at index " + i);

                    return finish(input, false,
                            "Closing parenthesis at index " + i + " has no matching opening."
                    );
                }

                balance--;

                logger.log(LogLevel.INFO, "Matching pair completed at index " + i);
            } else {
                return finish(input, false, "Unexpected character '" + current + "' at index " + i + ".");
            }
        }

        if (balance != 0) {
            return finish(input, false, "Unmatched opening parentheses remain: " + balance);
        }

        return finish(input, true, "");
    }

    private boolean finish(String input, boolean valid, String reason) {
        logger.log(LogLevel.INFO, "Validation result for \"" + input + "\": " + valid);

        if (!valid) {
            logger.log(LogLevel.ERROR, "Invalid input \"" + input + "\": " + reason);
        }

        return valid;
    }
}
