import Metro.MetroLine;
import Metro.MetroStation;
import Metro.MoscowMetro;
import Parse.CsvParser;
import Parse.HtmlParser;
import Parse.JsParser;
import org.json.simple.parser.ParseException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        MoscowMetro metro = new MoscowMetro();
        try {
            HtmlParser parser = new HtmlParser("https://skillbox-java.github.io/");
            List<MetroLine> lines = parser.getMetroLines();
            metro.setMetroLines(lines);
            metro.setMetroStations(parser.getMetroStations(lines));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<MetroStation> stationListJson = new ArrayList<>();
        List<MetroStation> stationListCsv = new ArrayList<>();
        FileFinder finder = new FileFinder();
        List<String> files = finder.fileSearch("data/", new String[]{"csv", "json"});
        files.forEach(file -> {
            if (FileFinder.getTypeFile(file).equals("csv")) {
                stationListCsv.addAll(CsvParser.parseFile(FileFinder.getFileLines(file, true)));
            }
            if (FileFinder.getTypeFile(file).equals("json")) {
                try {
                    stationListJson.addAll(JsParser.parseFile(FileFinder.getFileLines(file, false)));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        metro.addAllStations(stationListJson);
        metro.addAllStations(stationListCsv);
        try {
            OutputStream outFileStations = new FileOutputStream("out/stations.json");
            outFileStations.write(metro.getStationsJson().getBytes());
            outFileStations.flush();
            outFileStations.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            OutputStream outFileLines = new FileOutputStream("out/lines.json");
            outFileLines.write(metro.getLinesJson().getBytes());
            outFileLines.flush();
            outFileLines.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}
