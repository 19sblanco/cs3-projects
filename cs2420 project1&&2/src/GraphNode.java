public class GraphNode {

    public GraphNode( ){
        this.nodeID = 0;
        this.succ = new MyLinkedList<Integer>(); // linked list of cites that it "touches"
        this.nodeName="";
        this.succCt=0;
     }

    public GraphNode(int nodeID){
        this.nodeID = nodeID;
        this.succ = new MyLinkedList<Integer>();
        this.nodeName="noName";
        this.succCt=0;
      }

    public String toString() {
        return this.nodeName;
    }

    public void addEdge(int v1, int v2){
        succ.addFirst( v2 );
        succCt++;
    }


    public int nodeID;
    public int succCt;
    public String nodeName;
    public MyLinkedList<Integer> succ;
   // boolean holdsSupply;
}
