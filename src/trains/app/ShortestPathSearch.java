package trains.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class ShortestPathSearch {

	private KiwilandGraph g;
	private Map<String,Double> minDistance = new HashMap<String,Double>();
	private Map<Node,Node> previousNode = new HashMap<Node,Node>();
	
	
	public ShortestPathSearch(KiwilandGraph graph) {
		this.g = graph;
		for(Node n : g.getNodeRoutes().values()){
			minDistance.put(n.getName(), Double.POSITIVE_INFINITY);
		}
	}
	
	private void init(){
		for(Double d : minDistance.values()){
			d = Double.POSITIVE_INFINITY;
		}
		previousNode.clear();
	}
	
	private void setMinDistance(Node node, double distance){
		minDistance.put(node.getName(), distance);
	}
	
	private double getMinDistance(Node node){
		return minDistance.get(node.getName());
	}
	
	private void setPrevious(Node next, Node current) {
		previousNode.put(next, current);
	}
	
	public Path findShortestRoute(String fromName, String toName) {
		init();
		Node from = g.getNode(fromName);
		Node to = g.getNode(toName);
		
		setMinDistance(from, 0);

		PriorityQueue<Node> queue = new PriorityQueue<Node>();
		queue.add(from);
		
		while(!queue.isEmpty()){
			Node current = queue.poll();
			for(Route r : current.getRoutes()) {

				Node next = r.getTo();
				double distance = r.getDistance();
				double distanceThroughCurrent = getMinDistance(current) + distance;
				
				if(distanceThroughCurrent < getMinDistance(next)) {
					queue.remove(next);
					setMinDistance(next, distanceThroughCurrent);
					setPrevious(next, current);
					queue.add(next);
				}
			}
		}
		
		if(!previousNode.containsKey(to)) {
			throw new PathNotFoundException("No path found from " + fromName + " to " + toName);
		}
		
		List<Node> l = new ArrayList<Node>();
		for(Node node = to; node != null; node = getPrevious(node)){
			l.add(node);
		}
		double distance = minDistance.get(to.getName());
		Collections.reverse(l);
		
		return new Path(l, distance);
	}
	
	private Node getPrevious(Node node){
		if(!previousNode.containsKey(node)) {
			return null;
		}
		return previousNode.get(node);
	}

	
}
