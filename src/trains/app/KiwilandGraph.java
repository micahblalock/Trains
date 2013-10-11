package trains.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class KiwilandGraph {

	private static final int WHITE=0;
	private static final int BLACK=2;
	private static final int GRAY=1;
	
	// adjacenct routes
	private Map<String,Node> nodeRoutes = new HashMap<String,Node>();
	
	public KiwilandGraph(String filename) {
		BufferedReader br = null;		 
		try { 
			String route;
			br = new BufferedReader(new FileReader(filename));
			while ((route = br.readLine()) != null) {
				parseRoute(route);
			}

		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				throw new IllegalArgumentException(ex);
			}
		}		
	}
	
	public KiwilandGraph(String[] in) {
		for(String r: in){
			parseRoute(r);
		}
	}
	
	private void parseRoute(String route) {

		String from = route.substring(0,1);
		String to = route.substring(1,2);
		int weight = Integer.parseInt(route.substring(2,3));
		if(!nodeRoutes.containsKey(from)){
			nodeRoutes.put(from, new Node(from));
		}
		if(!nodeRoutes.containsKey(to)){
			nodeRoutes.put(to, new Node(to));
		}
		nodeRoutes.get(from).getRoutes().add(new Route(nodeRoutes.get(from),nodeRoutes.get(to),weight));
		
	}
	
	public Node getNode(String nodeName) {
		if(nodeRoutes.containsKey(nodeName)){
			return nodeRoutes.get(nodeName);
		} else {
			throw new NodeNotFoundException("Node not found in graph");
		}
	}
	
	public Map<String,Node> getNodeRoutes() {
		return nodeRoutes;
	}
	
	
	/**
     * Return a string representation of this graph.
     */
    public String toString() {
        String NEWLINE = System.getProperty("line.separator");
        StringBuilder s = new StringBuilder();

        for (Node node : nodeRoutes.values()) {
            s.append(node.getName() + ": " + NEWLINE);
            for (Route e : node.getRoutes()) {
                s.append("   " + e );
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

//		String[] gIn = "AB5,BC4,CD8,DC8,DE6,AD5,CE2,EB3,AE7".split(",");
		
		KiwilandGraph g = new KiwilandGraph(args[0]);
				
		PathFinder pathFinder = new PathFinder(g);
//		1. The distance of the route A-B-C.
//		Output #1: 9
		String[] abc = {"A", "B", "C"};
		Path abcPath = pathFinder.getPath(abc);
		System.out.println("Output  #1: " + abcPath.getDistance());
		
//		2. The distance of the route A-D.
//		Output #2: 5
		String[] ad = {"A", "D"};
		Path adPath = pathFinder.getPath(ad);
		System.out.println("Output  #2: " + adPath.getDistance());

//		3. The distance of the route A-D-C.
//		Output #3: 13
		String[] adc = {"A", "D", "C"};
		Path adcPath = pathFinder.getPath(adc);
		System.out.println("Output  #3: " + adcPath.getDistance());

//		4. The distance of the route A-E-B-C-D.
//		Output #4: 22
		String[] aebcd = {"A", "E", "B", "C", "D"};
		Path aebcdPath = pathFinder.getPath(aebcd);
		System.out.println("Output  #4: " + aebcdPath.getDistance());

//		5. The distance of the route A-E-D.
//		Output #5: NO SUCH ROUTE
		String[] aed = {"A", "E", "D"};
		try {
			Path aedPath = pathFinder.getPath(aed);
		} catch(PathNotFoundException e){
			System.out.println("Output  #5: " + "NO SUCH ROUTE");
		}

//		6. The number of trips starting at C and ending at C with a maximum of 3 stops.  
//		Output #6: 2
		List<Path> cc3 = pathFinder.findAllPaths("C", "C", 3, false);
		System.out.println("Output  #6: " + cc3.size());
		
//		7. The number of trips starting at A and ending at C with exactly 4 stops.  		
//		Output #7: 3
		List<Path> ac4 = pathFinder.findAllPaths("A", "C", 4, true);
		System.out.println("Output  #7: " + ac4.size());
		
//		8. The length of the shortest route (in terms of distance to travel) from A to C.
//		Output #8: 9
		ShortestPathSearch shortestPath = new ShortestPathSearch(g);
		Path ac = shortestPath.findShortestRoute("A", "C");
		System.out.println("Output  #8: " + ac.getDistance());
		
//		9. The length of the shortest route (in terms of distance to travel) from B to B.
//		Output #9: 9
		Path bb = pathFinder.findShortestCycle("B");
		System.out.println("Output  #9: " + bb.getDistance());

//		10.The number of different routes from C to C with a distance of less than 30.  
//		Output #10: 7
		List<Path> cc = pathFinder.findAllPaths("C", "C", 30.0);
		System.out.println("Output #10: " + cc.size());
		
		
	}
	
	
}

/*
PROBLEM ONE: TRAINS

The local commuter railroad services a number of towns in Kiwiland.  Because of monetary concerns, all of the tracks are 'one-way.'  
That is, a route from Kaitaia to Invercargill does not imply the existence of a route from Invercargill to Kaitaia.  In fact, even 
if both of these routes do happen to exist, they are distinct and are not necessarily the same distance!

The purpose of this problem is to help the railroad provide its customers with information about the routes.  In particular, you will 
compute the distance along a certain route, the number of different routes between two towns, and the shortest route between two towns.

Input:  A directed graph where a node represents a town and an edge represents a route between two towns.  The weighting of the edge 
represents the distance between the two towns.  A given route will never appear more than once, and for a given route, the starting and 
ending town will not be the same town.

Output: For test input 1 through 5, if no such route exists, output 'NO SUCH ROUTE'.  Otherwise, follow the route as given; do not make 
any extra stops!  For example, the first problem means to start at city A, then travel directly to city B (a distance of 5), then directly 
to city C (a distance of 4).

1. The distance of the route A-B-C.
2. The distance of the route A-D.
3. The distance of the route A-D-C.
4. The distance of the route A-E-B-C-D.
5. The distance of the route A-E-D.
6. The number of trips starting at C and ending at C with a maximum of 3 stops.  
In the sample data below, there are two such trips: C-D-C (2 stops). and C-E-B-C (3 stops).
7. The number of trips starting at A and ending at C with exactly 4 stops.  
In the sample data below, there are three such trips: A to C (via B,C,D); A to C (via D,C,D); and A to C (via D,E,B).
8. The length of the shortest route (in terms of distance to travel) from A to C.
9. The length of the shortest route (in terms of distance to travel) from B to B.
10.The number of different routes from C to C with a distance of less than 30.  
In the sample data, the trips are: CDC, CEBC, CEBCDC, CDCEBC, CDEBC, CEBCEBC, CEBCEBCEBC.

Test Input:
For the test input, the towns are named using the first few letters of the alphabet from A to D.  
A route between two towns (A to B) with a distance of 5 is represented as AB5.
Graph: AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7
Expected Output:
Output #1: 9
Output #2: 5
Output #3: 13
Output #4: 22
Output #5: NO SUCH ROUTE
Output #6: 2
Output #7: 3
Output #8: 9
Output #9: 9
Output #10: 7
*/