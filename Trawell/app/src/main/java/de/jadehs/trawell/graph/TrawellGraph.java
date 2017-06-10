package de.jadehs.trawell.graph;

import android.content.Context;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luxn on 09.05.2017.
 */

public class TrawellGraph {


    private ArrayList<Location> locations;
    private List<Route> routes;

    private Map<String, Location> locationMap;
    private Map<String, Route> routeMap;


    private static TrawellGraph graph;


    private TrawellGraph(Context ctx) {
        locations = new ArrayList<>();
        routes = new ArrayList<>();
        locationMap = new HashMap<>();
        routeMap = new HashMap<>();
        GraphLoader.loadGraph(this, ctx);
    }

    //lazy loading, single instance
    public static TrawellGraph get(Context ctx) {
        if (TrawellGraph.graph == null) {
            return new TrawellGraph(ctx);
        }
        return TrawellGraph.graph;
    }

    public List<Location> dijkstra(Location from, Location to) {
        DijsktraAlgorithm algorithm = new DijsktraAlgorithm(this);
        algorithm.execute(from);
        return algorithm.getPath(to);
    }

    void addLocation(Location l) {
        locations.add(l);
        locationMap.put(l.toString(), l);
    }

    void addRoute(Route r){
        routes.add(r);
        routeMap.put(r.toString(), r);
    }
    
    public ArrayList<Location> getLocations() {
    	return locations;
    }
    public List<Route> getRoutes() {
    	return routes;
    }

    public Map<String, Location> getLocationAsMap() { return locationMap; }
    public Map<String, Route> getRouteAsMap() { return routeMap; }

    @Nullable
    public Location getLocationByName(String name) {
        return locationMap.get(name);
    }

    @Nullable
    public Route getRouteByName(String name) {
        return routeMap.get(name);
    }


        
        

//        Location hamburg = new Location("Hamburg", "DE", new Coordinate(50.23, 5.23));
//        Location frankfurt = new Location("Frankfurt", "DE", new Coordinate(50.23, 5.23));
//
//        graph.addLocation(hamburg);
//        graph.addLocation(frankfurt);
//
//        Route hamToFra = new Route("ICE-123",hamburg, frankfurt);
//        hamToFra.addTrip(new Trip("ICE 72", new DayTime(13, 24), new DayTime(17, 00)));
//
//
//        Route fraToHam = new Route("ICE-456", frankfurt, hamburg);
//        fraToHam.addTrip(new Trip("ICE 67", new DayTime(11, 20), new DayTime(15, 05)));
//
//
//        graph.addRoute(hamToFra);
//        graph.addRoute(fraToHam);

}
