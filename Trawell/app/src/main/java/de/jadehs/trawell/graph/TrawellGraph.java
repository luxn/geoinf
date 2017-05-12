package de.jadehs.trawell.graph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lux on 09.05.2017.
 */

public class TrawellGraph {
    private Map<String, Location> locations;
    private Map<String, Route> routes;


    private TrawellGraph() {
        locations = new HashMap<>();
        routes = new HashMap<>();
    }

    public List<Route> dijkstra(Location from, Location to) {
        return null;
    }


    public boolean addLocation(Location v) {
        if (locations.containsValue(v)) {
            return false;
        }
        locations.put(v.toString(), v);
        return true;
    }

    public boolean addRoute(Route e){
        if (routes.containsValue(e)) {
            return false;
        }
        routes.put(e.toString(), e);
        return true;
    }

    static TrawellGraph populateDefaultGraph() {
        TrawellGraph graph = new TrawellGraph();

        Location hamburg = new Location("Hamburg", "DE", new Coordinate(50.23, 5.23));
        Location frankfurt = new Location("Frankfurt", "DE", new Coordinate(50.23, 5.23));

        graph.addLocation(hamburg);
        graph.addLocation(frankfurt);

        Route hamToFra = new Route("ICE-123", "Deutsche Bahn", 500, new Duration(3, 36));
        hamToFra.setOrigin(hamburg);
        hamToFra.setDestination(frankfurt);
        hamToFra.addTrip(new Trip(new DayTime(13, 24), new DayTime(17, 00), Trip.DayOfWeek.DAILY));


        Route fraToHam = new Route("ICE-456", "Deutsche Bahn", 500, new Duration(3, 36));
        fraToHam.setOrigin(frankfurt);
        fraToHam.setDestination(hamburg);
        fraToHam.addTrip(new Trip(new DayTime(11, 20), new DayTime(15, 05), Trip.DayOfWeek.DAILY));


        graph.addRoute(hamToFra);
        graph.addRoute(fraToHam);

        return graph;

    }
}
