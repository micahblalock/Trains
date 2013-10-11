package trains.app.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import trains.app.KiwilandGraph;
import trains.app.PathFinder;
import trains.app.Path;
import trains.app.PathNotFoundException;

public class PathFinderTest {

	KiwilandGraph g;
	PathFinder pathFinder;

	@Rule
    public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		String[] gIn = "AB5,BC4,CD8,DC8,DE6,AD5,CE2,EB3,AE7".split(",");
		g = new KiwilandGraph(gIn);
		pathFinder = new PathFinder(g);
	}

	@Test
	public void testGetPath() {
		String[] route = {"A","B","C"};
		Path path = pathFinder.getPath(route);
		assertEquals("A-B-C", path.toString());
		assertTrue(path.getDistance() == 9.0);
	}

	@Test
	public void testGetPathInvalidRoute() {
		String[] route = {"A","E","C"};

		thrown.expect(PathNotFoundException.class);

		Path path = pathFinder.getPath(route);
		
	}
	

	@Test
	public void testFindAllPathsMaxDistance() {
		List<Path> allPaths = pathFinder.findAllPaths("C", "C", 30.0);
		for(Path p:allPaths){
			System.out.println(p + ": " + p.getDistance());
		}
		assertTrue(allPaths.size() == 7);
	}

	@Test
	public void testFindAllPathsMaxStops() {
		List<Path> allPaths = pathFinder.findAllPaths("C", "C", 3, false);
		assertTrue(allPaths.size() == 2);
	}

	@Test
	public void testFindAllPathsMaxStopsExact() {
		List<Path> allPaths = pathFinder.findAllPaths("A", "C", 4, true);
		System.out.println(g);
		assertTrue(allPaths.size() == 3);
	}
	
	@Test
	public void testFindShortestCycle() {
		Path path = pathFinder.findShortestCycle("B");
		System.out.println(path + ": " + path.getDistance());
		assertTrue(path.getDistance() == 9.0);
	}
}

//6. The number of trips starting at C and ending at C with a maximum of 3 stops.  
//In the sample data below, there are two such trips: C-D-C (2 stops). and C-E-B-C (3 stops).
//7. The number of trips starting at A and ending at C with exactly 4 stops.  
//In the sample data below, there are three such trips: A to C (via B,C,D); A to C (via D,C,D); and A to C (via D,E,B).
//10.The number of different routes from C to C with a distance of less than 30.  
//In the sample data, the trips are: CDC, CEBC, CEBCDC, CDCEBC, CDEBC, CEBCEBC, CEBCEBCEBC.


