public class Station {
    // Instance Variables
    String name;
    int num;

    /**
     * Station Constructor
     * 
     * @param name  The name of the station
     * @param num   The index of the station
     */
    Station(String name, int num) {
        this.name = name;
        this.num = num;
    }// constructor

    /**
     * Returns a string with the station info
     * 
     * @return The the string with station info
     */
    public String toString() {
        return num + " " + name;
    }// toString
}