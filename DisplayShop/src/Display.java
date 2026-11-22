public class Display {
    private String model;
    private int width;
    private int height;
    private float ppi;

    public Display(String model, int width, int height, float ppi) {
        this.model = model;
        this.width = width;
        this.height = height;
        this.ppi = ppi;
    }

    public String getModel() {
        return model;
    }

    public float getPpi() {
        return ppi;
    }

    public double getDiagonal() {
        double w = width;
        double h = height;
        return Math.sqrt(w * w + h * h) / ppi;
    }

    public double getRoundedDiagonal() {
        return Math.round(getDiagonal() * 10) / 10.0;
    }

    public String getSizeText() {
        return model + " (" + getRoundedDiagonal() + "\")";
    }

    public String getPpiText() {
        return model + " (" + Math.round(ppi) + " ppi)";
    }

    public String compareSize(Display other) {
        if (getDiagonal() > other.getDiagonal()) {
            return getSizeText() + " is bigger than " + other.getSizeText() + ".";
        } else if (getDiagonal() < other.getDiagonal()) {
            return getSizeText() + " is smaller than " + other.getSizeText() + ".";
        } else {
            return getSizeText() + " has the same size as " + other.getSizeText() + ".";
        }
    }

    public String compareSharpness(Display other) {
        if (ppi > other.getPpi()) {
            return getPpiText() + " is sharper than " + other.getPpiText() + ".";
        } else if (ppi < other.getPpi()) {
            return getPpiText() + " is less sharp than " + other.getPpiText() + ".";
        } else {
            return getPpiText() + " is as sharp as " + other.getPpiText() + ".";
        }
    }

    public String compareWithMonitor(Display other) {
        String result = compareSize(other) + " " + compareSharpness(other);

        int score = 0;
        if (getDiagonal() > other.getDiagonal()) {
            score++;
        }
        if (getDiagonal() < other.getDiagonal()) {
            score--;
        }
        if (ppi > other.getPpi()) {
            score++;
        }
        if (ppi < other.getPpi()) {
            score--;
        }

        if (score > 0) {
            result = result + " Verdict: " + model + " is the better monitor.";
        } else if (score < 0) {
            result = result + " Verdict: " + other.getModel() + " is the better monitor.";
        } else {
            result = result + " Verdict: no clear winner, it depends on what you need.";
        }
        return result;
    }

    public String getInfo() {
        return model + " (" + getRoundedDiagonal() + "\", " + Math.round(ppi) + " ppi)";
    }
}
