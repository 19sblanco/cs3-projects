import java.util.Arrays;
public class SupplySolution {

    public SupplySolution(int vertexCt){
        this.vertexCt = vertexCt;
        supplies = new boolean[vertexCt];
        covered = new boolean[vertexCt];
        supplyCt=0;
        needToCover=vertexCt;
      }

    public SupplySolution(SupplySolution ss){
        this.supplies = ss.supplies.clone();
        this.covered = ss.covered.clone();
        this.vertexCt = ss.vertexCt;
        supplyCt=ss.supplyCt;
        needToCover=ss.needToCover;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("SupplyCt " + getSupplyCities() + " Need to cover " + needToCover + "\n SUPPLIES:");
        for (int i=0; i < vertexCt; i++)
            if (supplies[i]) sb.append("1");
            else sb.append("0");
        sb.append("\n COVERED :");
        for (int i=0; i < vertexCt; i++)
            if (covered[i]) sb.append("1");
            else sb.append("0");
        sb.append("\n ");
        return sb.toString();
    }
    public int getSupplyCities() {
        int count = 0;
        for (boolean val: supplies) {
            if (val) { count++; }
        }
        return count;
    }


    int supplyCt;  //Number of nodes which are supply nodes
    int vertexCt;  //Total number of nodes in graph
    int needToCover;  // Nodes which are not covered.
    boolean supplies[];  //supplies[i] is true if node i holds supplies
    boolean covered[];   // covered[i] is true if node i is covered
}
