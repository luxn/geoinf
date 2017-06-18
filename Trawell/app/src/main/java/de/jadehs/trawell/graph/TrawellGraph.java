package de.jadehs.trawell.graph;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luxn on 09.05.2017.
 */

public class TrawellGraph {


    private List<Location> locations;
    private List<Route> routes;
    private List<Trip> trips;

    private Map<String, Location> locationMap;
    private Map<String, Route> routeMap;
    private Map<String, Trip> tripMap;


    private static TrawellGraph graph;


    private TrawellGraph(Context ctx) {
    	
        locations = new ArrayList<>();
        routes = new ArrayList<>();
        trips = new ArrayList<>();
        
        locationMap = new HashMap<>();
        routeMap = new HashMap<>();
        tripMap = new HashMap<>();
        
        GraphLoader.loadGraph(this, ctx);
    }

    //lazy loading, single instance
    public static TrawellGraph get(Context ctx) {
        if (TrawellGraph.graph == null) {
        	TrawellGraph.graph = new TrawellGraph(ctx);
        }
        return TrawellGraph.graph;
    }
    
   

    public List<Location> dijkstra(Location from, Location to) {
        List<Trip> calcTrips = Dijkstra.dijkstra(graph, from, to);
        
        List<Location> res = new ArrayList<>();
        res.add(from);
        for (Trip t : calcTrips) {
        	res.add(t.getRoute().getDestination());
        }
        
        return res;
    }
    
    public List<Location> dijkstra(Location... via) {  	    
    	
    	List<Location> path = new ArrayList<>();
    	
    	for (int i = 0; i < via.length -1 ; i++) {    		
        	path.addAll(dijkstra(via[i], via[i+1]));  
        	if (i < via.length -2) {
        		path.remove(path.size()-1);
        	}
    	}
    	    	    	
		return path;    	
    }
    
    public List<Trip> dijkstra(Location from, Location to, DayTime time) {
    	return Dijkstra.dijkstra(graph, from, time, to);
    }

    void addLocation(Location l) {
        locations.add(l);
        locationMap.put(l.toString(), l);
    }

    void addRoute(Route r){
        routes.add(r);
        routeMap.put(r.toString(), r);
    }
    
    void addTrip(Trip t) {
    	trips.add(t);
    	tripMap.put(t.toString(), t);
    }
    
    public List<Location> getLocations() {
    	return locations;
    }
    public List<Route> getRoutes() {
    	return routes;
    }
    public List<Trip> getTrips() {
    	return trips;
    }

    public Map<String, Location> getLocationsAsMap() { return locationMap; }
    public Map<String, Route> getRoutesAsMap() { return routeMap; }
    public Map<String, Trip> getTripsAsMap() { return tripMap; }

   
    public Location getLocationByName(String name) {
        return locationMap.get(name);
    }
  
    public Route getRouteByName(String name) {
        return routeMap.get(name);
    }
    

}
