package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author luxn
 *
 */
public class DijsktraAlgorithm {
	private final List<Location> nodes;
	private final List<Route> edges;
	private Set<Location> settledNodes;
	private Set<Location> unSettledNodes;
	private Map<Location, Location> predecessors;
	private Map<Location, Integer> distance;

	public DijsktraAlgorithm(TrawellGraph graph) {
	    this.nodes = new ArrayList<>(graph.getLocations());
	    this.edges = new ArrayList<>(graph.getRoutes());
	 }

	public void execute(Location source) {
		settledNodes = new HashSet<>();
		unSettledNodes = new HashSet<>();
		distance = new HashMap<>();
		predecessors = new HashMap<>();
		distance.put(source, 0);
		unSettledNodes.add(source);
		while (!unSettledNodes.isEmpty() && unSettledNodes.size() > 0) {
			Location node = getMinimum(unSettledNodes);
			settledNodes.add(node);
			unSettledNodes.remove(node);
			findMinimalDistances(node);
		}
	}

	private void findMinimalDistances(Location node) {
		List<Location> adjacentNodes = getNeighbors(node);
		for (Location target : adjacentNodes) {
			if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)) {
				distance.put(target, getShortestDistance(node) + getDistance(node, target));
				predecessors.put(target, node);
				unSettledNodes.add(target);
			}
		}

	}

	private int getDistance(Location node, Location target) {
		for (Route route : edges) {
			if (route.getSource().equals(node) && route.getDestination().equals(target)) {
				return route.getWeight();
			}
		}
		throw new RuntimeException("Should really never ever happen");
	}

	private List<Location> getNeighbors(Location node) {
		List<Location> neighbors = new ArrayList<>();
		for (Route route : edges) {
			if (route.getSource().equals(node) && !isSettled(route.getDestination())) {
				neighbors.add(route.getDestination());
			}
		}
		return neighbors;
	}

	private Location getMinimum(Set<Location> locations) {
		Location minimum = null;
		for (Location Location : locations) {
			if (minimum == null) {
				minimum = Location;
			} else {
				if (getShortestDistance(Location) < getShortestDistance(minimum)) {
					minimum = Location;
				}
			}
		}
		return minimum;
	}

	private boolean isSettled(Location location) {
		return settledNodes.contains(location);
	}

	private int getShortestDistance(Location destination) {
		Integer d = distance.get(destination);
		if (d == null) {
			return Integer.MAX_VALUE;
		} else {
			return d;
		}
	}

	public List<Location> getPath(Location target) {
		ArrayList<Location> path = new ArrayList<>();
		Location step = target;
		if (predecessors.get(step) == null) {
			return new ArrayList<>();
		}
		path.add(step);
		while (predecessors.get(step) != null) {
			step = predecessors.get(step);
			path.add(step);
		}
		Collections.reverse(path);
		return path;
	}
}
