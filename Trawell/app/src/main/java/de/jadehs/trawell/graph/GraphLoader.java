package de.jadehs.trawell.graph;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import de.jadehs.trawell.R;
import de.jadehs.trawell.view.home.MainActivity;


/**
 * 
 * @author luxn
 *
 */
public class GraphLoader {
	
	static List<Location> locations;
	static List<Route> routes;
	static List<Trip> trips;

	public static void loadGraph(TrawellGraph graph) {
		GraphLoader.locations = new ArrayList<>();
		GraphLoader.routes = new ArrayList<>();

		try {
			loadLocations();
			loadRoutes();
//			loadTrips();
		} catch (IOException e) {
			e.printStackTrace();
		}


		for (Location l : GraphLoader.locations) {
			graph.addLocation(l);
		}
		for (Route r : GraphLoader.routes) {

//			for (Trip t : GraphLoader.trips) {
//				if (t.getRoute().getName().equals(r.getName())) {
//					r.addTrip(t);
//				}
//			}

			graph.addRoute(r);
		}

	}

	private static void loadLocations() throws IOException {
		Scanner scanner = GraphLoader.openResourceCSV(R.raw.locations);
		scanner.next(); // erste zeile �berspringen
		Log.d("scan", "next");
		while (scanner.hasNext()) {
			Log.d("scan", "next");
			String[] row = scanner.next().trim().split(",");
			GraphLoader.locations.add(new Location(row[0], row[1], Double.parseDouble(row[2]), Double.parseDouble(row[3]), row[4]));
		}
		scanner.close();		

	}

	private static void loadRoutes() throws IOException {
		Scanner scanner = GraphLoader.openResourceCSV(R.raw.routes);
		scanner.next(); // erste zeile �berspringen
		while (scanner.hasNext()) {
			String[] row = scanner.next().trim().split(",");
			
			Location origin = findLocationByName(row[0]);
			Location destination = findLocationByName(row[1]);
			
			if (origin == null || destination == null) {
				return;			
			}
			Route originRoute = new Route(row[0]+ "-" + row[1],origin, destination);
			origin.addRoute(originRoute);			
			GraphLoader.routes.add(originRoute);
			
			Route destinationRoute = new Route(row[1]+ "-" + row[0], destination, origin);
			destination.addRoute(destinationRoute);
			GraphLoader.routes.add(destinationRoute);
		}
		scanner.close();
		
	}

	private static void loadTrips() {
		Scanner scanner = GraphLoader.openResourceCSV(R.raw.trips);
		scanner.next(); // erste zeile �berspringen
		while (scanner.hasNext()) {
			String[] row = scanner.next().trim().split(",");


			Trip t = new Trip(
					row[0],
					findRouteByName(row[1]),
					new DayTime(row[4]),
					new DayTime(row[5])
			);

			GraphLoader.trips.add(t);


		}
		scanner.close();
	}
	
	private static Location findLocationByName(String name) {
		for (Location l : GraphLoader.locations) {
			if (l.toString().equals(name)) {
				return l;
			}
		}
		return null;
	}
	
	private static Route findRouteByName(String name) {
		for (Route r : GraphLoader.routes) {
			if (r.toString().equals(name)) {
				return r;
			}
		}
		return null;
	}

	private static Scanner openResourceCSV(int name) {
		InputStream inputStream = MainActivity.context.getResources().openRawResource(name);
		Scanner scanner = new Scanner(inputStream);
		scanner.useDelimiter("\n");
		return scanner;
	}

}
