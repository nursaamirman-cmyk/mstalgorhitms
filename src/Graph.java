package src;
import java.util.*;

public class Graph {
    List<String> nodes;
    List<Edge> edges;

    public Graph(List<String> nodes, List<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    public int getVerticesCount() {
        return nodes.size();
    }

    public int getEdgesCount() {
        return edges.size();
    }

    public List<String> getNodes() {
        return nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }
}

