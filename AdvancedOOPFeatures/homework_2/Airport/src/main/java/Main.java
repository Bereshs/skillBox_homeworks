import com.skillbox.airport.Airport;
import com.skillbox.airport.Flight;
import com.skillbox.airport.Terminal;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Airport airport = Airport.getInstance();
        System.out.println(findPlanesLeavingInTheNextTwoHours(airport));

    }

    public static List<Flight> findPlanesLeavingInTheNextTwoHours(Airport airport) {
        List<Flight> flightsToLeaving = new ArrayList<>();
        long nowTime = System.currentTimeMillis();
        long nowTimePlus2H = nowTime + 2 * 60 * 60 * 1000;

        flightsToLeaving = airport.getTerminals().stream()
                .map(Terminal::getFlights)
                .flatMap(Collection::stream)
                .filter(flight -> flight.getType().equals(Flight.Type.DEPARTURE))
                .filter(flight -> flight.getDate().before(new Date(nowTimePlus2H)) &&
                        flight.getDate().after(new Date(nowTime)))
                .collect(Collectors.toList());

        return flightsToLeaving;
    }

}