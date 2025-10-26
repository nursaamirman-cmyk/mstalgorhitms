package src;

import java.util.*;

public class PrimAlgorithm {

    public static class Result {
        public List<Edge> mstEdges;
        public int totalCost;
        public int operationsCount;
        public double executionTimeMs;

        public Result(List<Edge> mstEdges, int totalCost, int operationsCount, double executionTimeMs) {
            this.mstEdges = mstEdges;
            this.totalCost = totalCost;
            this.operationsCount = operationsCount;
            this.executionTimeMs = executionTimeMs;
        }
    }

    public static Result findMST(Graph graph) {
        long startTime = System.nanoTime();

        List<Edge> mst = new ArrayList<>();
        int totalCost = 0;
        int operations = 0;

        Set<String> visited = new HashSet<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        // Начинаем с первой вершины
        String start = graph.getNodes().get(0);
        visited.add(start);

        // Добавляем все рёбра, исходящие из неё
        for (Edge e : graph.getEdges()) {
            if (e.from.equals(start) || e.to.equals(start)) {
                pq.add(e);
            }
        }

        // Пока не собрали все вершины
        while (!pq.isEmpty() && visited.size() < graph.getVerticesCount()) {
            Edge minEdge = pq.poll();
            operations++;

            String nextNode = null;

            if (visited.contains(minEdge.from) && !visited.contains(minEdge.to)) {
                nextNode = minEdge.to;
            } else if (visited.contains(minEdge.to) && !visited.contains(minEdge.from)) {
                nextNode = minEdge.from;
            }

            if (nextNode != null) {
                visited.add(nextNode);
                mst.add(minEdge);
                totalCost += minEdge.weight;

                // Добавляем все новые рёбра, ведущие из nextNode
                for (Edge e : graph.getEdges()) {
                    if ((e.from.equals(nextNode) && !visited.contains(e.to)) ||
                        (e.to.equals(nextNode) && !visited.contains(e.from))) {
                        pq.add(e);
                    }
                }
            }
        }

        long endTime = System.nanoTime();
        double executionTimeMs = (endTime - startTime) / 1_000_000.0;

        return new Result(mst, totalCost, operations, executionTimeMs);
    }
}
