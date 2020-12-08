
public class disaster {
    public static void main(String[] args) {
        String[] files  = {"Tester.txt", "Tester2.txt","WesternUS.txt","BritishIsles.txt","NortheastUS.txt", "CentralEurope.txt", "IberianPeninsula.txt",
                "SouthernNigeria.txt", "SouthernSouthKorea.txt", "NortheastUS2.txt", "SouthernUS.txt"};

        Graph graph;
        for (String file: files) {
            graph = new Graph();
            graph.makeGraph(file);
            SupplySolution partialSoln = new SupplySolution(graph.vertexCt);

            // determine the upper bound of allowed cities using a greedy solution
            SupplySolution greedy = new SupplySolution(partialSoln);
            int allowed = graph.getGreedyUpperBound(greedy);
            System.out.println(getSupply(0, allowed, partialSoln, graph));
        }
    }

    public static SupplySolution addSupply(SupplySolution partialSoln, int nodeId, Graph graph) {
        SupplySolution newSoln = new SupplySolution(partialSoln);
        newSoln.supplies[nodeId] = true;
        // covered
        //               :graph node: array of successors:
        for (int num : graph.G[nodeId].succ.grabListIndexes()) {
            if (!newSoln.covered[num]) {
                newSoln.covered[num] = true;
                newSoln.needToCover--;
            }
        }
        if (!newSoln.covered[nodeId]) {
            newSoln.covered[nodeId] = true;
            newSoln.needToCover--;
        }
        return newSoln;
    }

    // recursive function
    public static SupplySolution getSupply(int nodeId, int allowed, SupplySolution partialSoln, Graph graph) {
        if (allowed == 0) { return partialSoln; }
        if (nodeId >= partialSoln.vertexCt) { return partialSoln; }
        SupplySolution useIt = getSupply(nodeId + 1, allowed - 1, addSupply(partialSoln, nodeId, graph), graph);// update supply solution, both covered and supplied cites)
        SupplySolution dont = getSupply(nodeId + 1, allowed, partialSoln, graph);
        return betterOf(useIt, dont);
    }

    public static SupplySolution betterOf(SupplySolution partialSoln1, SupplySolution partialSoln2) {
        // if both are full solutions
        if ((partialSoln1.needToCover == 0) && (partialSoln2.needToCover == 0)) {
            if (partialSoln1.supplyCt < partialSoln2.supplyCt) {
                return partialSoln1;
            } else {
                return partialSoln2;
            }
        }
        // if neither is a full solution return worst case, because neither count
        if ((partialSoln1.needToCover != 0) && partialSoln2.needToCover != 0) {
            return partialSoln1;
        }
        // if one is zero
        if (partialSoln1.needToCover == 0) { return partialSoln1; }
        return partialSoln2;
    }
}
