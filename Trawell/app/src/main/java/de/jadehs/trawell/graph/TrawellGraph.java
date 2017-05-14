package de.jadehs.trawell.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luxn on 09.05.2017.
 */

public class TrawellGraph {
    private List<Location> locations;
    private List<Route> routes;


    public TrawellGraph() {
        locations = new ArrayList<>();
        routes = new ArrayList<>();
        TripLoader.loadGraph(this);
    }

    public List<Location> dijkstra(Location from, Location to) {
        DijsktraAlgorithm algorithm = new DijsktraAlgorithm(this);
        algorithm.execute(from);
        return algorithm.getPath(to);
    }
    
    public void setLocations(List<Location> locations) {
    	this.locations = locations;
    }
    
    public void setRoutes(List<Route> routes) {
    	this.routes = routes;
    }


    public void addLocation(Location v) {   
        locations.add(v);
    }

    public void addRoute(Route e){       
        routes.add(e);
    }
    
    public List<Location> getLocations() {
    	return locations;
    }
    public List<Route> getRoutes() {
    	return routes;
    }

    public void trawellGraphTODO() {
                
        
        

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
}
