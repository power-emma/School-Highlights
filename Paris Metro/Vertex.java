import java.util.ArrayList;

/**
 *  Contains all information required for an vertex in a graph
 *  Typically contains a station object as element
 */
public class Vertex<E> {
        private ArrayList<Edge> edges = new ArrayList<>();
        private E elem;
        private Label label;
        private Vertex<E> previousVertex;
        private int bestDist;
        private int index;

        /**
         * Vertex Constructor
         * 
         * @param elem  Element to be contained in the vertex
         * @param index Index of the vertex
         */
        Vertex(E elem, int index) {
            this.elem = elem;
            label = Label.UNEXPLORED;
            bestDist = -1;
            this.index = index;
        }

        /**
         * Sets the index of the vertex to i
         * 
         * @param i index of the vertex
         */
        public void setIndex(int i) {
            index = i;
        }

        /**
         * Returns index of the vertex
         * 
         * @return  Index of the vertex
         */
        public int getIndex() {
            return index;
        }

        /**
         * Returns the list of edges this vertex has
         * 
         * @return List of edges
         */
        public ArrayList<Edge> getEdgeList() {
            return edges;
        }

        /**
         * Adds edge to the edge list
         * 
         * @param e Edge to add
         */
        public void addEdge(Edge e) {
            edges.add(e);
        }

        /**
         * Sets the label to be used for searching
         * 
         * @param Label Label to give the vertex
         */
        public void setLabel(Label label) {
            this.label = label;
        }

        /**
         * Returns the search label
         * 
         * @return The search label
         */
        public Label getLabel() {
            return label;
        }

        /**
         * Returns the element of the vertex
         * 
         * @return The element of the vertex
         */
        public E getElement() {
            return elem;
        }

        /**
         * Sets a previous vertex
         * Used for dijkstra's algorithm to allow an endpoint to find the fastest way to the start
         * 
         * @param v The vertex to set as the previous
         */
        public void setPrevious(Vertex<E> v) {
            previousVertex = v;
        }

        /**
         * Gets the previous vertex
         * 
         * @return The previous vertex
         */
        public Vertex<E> getPrevious() {
            return previousVertex;
        }

        /**
         * Returns the best distance of this vertex (used for dijkstra)
         * 
         * @return The best distance
         */
        public int getBestDist() {
            return bestDist;
        }

        /**
         * Sets the best distance of this vertex from a specified vertex
         * 
         * @param i The distance
         */
        public void setBestDist(int i) {
            bestDist = i;
        }


        public String toString() {
            return elem.toString();
        }

    }