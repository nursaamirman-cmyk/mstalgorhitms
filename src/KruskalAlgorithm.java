package src;

import java.util.*;

public class KruskalAlgorithm {

    public static class Result {
        List<Edge> mstEdges;
        int totalCost;
        int operationsCount;
        double executionTimeMs;

        public Result(List<Edge> mstEdges, int totalCost, int operationsCount, double executionTimeMs) {
            this.mstEdges = mstEdges;
            this.totalCost = totalCost;
            this.operationsCount = operationsCount;
            this.executionTimeMs = executionTimeMs;
        }
    }

    // Вспомогательный класс для Union–Find (Disjoint Set Union)
    static class DisjointSet {
        private final Map<String, String> parent = new HashMap<>();

        public void makeSet(List<String> nodes) {
            for (String node : nodes) {
                parent.put(node, node);
            }
        }

        public String find(String node) {
            if (!parent.get(node).equals(node)) {
                parent.put(node, find(parent.get(node))); // path compression
            }
            return parent.get(node);
        }

        public void union(String node1, String node2) {
            String root1 = find(node1);
            String root2 = find(node2);
            if (!root1.equals(root2)) {
                parent.put(root2, root1);
            }
        }
    }

    // Основной метод
    public static Result findMST(Graph graph) {
        long startTime = System.nanoTime();

        List<Edge> mst = new ArrayList<>();
        List<Edge> edges = new ArrayList<>(graph.getEdges());
        Collections.sort(edges); // сортируем по весу

        DisjointSet ds = new DisjointSet();
        ds.makeSet(graph.getNodes());

        int totalCost = 0;
        int operationsCount = 0;

        for (Edge edge : edges) {
            operationsCount++;
            String root1 = ds.find(edge.from);
            String root2 = ds.find(edge.to);

            if (!root1.equals(root2)) {
                mst.add(edge);
                totalCost += edge.weight;
                ds.union(edge.from, edge.to);
            }

            // Остановка, если MST уже готов
            if (mst.size() == graph.getVerticesCount() - 1)
                break;
        }

        long endTime = System.nanoTime();
        double execTimeMs = (endTime - startTime) / 1_000_000.0;

        return new Result(mst, totalCost, operationsCount, execTimeMs);
    }
}

