You can run the application with the following command:
java -jar KiwilandGraph.jar ./TrainPaths.txt


The main class, KiwilandGraph, accepts input in the form of routes, for example, AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7. The input is parsed and translated into Node and Route objects which are used to build a directed graph. The graph, contained in the KiwilandGraph class, is implemented as a collection of nodes containing adjacent weighted edges. Weights represent the distance to the next node. 

Two help classes, ShortestPathSearch and PathFinder, are used to order and search the graph. The main method in KiwilandGraph shows the use of the helper classes. Both helper classes contain methods to find the shortest path between 2 nodes. ShortestPathSearch is more efficient, but it does not allow cycles, beginning and ending on the same node. The findShortestCycle method in PathFinder, finds all distinct cycles and returns the cycle with the smallest distance. implements the 


