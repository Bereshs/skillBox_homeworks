package Metro;

import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class MetroStation {
    private final String name;
    private MetroLine line;
    private boolean hasConnection = false;
    private LocalDate date = LocalDate.parse("1970-01-01");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private double depth;

    public MetroStation(String name, MetroLine line, boolean hasConnection) {
        this.name = name;
        this.line = line;
        this.hasConnection = hasConnection;

    }

    public MetroStation(String name, double depth) {
        this.name = name;
        this.depth = depth;
        this.line = new MetroLine("0", "depth");
    }

    public MetroStation(String name, LocalDate date) {
        this.name = name;
        this.date = date;
        this.line = new MetroLine("0", "depth");
    }

    public void setData(MetroStation otherStation) {
        if (!equals(otherStation)) {
            return;
        }
        if (otherStation.getDepth() != 0.0) {
            setDepth(otherStation.getDepth());
        }
        if (otherStation.getDate().isBefore(LocalDate.parse("1970-01-01"))) {
            setDate(otherStation.getDate());
        }
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    boolean isHasConnection() {
        return hasConnection;
    }

    public String getDateAsString() {
        return date.format(formatter);
    }

    public double getDepth() {
        return depth;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }

    MetroLine getLine() {
        return line;
    }

    String getName() {
        return name;
    }

    @Override
    public String toString() {
        String nextLine = System.lineSeparator();
        String stationToString = "\t{" + nextLine +
                "\t\t\"name\": \"" + getName() + "\"," + nextLine +
                "\t\t\"line\": \"" + line.getName() + "\"," + nextLine;
        if (depth != 0) {
            stationToString += "\t\t\"depth\": " + getDepth() + "," + nextLine;
        }
        if (date.isBefore(LocalDate.parse("1970-01-01"))) {
            stationToString += "\t\t\"date\": \"" + getDateAsString() + "\"," + nextLine;
        }
        if (isHasConnection()) {
            stationToString += "\t\t\"hasConnection\": " + isHasConnection() + "," + nextLine;
        }
        stationToString = stationToString.substring(0, stationToString.length() - 2);
        stationToString += nextLine + "\t}," + nextLine;
        return stationToString;

    }

    JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        if (isHasConnection()) {
            obj.put("hasConnection", isHasConnection());
        }
        if (date.isBefore(LocalDate.parse("1970-01-01"))) {
            obj.put("date", getDateAsString());
        }
        if (depth != 0) {
            obj.put("depth", depth);
        }
        obj.put("line", line.getName());
        obj.put("name", getName());
        return obj;
    }

    public boolean equals(MetroStation otherStation) {
        return this.getName().equals(otherStation.getName());
    }
}
