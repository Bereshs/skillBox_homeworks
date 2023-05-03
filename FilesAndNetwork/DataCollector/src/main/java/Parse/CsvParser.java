package Parse;

import Metro.MetroStation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CsvParser {
    public static List<MetroStation> parseFile(String textFileStrings) {
        List<MetroStation> listStations = new ArrayList<>();
        String[] lines = textFileStrings.split("\n");
        for(String line: lines) {
            String[] parameters = line.split(",");
            boolean isHead = parameters[1].matches("[A-z]+");
            if(!isHead){
                DateTimeFormatter formatter = DateTimeFormatter.
                        ofPattern("dd.MM.yyyy");
                LocalDate date =LocalDate.parse(parameters[1], formatter);
                listStations.add(new MetroStation(parameters[0], date));
            }
        }

        return listStations;
    }

}
