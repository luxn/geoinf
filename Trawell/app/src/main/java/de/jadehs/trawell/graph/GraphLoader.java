package de.jadehs.trawell.graph;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import de.jadehs.trawell.R;


/**
 * 
 * @author luxn
 *
 */
public class GraphLoader {
	
	static List<Location> locations;
	static List<Route> routes;
	static List<Trip> trips;

	public static void loadGraph(TrawellGraph graph, Context ctx) {
		GraphLoader.locations = new ArrayList<>();
		GraphLoader.routes = new ArrayList<>();
		GraphLoader.trips = new ArrayList<>();

		try {
			loadLocations(ctx);
			loadRoutes(ctx);
			loadTrips(ctx);
		} catch (IOException e) {
			e.printStackTrace();
		}


		for (Location l : GraphLoader.locations) {
			graph.addLocation(l);
		}
		for (Route r : GraphLoader.routes) {
			graph.addRoute(r);
		}
		for (Trip t: GraphLoader.trips) {
			graph.addTrip(t);
		}
		

	}

	private static void loadLocations(Context ctx) throws IOException {
		Scanner scanner = GraphLoader.openResourceCSV(R.raw.locations, ctx);
		scanner.next(); // erste zeile �berspringen
		while (scanner.hasNext()) {
			String[] row = scanner.next().trim().split(",");
			GraphLoader.locations.add(new Location(row[0], row[1], Double.parseDouble(row[2]), Double.parseDouble(row[3]), row[4]));
		}
		scanner.close();		

	}

	private static void loadRoutes(Context ctx) throws IOException {
		Scanner scanner = GraphLoader.openResourceCSV(R.raw.routes, ctx);
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

	private static void loadTrips(Context ctx) {
		Scanner scanner = GraphLoader.openResourceCSV(R.raw.trips, ctx);
		scanner.next(); // erste zeile �berspringen
		while (scanner.hasNext()) {
			String[] row = scanner.next().trim().split(",");

			Route r = findRouteByName(row[1]);		
			
			
			Trip t = new Trip(
					row[0],
					r,
					new DayTime(row[4]),
					new DayTime(row[5])
			);
			

			r.addTrip(t);

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

	private static Scanner openResourceCSV(int res, Context ctx) {

		InputStream inputStream = ctx.getResources().openRawResource(res);
		Scanner scanner = new Scanner(inputStream);
		scanner.useDelimiter("\n");
		return scanner;

	}

}
