public class Edge {
        private Vertex<Station> origin, destination;
        private int weight;

        Edge(Vertex<Station> origin, Vertex<Station> destination, int weight) {
            this.origin = origin;
            this.destination = destination;
            this.weight = weight;
        }

        public Vertex<Station> getOrigin () {
            return origin;
        }

        public Vertex<Station> getDestination () {
            return destination;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }
    }