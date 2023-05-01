import core.Line;
import core.Station;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class RouteCalculatorTests extends TestCase {
    RouteCalculator calculator;
    StationIndex stationIndex;

    @Override
    protected void setUp() throws Exception {
        stationIndex = new StationIndex();
        List<String> stationsList = List.of(
                "Невско-Московская",
                "Московско-Петроградская",
                "Невско-Василеостровская");

        for (int iterator = 1; iterator <= stationsList.size(); iterator++) {
            stationIndex.addLine(new Line(iterator, stationsList.get(iterator-1)));
        }
        ;

        List<String> firstLineStations = List.of(
                "Беговая",
                "Зенит",
                "Приморская",
                "Василеостровская",
                "Гостиный двор",
                "Маяковская",
                "Площадь Александра Невского");
        createStationLine(firstLineStations, stationIndex.getLine(1));

        List<String> secondfLineStations = List.of(
                "Чёрная речка",
                "Петроградская",
                "Горьковская",
                "Невский проспект",
                "Сенная площадь");
        createStationLine(secondfLineStations, stationIndex.getLine(2));

        List<String> thirdLineStations = List.of(
                "Площадь мужества",
                "Лесная",
                "Выборгская",
                "Площадь ленина",
                "Площадь восстания",
                "Достоевская");
        createStationLine(thirdLineStations, stationIndex.getLine(3));

        stationIndex.addConnection(List.of(
                getStationFromIndex("Невский проспект"),
                getStationFromIndex("Гостиный двор")));
        stationIndex.addConnection(List.of(
                getStationFromIndex("Горьковская"),
                getStationFromIndex("Площадь Ленина")));

        calculator = new RouteCalculator(stationIndex);
    }

    public void createStationLine(List<String> stationList, Line line) {
        for (String stationName : stationList) {
            stationIndex.addStation(new Station(stationName, line));
            line.addStation(getStationFromIndex(stationName));
        }
    }

    public List<Station> createStationList(List<String> namesList) {
        List<Station> stationList = new ArrayList<>();
        for (String station : namesList) {
            stationList.add(getStationFromIndex(station));
        }
        return stationList;
    }

    public void testCalculateDuration() {
        List<Station> route = createStationList(List.of(
                "Приморская",
                "Василеостровская",
                "Гостиный двор",
                "Невский проспект",
                "Горьковская"));

        double actual = RouteCalculator.calculateDuration(route);
        double expected = 13.5;
        assertEquals(expected, actual);
    }

    public void testGetRouteOnTheLine() {
        List<Station> actual = calculator.getShortestRoute(
                getStationFromIndex("Достоевская"),
                getStationFromIndex("Лесная"));

        List<Station> expected = createStationList(List.of(
                "Достоевская",
                "Площадь восстания",
                "Площадь ленина",
                "Выборгская",
                "Лесная"));

        assertEquals(expected, actual);
    }

    public void testGetRouteWithOneConnection() {
        List<Station> actual = calculator.getShortestRoute(
                getStationFromIndex("Горьковская"),
                getStationFromIndex("Маяковская"));
        List<Station> expected = createStationList(List.of(
                "Горьковская",
                "Невский проспект",
                "Гостиный двор",
                "Маяковская"));
        assertEquals(expected, actual);
    }


    public void testGetRouteWithTwoConnection() {
        List<Station> actual = calculator.getShortestRoute(
                getStationFromIndex("Приморская"),
                getStationFromIndex("Выборгская"));
        List<Station> expected = createStationList(List.of(
                "Приморская",
                "Василеостровская",
                "Гостиный двор",
                "Невский проспект",
                "Горьковская",
                "Площадь ленина",
                "Выборгская"));

        assertEquals(expected, actual);
    }


    Station getStationFromIndex(String name) {
        return stationIndex.getStation(name);
    }


}
