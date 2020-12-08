import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Flow;

public class MaxFlow {
    public static void main(String[] args) {
        String[] files = {
                "group0.txt",
                "group1.txt",
                "group4.txt",
                "group5.txt",
                "group6.txt",
                "group7.txt",
                "group8.txt",
                "bellman0.txt",
        };
        for (String file: files) {
            System.out.println(file);
            FlowGraph graph = new FlowGraph();
            graph.makeGraph(file);
            int maxFlow = FordFulkerson(graph);
            System.out.println(file + " Max Flow SPACE " + graph.maxFlowIntoSink + " assigned " + maxFlow);
            graph.printEdges();
            System.out.println("TotalCost = " + graph.getCost() + "\n");
        }
    }

    public static boolean DijkstraNeg(FlowGraph graph) {
        GraphNode source = graph.source;
        GraphNode sink = graph.sink;

        MyQueue<GraphNode> queue = new MyQueue<GraphNode>();
        for (GraphNode node: graph.G) {
            node.distance = GraphNode.INF;
            node.prevNode = -1;
        }
        source.distance = 0;
        queue.enqueue(source);

        while(!queue.isEmpty()) {
            GraphNode node = queue.dequeue();
            for (EdgeInfo edgeInfo: node.succ) {
                GraphNode other = graph.G[edgeInfo.to];

                if (node.getResidualFlow(other.nodeID) <= 0) { continue; }

                if (node.distance + edgeInfo.cost < other.distance) {
                    other.distance = node.distance + edgeInfo.cost;
                    other.prevNode = node.nodeID;
                    queue.enqueue(other);
                }
            }
        }
        return sink.prevNode >= 0;
    }

    public static int FordFulkerson(FlowGraph graph) {
        GraphNode source = graph.source;
        GraphNode sink = graph.sink;
        MyStack<Integer> stack = new MyStack<>();
        int value = 0;

        while (DijkstraNeg(graph)) {
            int availFlow = GraphNode.INF;
            for (GraphNode curr = sink; curr != source; curr = graph.G[curr.prevNode]) {
                availFlow = Math.min(availFlow, graph.G[curr.prevNode].getResidualFlow(curr.nodeID));
            }

            // augment flow
            for (GraphNode curr = sink; curr != source; curr = graph.G[curr.prevNode]) {
                graph.G[curr.prevNode].addFlow(curr.nodeID, availFlow); // forward edge
                graph.G[curr.nodeID].addFlow(curr.prevNode, -availFlow); // backward edge
                stack.add(curr.nodeID);
            }
            stack.add(0); // add source to stack for printing
            System.out.println("DijkstrasNeg found flow " + availFlow + ": " + stack);
            stack.clear();
            value += availFlow;
        }
        return value;
    }
}
