import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/*
 * Date: November 27, 2023
 * By: Emma Power
 * 
 * Performs all required tasks within the CSI 2110 Paris Metro Assignment
 */
public class ParisMetro {

    // What -1 should be set to when computing shortest path
    public static final int WALKING_TIME = 90;

    /**
     * Returns an ArrayList of vertices that are all a part of the same Subway/Metro line
     * Done by performing a Breadth First Search starting at the given vertex
     * 
     * @param start A vertex on the desired line
     * @return      An ArrayList containing all vertices on the same line as start
     */
    public static ArrayList<Vertex<Station>> findAllConnected(Vertex<Station> start) {

        // Initialize queue and list of stations
        Queue<Vertex<Station>> toVisit = new LinkedList<Vertex<Station>>();
        ArrayList<Vertex<Station>> stationList = new ArrayList<Vertex<Station>>();

        // Start is part of the metro line to begin with
        toVisit.add(start);
        
        // Loop until no more edges are to be found
        while (!toVisit.isEmpty()) {

            // Pop from queue
            Vertex<Station> v = toVisit.remove();

            //Dont interact with it if it has been visited
            if (v.getLabel() == Label.VISITED) {
                continue;
            }

            // Iterate through the edges in this vertex
            Iterator<Edge> it = v.getEdgeList().iterator();

            while (it.hasNext()) {
                // Get edge
                Edge e = it.next();

                // Ignore walking paths
                if (e.getWeight() == -1) {
                    continue;
                }

                // Add edge destination to the queue if it has not been visited
                Vertex<Station> tempV = e.getDestination();
                if (tempV.getLabel() == Label.UNEXPLORED) {
                    toVisit.add(tempV);
                }
            }
            
            // Now this vertex has been visited
            v.setLabel(Label.VISITED);

            // Thus this vertex is part of the line as it was accessed by a non negative edge
            stationList.add(v);

        };

        return stationList;
    }// findAllConnected

    /**
     * Reads the given file, and parses it based on the description in the handout
     * 
     * @param fileName  The file to be read
     * @return          The graph that is contained within the file
     */
    public static Graph readMetro(String fileName) throws Exception {
        Graph g = new Graph();

        // Setup file reading
        File file = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));
 
        // Declaring a string variable
        String next;
        // Pretty much ignore the information in the first line (we can get it by proxy anyways)
        next = br.readLine();

        // Parse Vertices (until $ is hit) 
        while (!(next = br.readLine()).equals("$")) {
            // Get array of items in this line
            String[] split = next.split(" ");

            // Make a new station and vertex with this line
            Station s = new Station(split[1], Integer.parseInt(split[0]));
            Vertex<Station> v = new Vertex<Station>(s, Integer.parseInt(split[0]));
            g.addVertex(v);
        }


        // Parse Edges
        while((next = br.readLine()) != null) {
            // Get array of items in this line
            String[] split = next.split(" ");

            // Set values based on split string
            int indexOrigin = Integer.parseInt(split[0]);
            int indexDestination = Integer.parseInt(split[1]);
            int length = Integer.parseInt(split[2]);

            // Make a new edge based on this information
            Vertex<Station> origin = g.getVertexByIndex(indexOrigin);
            Vertex<Station> destination = g.getVertexByIndex(indexDestination);
            Edge e = new Edge(origin, destination, length);

            g.addEdge(e);
        }

        // Stuff like this makes me glad im not doing this in C (malloc() flashbacks)
        br.close();

        return g;
    }// readMetro

    /**
     * Returns an ArrayList that contains, in order, the vertices that comprise the shortest route from start to end
     * This is implemented using Dijkstra's algorithm
     * 
     * @param g     The graph in which all the vertexes are stored
     * @param start The vertex to start with
     * @param end   The vertex that is the desired endpoint
     * @return      An ArrayList that contains, in order, the vertexes that comprise the shortest route
     */
    public static ArrayList<Vertex<Station>> dijkstra(Graph g, Vertex<Station> start, Vertex<Station> end) {

        // Makes new PQ with a comparator based on the edge weight
        PriorityQueue<Edge> heap = new PriorityQueue<Edge>(g.getEdgeSize(), (a,b) -> a.getWeight() - b.getWeight());
        // The graph which represents the cloud
        Graph cloud = new Graph();

        // Add the start vertex to the cloud (weight is 0 by definition)
        Vertex<Station> s = start;
        s.setBestDist(0);
        s.setPrevious(null);
        cloud.addVertex(s);
        ArrayList<Edge> SEL = s.getEdgeList();

        // Add the initial edges from the start vertex
        for (int i = 0; i < start.getEdgeList().size(); i++) {
            heap.add(SEL.get(i));
        }

        // Runs a max of e times
        while (!heap.isEmpty()) {

            // Smallest edge leads to vertex that is to be added to the cloud
            Edge e = heap.poll();
            Vertex<Station> v = e.getDestination();
            v.setBestDist(e.getWeight());
            v.setPrevious(e.getOrigin());
            cloud.addVertex(v);

            // Iterator for this vertex's edge list
            Iterator it = v.getEdgeList().iterator();
            it = v.getEdgeList().iterator();

            // Runs a max of v(e) times
            // Iterates over each edge the new vertex has
            while (it.hasNext()) {
                
                Edge vEdge = (Edge) it.next();

                // Make copy of edge with new distance
                Edge adjEdge = new Edge(vEdge.getOrigin(), vEdge.getDestination(), 0);

                // Prevents Infinite Loop being caused by edges that are already in the cloud
                if (cloud.getVertices().contains(vEdge.getDestination())) {
                    continue;
                }

                // Set adjusted weights for the edges
                if(vEdge.getWeight() == -1) {
                    adjEdge.setWeight(WALKING_TIME + v.getBestDist());
                } else {
                    adjEdge.setWeight(vEdge.getWeight() + v.getBestDist());
                }
                
                // Iterates over each item in the heap to look for one with the same destination
                Iterator<Edge> hit = heap.iterator();

                // Variables to do manipulation after the loop
                boolean found = false;
                Edge toRemove = null;

                // Runs a max of e times
                // Tries to find an edge to replace, but a PQ can only be traversed by an iterator
                while (hit.hasNext()) {
                    Edge heapEdge = hit.next();

                    // This only runs once because we always only keep the smaller weight
                    // Runs if the destination of both edges are the same
                    if (heapEdge.getDestination().getIndex() == adjEdge.getDestination().getIndex()) {
                        // If the new edge is smaller than delete the old one
                        if(heapEdge.getWeight() > adjEdge.getWeight()) {
                            found = true;
                            toRemove = heapEdge;
                            break;

                        // If the new edge is smaller add it and then instantly delete it
                        } else if(heapEdge.getWeight() <= adjEdge.getWeight()) {
                            //System.out.println("WHAT");
                            found = true;
                            toRemove = adjEdge;
                            break;
                        }
                    }
                }
                // If this is a new edge then add it to the heap
                if(!found) {
                    heap.add(adjEdge);

                // If this is an overwrite then add the new edge and either delete it instantly or delete the old one depending on what was bigger
                } else {
                    heap.add(adjEdge);
                    heap.remove(toRemove);
                }
            }
        }

        // Find the end point you are looking
        Vertex<Station> endCloud = null;
        for(Vertex<Station> v : cloud.getVertices()) {
            // There will only be one instance of each index in the cloud
            if(v.getIndex() == end.getIndex()) {
                endCloud = v;
                break;
            }
        }

        // The final array that holds the shortest path
        ArrayList<Vertex<Station>> path = new ArrayList<Vertex<Station>>();

        // Lightning Strike
        // "Recursively" call getPrevious() from the end to the beginning
        Vertex<Station> prev = endCloud.getPrevious();
        path.add(endCloud);

        while (prev != null) {
            //Add to front because this traversal is backwards
            path.add(0, prev);
            prev = prev.getPrevious();
        }

        return path;
    }// dijkstra

    /**
     * Removes all edges from each vertex in a, effectively deleting it from the view of dijkstra's algorithm
     * 
     * @param a     The list of vertices to remove all edges of
     */
    public static void deleteList(ArrayList<Vertex<Station>> a) {
        // Loop through array clearing every edge from the vertex
        for(Vertex<Station> v : a) {
            v.getEdgeList().clear();
        }
    }// deleteList

    /**
     * Performs the operations specified in the handout depending on how many paramaters are specified
     * 1 Paramater (2, i) - Lists all stations on the same line as vertex N1
     * 2 Paramaters (2, ii) - Finds the shortest path between N1 and N2
     * 3 Paramaters (2, iii) - Finds the shortest path between N1 and N2, given that the line that vertex
     *                         N3 is on is non-functional
     * 
     * @param args  args[0] (N1) is the start vertex
     *              args[1] (N2) is the end vertex
     *              args[2] (N3) is the vertex of a line to delete
     */
    public static void main(String args[]) {

        Graph graph = new Graph();
        // Read the graph from the file
        try {
            graph = readMetro("metro.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Do Question 2 III
        if(args.length == 3) {
            int n1 = Integer.parseInt(args[0]);
            int n2 = Integer.parseInt(args[1]);
            int n3 = Integer.parseInt(args[2]);
            // Reuse Code from part 1 and then delete every edge on that vertex
            // This works because every vertex will be a dead end and thus can not be traversed
            deleteList(findAllConnected(graph.getVertexByIndex(n3)));
            // Then use the same codeas part 2 with the newly edited graph
            ArrayList<Vertex<Station>> path = dijkstra(graph, graph.getVertexByIndex(n1), graph.getVertexByIndex(n2));

            for(Vertex<Station> v : path) {
                System.out.print(v.getIndex() + " ");
            }

        // Do question 2 II
        } else if(args.length == 2) {
            int n1 = Integer.parseInt(args[0]);
            int n2 = Integer.parseInt(args[1]);

            ArrayList<Vertex<Station>> path = dijkstra(graph, graph.getVertexByIndex(n1), graph.getVertexByIndex(n2));

            for(Vertex<Station> v : path) {
                System.out.print(v.getIndex() + " ");
            }

        // Do question 2 I
        } else if(args.length == 1) {
            int n1 = Integer.parseInt(args[0]);

            ArrayList<Vertex<Station>> line = findAllConnected(graph.getVertexByIndex(n1));

            for(Vertex<Station> v : line) {
                System.out.print(v.getIndex() + " ");
            }

        } else {
            System.out.println("Specify 1, 2 or 3 integers");
        }
    
    }// main

}// ParisMetro
