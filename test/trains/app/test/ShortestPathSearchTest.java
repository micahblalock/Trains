package trains.app.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import trains.app.KiwilandGraph;
import trains.app.Path;
import trains.app.ShortestPathSearch;

public class ShortestPathSearchTest {

	private KiwilandGraph g;
	private ShortestPathSearch search;
	
	@Before
	public void setUp() throws Exception {
		String[] gIn = "AB5,BC4,CD8,DC8,DE6,AD5,CE2,EB3,AE7".split(",");
		g = new KiwilandGraph(gIn);
		search = new ShortestPathSearch(g);
	}

	@Test
	public void testFindShortestRoute() {
		Path ac = search.findShortestRoute("A", "C");
		
		assertTrue(ac.getDistance() == 9.0);
	}

}

//8. The length of the shortest route (in terms of distance to travel) from A to C.
//9. The length of the shortest route (in terms of distance to travel) from B to B.

