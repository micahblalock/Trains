package trains.app;

import java.util.ArrayList;
import java.util.List;

public class Path implements Comparable<Path> {

	List<Node> nodes;
	double distance=0;
	
	public Path(List<Route> routes, Route destination){
		if(routes == null) {
			throw new IllegalArgumentException("Routes list cannot be null.");
		}
		if(destination == null) {
			throw new IllegalArgumentException("Destination route cannot be null.");
		}
		this.nodes = new ArrayList<Node>();
		if(routes.size() < 1) {
			nodes.add(destination.getFrom());
		} else {
			nodes.add(routes.get(0).getFrom());
		}
		for(Route r : routes){
			distance += r.getDistance();
			nodes.add(r.getTo());
		}
		distance += destination.getDistance();
		nodes.add(destination.getTo());
	}
	
	public Path(List<Node> nodes, double distance){
		this.nodes = new ArrayList<Node>(nodes);
		this.distance = distance;
	}

	public double getDistance() {
		return distance;
	}
	
	public int getNumberOfStops() {
		return nodes.size() - 1;
	}
	
	public String toString() {
		StringBuilder ret= new StringBuilder();
		ret.append(nodes.get(0).getName());
		for(int i = 1; i < nodes.size(); i++){
			ret.append("-" + nodes.get(i).getName()); 
		}
		return ret.toString();		
	}
	
	public static Path getMergedPath(Path path1, Path path2) {
		List<Node> n1 = new ArrayList<Node>(path1.nodes);
		n1.remove(n1.size() - 1);
		n1.addAll(path2.nodes);
		return new Path(n1, path1.getDistance() + path2.getDistance());
	}

	@Override
	public int compareTo(Path o) {
		return (int) (this.distance - o.getDistance());
	}
}
