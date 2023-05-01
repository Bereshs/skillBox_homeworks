import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JSONHandler {
    public static void main(String[] args) {
        try {
            String jsonFile = Files.readString(
                    Paths.get("src/main/resources/map.json")
            );
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonData = objectMapper.readTree(jsonFile);
            JsonNode stations = jsonData.get("stations");
            JsonNode lines = jsonData.get("lines");
            for(JsonNode line : lines) {
                ObjectNode lineNode = (ObjectNode) line;
                lineNode.remove("color");
                int stationsCount = stations.get(String.valueOf(lineNode.get("number"))).size();
                lineNode.put("stationsCount",stationsCount);
            }
        File output = new File("src/main/resources/changedMap.json");
            objectMapper.writeValue(output, lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
