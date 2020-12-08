import java.io.File;
import java.util.Scanner;
import java.util.Arrays;

public class Graph {
    int vertexCt;  // Number of vertices in the graph.
    GraphNode[] G;  // Adjacency array for graph. each index represents a city, containing an linked list of ajacent city ids
    String graphName;  //The file from which the graph was created.

    public Graph() {
        this.vertexCt = 0;
        this.graphName = "";
      }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append( "The Graph " + graphName + " \n" );

        for (int i = 0; i < vertexCt; i++) {
            sb.append( G[i].toString() );
        }
        return sb.toString();
    }

    public boolean makeGraph(String filename) {
        try {
            graphName = filename;
            Scanner reader = new Scanner( new File( filename ) );
            System.out.println( "\n" + filename );
            vertexCt = Integer.parseInt(reader.nextLine());
            G = new GraphNode[vertexCt];
            for (int i = 0; i < vertexCt; i++) {
                G[i] = new GraphNode( i );
            }
            for (int i = 0; i < vertexCt; i++) {
                String[] values = reader.nextLine().split(" ");
                int v1 = Integer.parseInt(values[0]);
                G[v1].nodeName = values[1];
                for (int v = 2; v <values.length; v++){
                    int v2=Integer.parseInt(values[v]);
                    G[v1].addEdge(v1,v2);
                }
             }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void main(String[] args) {
        String[] files  = {"Tester.txt", "Tester2.txt","WesternUS.txt","BritishIsles.txt","NortheastUS.txt", "CentralEurope.txt", "IberianPeninsula.txt",
                "SouthernNigeria.txt", "SouthernSouthKorea.txt", "NortheastUS2.txt", "SouthernUS.txt"};

        Graph graph1;
        for (String file:files) {
            graph1 = new Graph();
            graph1.makeGraph(file);
            System.out.println(graph1.toString());
        }
    }
    public int getGreedyUpperBound(SupplySolution partialSoln) {
        boolean loopAgain = true;
        while(loopAgain) {
            int max = 0;
            GraphNode maxSucc = G[0]; // initialize so it wont have error
            for (GraphNode city : G) {
                int num = wouldCover(city, partialSoln);
                if (num > max) {
                    max = num;
                    maxSucc = city;
                }
            }
            addSupplies(partialSoln, maxSucc);
            loopAgain = false;
            for (boolean val: partialSoln.covered) {
                if (!val) {
                    loopAgain = true;
                    break;
                }
            }
        }
        return partialSoln.supplyCt;
    }
    public static int wouldCover(GraphNode newSupplyNode, SupplySolution partialSoln) {
        int count = 1; // 1 because it should always atleast supply itself
        // grab the node indexes from the linked list, and check which ones would be newly supplied
        for (int idx: newSupplyNode.succ.grabListIndexes()) { // loop through array returned from function
            // array contains the cities or indexes that are adjacent
            // check if that index is covered, if not count++;
            if (!partialSoln.covered[idx]) {
                count++; // checks if that node is already supplied, if not supply it and count++;
            }
        }
        return count;
    }
    public static void addSupplies(SupplySolution partialSoln, GraphNode node) {
        partialSoln.supplies[node.nodeID] = true; // update supplies array
        partialSoln.supplyCt++;

        for(int num: node.succ.grabListIndexes()) { // update covered array
            if (!partialSoln.covered[num]) {
                partialSoln.covered[num] = true;
                partialSoln.needToCover--;
            }
        }
        if (!partialSoln.covered[node.nodeID]) {
            partialSoln.covered[node.nodeID] = true;
            partialSoln.needToCover--;
        }
    }
}