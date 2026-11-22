import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandReader {
    private Logger logger;

    public CommandReader(Logger logger) {
        this.logger = logger;
    }

    public List<String> readLines(String path) {
        List<String> lines = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(path));
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
            scanner.close();
            logger.trace("Read " + lines.size() + " lines from " + path);
        } catch (FileNotFoundException e) {
            logger.error("File " + path + " does not exist");
        }
        return lines;
    }
}
