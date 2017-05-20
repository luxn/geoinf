package de.jadehs.trawell.graph;

import android.renderscript.ScriptGroup;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import de.jadehs.trawell.R;
import de.jadehs.trawell.view.MainActivity;


/**
 * 
 * @author luxn
 *
 */
public class TripLoader {
	
	static List<Location> locations;
	static List<Route> routes;

	public static void loadGraph(TrawellGraph graph) {
		TripLoader.locations = new ArrayList<>();
		TripLoader.routes = new ArrayList<>();

		try {
			loadLocations();
			loadRoutes();
		} catch (IOException e) {
			e.printStackTrace();
		}


		for (Location l : TripLoader.locations) {
			graph.addLocation(l);
		}
		for (Route r : TripLoader.routes) {
			graph.addRoute(r);
		}

	}

	private static void loadLocations() throws IOException {
		Scanner scanner = TripLoader.openResourceCSV(R.raw.locations);
		scanner.next(); // erste zeile �berspringen
		while (scanner.hasNext()) {
			String[] row = scanner.next().trim().split(",");
			TripLoader.locations.add(new Location(row[0], row[1], Double.parseDouble(row[2]), Double.parseDouble(row[3])));
		}
		scanner.close();		

	}

	private static void loadRoutes() throws IOException {
		Scanner scanner = TripLoader.openResourceCSV(R.raw.routes);
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
			TripLoader.routes.add(originRoute);
			
			Route destinationRoute = new Route(row[1]+ "-" + row[0], destination, origin);
			destination.addRoute(destinationRoute);
			TripLoader.routes.add(destinationRoute);
		}
		scanner.close();
		
	}

	private static void loadTrips() {

	}
	
	private static Location findLocationByName(String name) {
		for (Location l : TripLoader.locations) {
			if (l.toString().equals(name)) {
				return l;
			}
		}
		return null;
	}
	
	private static Route findRouteByName(String name) {
		for (Route r : TripLoader.routes) {
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
