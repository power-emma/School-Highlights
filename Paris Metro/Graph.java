import java.util.ArrayList;

public class Graph {
    
    // The array to store edges in
    private ArrayList<Edge> edges = new ArrayList<>();
    // Array to store vertices in
    private ArrayList<Vertex<Station>> vertices = new ArrayList<>();

    /**
     * Returns the vertex with id i
     * 
     * @param i The index to find
     * @return  the vertex with id i
     */
    public Vertex<Station> getVertexByIndex(int i) {
        return vertices.get(i);
    }// getVertexByIndex

    /**
     * Adds the vertex v to the graph
     * 
     * @param v The vertex to be added to the graph
     */
    public void addVertex(Vertex<Station> v) {
        vertices.add(v);
    }// addVertex

    /**
     * Returns the edge with id i
     * 
     * @param   i
     * @return  the edge with id i
     */
    public Edge getEdgeByIndex(int i) {
        return edges.get(i);
    }// getEdgeByIndex

    /**
     * Adds the edge e to the graph
     * 
     * @param e The edge to be added
     */
    public void addEdge(Edge e) {
        // Edges are one directional
        e.getOrigin().addEdge(e);
        edges.add(e);
    }// addIndex

    /**
     * Gets the amount of edges in the graph
     * 
     * @return  The amount of edges in the graph
     */
    public int getEdgeSize() {
        return edges.size();
    }//

    /**
     * Returns the list of vertices
     * 
     * @return  The list of vertices
     */
    public ArrayList<Vertex<Station>> getVertices() {
        return vertices;
    }//getVertices
}// Graph