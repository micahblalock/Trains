package trains.app;

import java.util.ArrayList;
import java.util.List;


public class Node implements Comparable<Node> {
	private String name;
	private List<Route> routes;
	
	public Node(String name){
		this.name = name;
		this.routes = new ArrayList<Route>();
	}
	
	public Route getRouteTo(Node to){
		for(Route r: routes){
			if(r.getTo().equals(to)){
				return r;
			}
		}
		return null;
	}
	public List<Route> getRoutes() {
		return this.routes;
	}
	public void addRoute(Route r){
		this.routes.add(r);
	}
	public String getName(){
		return this.name;
	}
		
	public String toString() {
		return this.name;
	}

	@Override
	public int compareTo(Node arg0) {
		return this.getName().compareTo(arg0.getName());
	}
	
}
