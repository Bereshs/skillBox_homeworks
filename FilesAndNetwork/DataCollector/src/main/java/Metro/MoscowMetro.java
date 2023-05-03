package Metro;

import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MoscowMetro {
    private List<MetroLine> metroLinesList;
    private List<MetroStation> metroStationsList;

    public MoscowMetro() {
        metroLinesList = new ArrayList<>();
        metroStationsList = new ArrayList<>();
    }

    public void setMetroLines(List<MetroLine> metroLinesList) {
        this.metroLinesList = metroLinesList;
    }

    public void setMetroStations(List<MetroStation> metroStationsList) {
        this.metroStationsList = metroStationsList;
    }

    public void addAllStations(List<MetroStation> addStationsList) {
        addStationsList.forEach(addStation -> {
            metroStationsList.forEach(station -> {
                if (station.getName().equals(addStation.getName())) {
                    station.setData(addStation);
                }
            });
        });
    }

    public String getStationsJson() {

        JSONObject obj = new JSONObject();
        List<JSONObject> lineList = new ArrayList<>();
        for (MetroStation metroStation : metroStationsList) {
            lineList.add(metroStation.toJSONObject());
        }
        obj.put("stations", lineList);

        return obj.toJSONString();
    }

    public String getLinesJson() {

        JSONObject obj = new JSONObject();
        List<JSONObject> lineList = new ArrayList<>();
        for (MetroLine metroLine : metroLinesList) {
            lineList.add(metroLine.toJSONObject());
        }
        obj.put("lines", lineList);
        return obj.toJSONString();
    }

    @Override
    public String toString() {
        StringBuilder allStationsAndLines = new StringBuilder();
        metroLinesList.forEach(line -> {
            String lineName = line.getName();
            allStationsAndLines.append("Линия ").append(line).append(System.lineSeparator());
            for (MetroStation metroStation : metroStationsList) {
                String stationLineName = (metroStation.getLine().getName());
                if (lineName.equals(stationLineName)) {
                    allStationsAndLines.
                            append("\t").
                            append(metroStation).
                            append(System.lineSeparator());
                }
            }
        });
        return allStationsAndLines.toString();
    }
}
