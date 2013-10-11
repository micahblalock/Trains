package trains.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PathFinder {

	private KiwilandGraph g;
	
	public PathFinder(KiwilandGraph graph) {
		this.g = graph;
	}
	
	public Path getPath(String[] path){

		Node current = g.getNodeRoutes().get(path[0]);
		List<Node> nodes = new ArrayList<Node>();
		nodes.add(current);
		double total = 0;
		for(int i = 1; i < path.length; i++){
			Node next = g.getNodeRoutes().get(path[i]);
			Route route = current.getRouteTo(next);
			if(route == null) {
				throw new PathNotFoundException("NO SUCH ROUTE");
			} 
			nodes.add(next);
			current = next;
			total += route.getDistance();
		}
		return new Path(nodes,total);
	}

	public Path findShortestCycle(String fromName) {
		List<Path> all = findDistinctPaths(fromName, fromName);
		Collections.sort(all);
		return all.get(0);
	}
	
	public List<Path> findAllPaths(String fromName, String destinationName, int maxStops, boolean exact) {
		List<Path> result = new ArrayList<Path>();
		List<Path> all = walkAllPaths(fromName, destinationName, maxStops);
		
		for(Path p : all){
			if(exact){
				if(p.getNumberOfStops() == maxStops) {
					result.add(p);
				}
			} else {
				if(p.getNumberOfStops() <= maxStops) {
					result.add(p);					
				}
			}
		}
		
		return result;
		
	}

	public List<Path> findAllPaths(String fromName, String destinationName, double maxDistance) {
//		List<Path> result = new ArrayList<Path>();
		List<Path> all = walkAllPaths(fromName, destinationName, maxDistance);
		return all;
//		for(Path p : all){
//			if(p.getDistance() < maxDistance) {
//				result.add(p);
//			} else {
//				System.out.println(p + ": " + p.getDistance() + " exceeds maxDistance.");
//			}
//		}
		
//		List<Path> currentList = new ArrayList<Path>(result);
//		List<Path> nextList = new ArrayList<Path>();
//		boolean done=false;
//		do {
//			combineDistinctPaths(currentList, result, nextList, maxDistance);
//			if(nextList.size() > 0){
//				result.addAll(nextList);
//				currentList.clear();
//				currentList.addAll(nextList);
//				nextList.clear();
//			} else {
//				done=true;
//			}
//		} while(!done);
//		
//		return result;
	}
	
//	private void combineDistinctPaths(List<Path> nodes, List<Path> distinctList, List<Path> result, double maxDistance) {
//		for(Path p : nodes) {
//			for(Path p1 : distinctList) {
//				if(p.getDistance() + p1.getDistance() < maxDistance) {
//					result.add(Path.getMergedPath(p, p1));
//				}
//			}
//		}
//	}

	private List<Path> walkAllPaths(String fromName, String destinationName, double maxDistance) {
		Node from = g.getNode(fromName);
		Node destination = g.getNode(destinationName);
		List<Path> allPaths = new ArrayList<Path>();
		List<Route> curpath = new ArrayList<Route>();
		double currentDistance = 0;
		for(Route r : from.getRoutes()){
			buildPaths(r.getTo(), destination, r,  curpath, allPaths, currentDistance, maxDistance);
			curpath.clear();
		}
		return allPaths;
	}

	private List<Path> walkAllPaths(String fromName, String destinationName, int stops) {
		Node from = g.getNode(fromName);
		Node destination = g.getNode(destinationName);
		List<Path> allPaths = new ArrayList<Path>();
		List<Route> curpath = new ArrayList<Route>();
		for(Route r : from.getRoutes()){
			buildPaths(r.getTo(), destination, r,  curpath, allPaths, stops);
			curpath.clear();
		}
		return allPaths;
	}

	private void buildPaths(Node current, Node to, Route route, List<Route> currentPath, List<Path> allPaths, int stops){
		if(current.equals(to)){
			allPaths.add(new Path(currentPath, route));
//			return;
		}
		if(currentPath.size() >= stops - 1) {
			return;
		}
		currentPath.add(route);
		for(Route r : current.getRoutes()){
			buildPaths(r.getTo(), to, r, currentPath, allPaths, stops);
		}
		currentPath.remove(currentPath.size() -1);
	}

	private void buildPaths(Node current, Node to, Route route, List<Route> currentPath, List<Path> allPaths, double curDistance, double maxDistance){
		if(current.equals(to)){
			allPaths.add(new Path(currentPath, route));
		}
		if(curDistance >= maxDistance) {
			return;
		}
		currentPath.add(route);
		curDistance += route.getDistance();
		for(Route r : current.getRoutes()){
			if(curDistance + r.getDistance() < maxDistance) {
				buildPaths(r.getTo(), to, r, currentPath, allPaths, curDistance, maxDistance);
			}
		}
		if(currentPath.size() > 1) {
			curDistance -= currentPath.get(currentPath.size() - 1).getDistance();
			currentPath.remove(currentPath.size() -1);
		}
	}
	
	
	private List<Path> findDistinctPaths(String fromName, String destinationName) {
		Node from = g.getNode(fromName);
		Node destination = g.getNode(destinationName);
		List<Path> allPaths = new ArrayList<Path>();
		List<Route> curpath = new ArrayList<Route>();
		for(Route r : from.getRoutes()){
			if(!curpath.contains(r)){
				buildPaths(r.getTo(), destination, r,  curpath, allPaths);
			}
		}
		return allPaths;
	}

	private void buildPaths(Node current, Node to, Route route, List<Route> currentPath, List<Path> allPaths){
		if(current.equals(to)){
			allPaths.add(new Path(currentPath, route));
			return;
		}
		currentPath.add(route);
		for(Route r : current.getRoutes()){
			if(!currentPath.contains(r)){
				buildPaths(r.getTo(), to, r, currentPath, allPaths);
			}
		}
		currentPath.remove(currentPath.size() -1);
	}

	
}
