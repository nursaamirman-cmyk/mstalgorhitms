package src;

import com.google.gson.*;
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            Gson gson = new Gson();
            Reader reader = new FileReader("assign_3_input.json");
            JsonObject json = gson.fromJson(reader, JsonObject.class);
            reader.close();

            JsonArray graphsArray = json.getAsJsonArray("graphs");

            for (JsonElement graphElement : graphsArray) {
                JsonObject graphObj = graphElement.getAsJsonObject();
                int id = graphObj.get("id").getAsInt();

                List<String> nodes = new ArrayList<>();
                for (JsonElement n : graphObj.getAsJsonArray("nodes")) {
                    nodes.add(n.getAsString());
                }

                List<Edge> edges = new ArrayList<>();
                for (JsonElement e : graphObj.getAsJsonArray("edges")) {
                    JsonObject edgeObj = e.getAsJsonObject();
                    String from = edgeObj.get("from").getAsString();
                    String to = edgeObj.get("to").getAsString();
                    int weight = edgeObj.get("weight").getAsInt();
                    edges.add(new Edge(from, to, weight));
                }

                Graph graph = new Graph(nodes, edges);

                System.out.println("\n==============================");
                System.out.println("Graph ID: " + id);
                System.out.println("Vertices: " + graph.getVerticesCount());
                System.out.println("Edges: " + graph.getEdgesCount());
                System.out.println("Edges list: " + edges);

                // 🌿 ВЫЗОВ АЛГОРИТМА ПРИМА
                PrimAlgorithm.Result result = PrimAlgorithm.findMST(graph);

                System.out.println("\n Prim’s Algorithm Result:");
                System.out.println("MST edges: " + result.mstEdges);
                System.out.println("Total cost: " + result.totalCost);
                System.out.println("Operations count: " + result.operationsCount);
                System.out.printf("Execution time (ms): %.4f\n", result.executionTimeMs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

