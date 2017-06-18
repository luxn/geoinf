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

		PriorityQueue<Location> queue = 					// Priority-Queue zum Verwalten der L�nge in Minuten
				new PriorityQueue<>(); 						// des k�rzesten Weges bis zum Knoten(Location)															

		for (Location l : graph.getLocations()) { 			// f�r jeden Knoten(Location)
			l.distance = Integer.MAX_VALUE; 				// Entfernung ist erstmal unendlich
			l.seen = false; 								// Knoten(Location) noch nicht vorher besucht/gesehen
			l.previousLocation = null; 						// Vorg�nger somit noch nicht ermittelt f�r sp�tere 
															// inverse Pfadbildung
		}

		start.distance = 0; 								// endg�ltige Kosten zum Startknoten/Ort
		start.time = time;									// Startzeit
		queue.add(start); 									// erster Eintrag in PriorityQueue (hier wird angefangen)
				
		
		while (!queue.isEmpty()) { 							// solange noch Eintr�ge in Priority-Queue

			Location l = queue.poll(); 						// billigster Eintrag in der PriorityQueue - Entfernt ihn auch
			if (l.seen)
				continue; 									// falls schon besucht worden: ignorieren
			l.seen = true; 									// als besucht setzen

			for (Route r : l.routes) { 						// f�r jede Kante(Route)
				Location dest = r.getDestination(); 		// erhalte Zielort 
				int costs = r.getWeight(l.time); 			// erhalte Kosten c zum Zielort unter ber�cksichtung der Ankunftszeit am Bahnhof/Ort				
								
				if (costs < 0)
					throw new RuntimeException("Negative Kosten :-(");	// falls Kantenkosten negativ werfe Exception (sollte nie passieren)
							
				if (dest.distance > l.distance + costs) { 	// falls eine Verk�rzung der Kosten m�glich sind
					dest.distance = l.distance + costs; 	
					dest.previousLocation = l; 				
					Trip t = r.getTripForWeight();			// hole den Trip der dem vorherigen Kosten zugeordnet worden ist					dest.previousTrip = t;					
					dest.time = t.endTime;					// Ankunft am Zielort setzen
					dest.previousTrip = t;					//Trip der zu dem Knoten/Ort gef�hrt hat setzen
					queue.add(dest); 						// neuer Eintrag des Zielortes in die PriorityQueue setzen
				}
			}
		}
		
		//Queue ist leer und der Pfad kann invers gebildet werden
		
		return getPath(target);
	}
	
	private static List<Trip> getPath(Location target) {
		ArrayList<Trip> path = new ArrayList<>();
		Location step = target;		
		while (step.previousLocation != null) { //solange der Vorg�ngerort nicht null ist
			path.add(step.previousTrip);
			step = step.previousLocation;
		}		
		Collections.reverse(path); //invertieren
		return path;
	}
}
