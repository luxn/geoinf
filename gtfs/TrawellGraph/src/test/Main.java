package test;

import java.util.List;

import graph.DayTime;
import graph.Location;
import graph.Route;
import graph.TrawellGraph;

public class Main {

	public static void main(String[] args) {
		TrawellGraph graph = TrawellGraph.get(new Context());
		
		Location amsterdam = graph.getLocationByName("Amsterdam");
		Location münchen = graph.getLocationByName("München");
		Location lyon = graph.getLocationByName("Lyon");
		Location berlin = graph.getLocationByName("Berlin");
		Location brüssel = graph.getLocationByName("Brüssel");
		Location marseille = graph.getLocationByName("Marseille");
		
		
		
		DayTime startTime = new DayTime("8:30");
		Location startLocation = graph.getLocationByName("Frankfurt");
		Location endLocation = graph.getLocationByName("Nice");
		
		List<Location> path = graph.dijkstraTime(startLocation, startTime, endLocation); 
		
		
		
		
//		
//		System.out.println("Route 1---");
//		
//		List<Location> pathToTravel = graph.dijkstraRaw(lyon, berlin);
//		
//		for (Location l : pathToTravel) {
//			System.out.println(l);
//		}
//		
//		System.out.println("Route 2---");
//		
//
//		
//		List<Location> pathToTravel2 = graph.dijkstraRaw(amsterdam, lyon, berlin, münchen);
//		
//		for (Location l : pathToTravel2) {
//			System.out.println(l);
//		}
//		
//		
//		for (Route r : graph.getRoutes()) {
//			if (r.getWeight() < 0 ) {
//				System.err.println("maaa");
//			}
//			System.out.println(r.getName() + " " + r.getDistance() + " " + r.getWeight());
//		}
	}
}
