package Parse;

import Metro.MetroLine;
import Metro.MetroStation;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HtmlParser {
    private final Document doc;

    public HtmlParser(String urlPath) throws IOException {
        doc = Jsoup.connect(urlPath).get();

    }
    public List<MetroLine> getMetroLines() {
        List<MetroLine> metroLinesList = new ArrayList<>();
        Elements elements = doc.select("span.js-metro-line");
        elements.forEach(element -> {
            String lineNumber  = element.attr("data-line");
            String lineName = element.text();
            metroLinesList.add(new MetroLine(lineNumber, lineName));
        });
        return metroLinesList;
    }

    public List<MetroStation> getMetroStations(List<MetroLine> metroLinesList) {
        List<MetroStation> metroStationsList =  new ArrayList<>();
        if(metroLinesList.size()==0) {
            getMetroLines();
        }
        metroLinesList.forEach(line ->{
            Elements currentLine = doc.select("div[data-depend-set=lines-"+line.getNumber()+"]");
            Elements stations = currentLine.select("p.single-station");
            stations.forEach(station ->{
                String stationName = station.select("span.name").text();
                int countStationConnections = station.select("span.t-icon-metroln").size();
                metroStationsList.add(
                        new MetroStation(stationName,
                                line,
                                countStationConnections > 0
                        ));

            });
        });
        return metroStationsList;
    }

}
