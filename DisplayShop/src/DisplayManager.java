import java.util.ArrayList;
import java.util.List;

public class DisplayManager {
    private String name;
    private List<Display> assignedDisplays;
    private Logger logger;

    public DisplayManager(String name, Logger logger) {
        this.name = name;
        this.assignedDisplays = new ArrayList<>();
        this.logger = logger;
    }

    public String getName() {
        return name;
    }

    public boolean hasDisplay(Display d) {
        return assignedDisplays.contains(d);
    }

    public boolean assignDisplay(Display d) {
        if (hasDisplay(d)) {
            return false;
        }
        assignedDisplays.add(d);
        logger.trace(d.getModel() + " assigned to " + name);
        return true;
    }

    public void assist() {
        if (assignedDisplays.size() < 2) {
            logger.warn(name + " has " + assignedDisplays.size() + " display(s), nothing to compare");
            return;
        }
        logger.info(name + " compares " + assignedDisplays.size() + " displays:");
        for (int i = 0; i < assignedDisplays.size() - 1; i++) {
            Display first = assignedDisplays.get(i);
            Display second = assignedDisplays.get(i + 1);
            logger.info(first.compareWithMonitor(second));
        }
    }

    public Display buyDisplay(Display d) {
        if (!hasDisplay(d)) {
            return null;
        }
        assignedDisplays.remove(d);
        logger.info(name + " sold " + d.getModel());
        if (assignedDisplays.size() == 0) {
            logger.warn(name + " has no displays left");
        }
        return d;
    }
}
