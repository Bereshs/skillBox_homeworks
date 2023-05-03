package Parse;

import Metro.MetroStation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

public class JsParser {

    public static List<MetroStation> parseFile(String jsonFileStrings) throws ParseException {
        List<MetroStation> listStations = new ArrayList<>();
        JSONParser parser = new JSONParser();
        JSONArray jsonData = (JSONArray) parser.parse(jsonFileStrings);
        jsonData.forEach(stationArray -> {
            JSONObject stationObject = (JSONObject) stationArray;
            double depth = 0;
            String depthLine = stationObject.get("depth").toString().replaceAll(",", ".");
            boolean isdepthLineNumber = depthLine.matches("[-]?[0-9]+[\\.]*[0-9]*");
            if (isdepthLineNumber) {
                depth = Double.parseDouble(depthLine);
            }


            listStations.add(new MetroStation(
                    stationObject.get("station_name").toString(),
                    depth));
        });
        return listStations;
    }

}
