public class EdgeInfo {

    public EdgeInfo(int from, int to, int capacity,int cost, int flow){
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        this.cost = cost;
        this.flow = flow;
        this.marked = false;

    }
    public String toString(){
        return "Edge " + from + "->" + to + " ("+ flow + "/" + capacity + ", " + cost + ") " ;
    }

    int from;        // source of edge
    int to;          // destination of edge
    int capacity;    // capacity of edge
    int cost;        // cost of edge
    int flow;        // flow through edge
    boolean marked;  // if node has been marked


}
