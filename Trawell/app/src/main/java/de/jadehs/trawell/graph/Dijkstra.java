package de.jadehs.trawell.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Dijkstra {

	static List<Trip> dijkstra(TrawellGraph graph, Location start,  Location target) {
		return dijkstra(graph, start, new DayTime("00:00"), target);
	}
	
	static List<Trip> dijkstra(TrawellGraph graph, Location start, DayTime time, Location target) {

		PriorityQueue<Location> queue = 					// Priority-Queue zum Verwalten der L�nge
				new PriorityQueue<>(); 						// des k�rzesten Weges bis zum
															// Knoten

		for (Location l : graph.getLocations()) { 			// f�r jeden Knoten
			l.distance = Integer.MAX_VALUE; 				// Entfernung ist unendlich
			l.seen = false; 								// Knoten noch nicht gesehen
			l.previousLocation = null; 						// Vorg�nger noch nicht ermittelt
		}

		start.distance = 0; 								// endg�ltige Kosten zum Startknoten
		queue.add(start); 									// erster Eintrag in PriorityQueue

		DayTime temp = time;
		
		while (!queue.isEmpty()) { 							// solange noch Eintr�ge in Priority-Queue

			Location l = queue.poll(); 						// billigster Eintrag in PriorityQueue
			if (l.seen)
				continue; 									// falls schon bearbeitet/besucht: ignorieren
			l.seen = true; 									// als bearbeitet markieren

			for (Route r : l.routes) { 						// f�r jede Nachbarkante r von l tue
				Location dest = r.getDestination(); 		// besorge Zielknoten w
				int costs = r.getWeight(temp); 				// besorge Kosten c zum Zielknoten w
				Trip t = r.getTripForWeight();
				if( t != null)
					temp = 	t.endTime;							// Hole die Ankunftszeit und setze sie als StartZeit f�r die n�chste Iteration
				else
					temp = new DayTime("99:59");
				if (costs < 0)
					throw new RuntimeException("Negativ");	// falls Kantenkosten negativ throw
							
				if (dest.distance > l.distance + costs) { 	// falls Verkuerzung moeglich
					dest.distance = l.distance + costs; 	// berechne Verkuerzung
					dest.previousLocation = l; 						// notiere verursachenden Vorgaenger
					dest.previousTrip = t;					// notiere verursachenden Trip
					queue.add(dest); 						// neuer Eintrag in PriorityQueue
				}
			}
		}
		
		return getPath(target);
	}
	
	private static List<Trip> getPath(Location target) {
		ArrayList<Trip> path = new ArrayList<>();
		Location step = target;		
		while (step.previousLocation != null) {
			path.add(step.previousTrip);
			step = step.previousLocation;
		}		
		Collections.reverse(path);
		return path;
	}
}
