import java.util.concurrent.ThreadLocalRandom;

public enum LogLevel {
    TRACE("Following program steps", "Inspecting an operation"),
    INFO("Event recorded", "Here is your result"),
    WARN("Something needs attention", "Unexpected situation"),
    ERROR("An operation failed", "Invalid input detected");

    private final String[] messages;

    LogLevel(String... messages) {
        this.messages = messages;
    }

    public String randomMessage() {
        int index = ThreadLocalRandom.current().nextInt(messages.length);
        return messages[index];
    }
}
