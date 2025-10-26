package src;

import com.google.gson.*;
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create(); 
            Reader reader = new FileReader("assign_3_input.json");
            JsonObject json = gson.fromJson(reader, JsonObject.class);
            reader.close();

            JsonArray graphsArray = json.getAsJsonArray("graphs");
            JsonArray resultsArray = new JsonArray(); 

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

                // prim's algorithm
                PrimAlgorithm.Result primResultData = PrimAlgorithm.findMST(graph);
                System.out.println("\n Prim's Algorithm Result:");
                System.out.println("MST edges: " + primResultData.mstEdges);
                System.out.println("Total cost: " + primResultData.totalCost);
                System.out.println("Operations count: " + primResultData.operationsCount);
                System.out.printf("Execution time (ms): %.4f\n", primResultData.executionTimeMs);

                // Kruskal's algorithm
                KruskalAlgorithm.Result kruskalResultData = KruskalAlgorithm.findMST(graph);
                System.out.println("\n Kruskal's Algorithm Result:");
                System.out.println("MST edges: " + kruskalResultData.mstEdges);
                System.out.println("Total cost: " + kruskalResultData.totalCost);
                System.out.println("Operations count: " + kruskalResultData.operationsCount);
                System.out.printf("Execution time (ms): %.4f\n", kruskalResultData.executionTimeMs);

                // forming JSON result for this graph
                JsonObject graphResult = new JsonObject();
                graphResult.addProperty("graph_id", id);

                JsonObject inputStats = new JsonObject();
                inputStats.addProperty("vertices", graph.getVerticesCount());
                inputStats.addProperty("edges", graph.getEdgesCount());
                graphResult.add("input_stats", inputStats);

                // Prim JSON
                JsonObject primJson = new JsonObject();
                JsonArray primEdgesArray = new JsonArray();
                for (Edge edge : primResultData.mstEdges) {
                    JsonObject edgeJson = new JsonObject();
                    edgeJson.addProperty("from", edge.from);
                    edgeJson.addProperty("to", edge.to);
                    edgeJson.addProperty("weight", edge.weight);
                    primEdgesArray.add(edgeJson);
                }
                primJson.add("mst_edges", primEdgesArray);
                primJson.addProperty("total_cost", primResultData.totalCost);
                primJson.addProperty("operations_count", primResultData.operationsCount);
                primJson.addProperty("execution_time_ms", primResultData.executionTimeMs);
                graphResult.add("prim", primJson);

                // Kruskal JSON
                JsonObject kruskalJson = new JsonObject();
                JsonArray kruskalEdgesArray = new JsonArray();
                for (Edge edge : kruskalResultData.mstEdges) {
                    JsonObject edgeJson = new JsonObject();
                    edgeJson.addProperty("from", edge.from);
                    edgeJson.addProperty("to", edge.to);
                    edgeJson.addProperty("weight", edge.weight);
                    kruskalEdgesArray.add(edgeJson);
                }
                kruskalJson.add("mst_edges", kruskalEdgesArray);
                kruskalJson.addProperty("total_cost", kruskalResultData.totalCost);
                kruskalJson.addProperty("operations_count", kruskalResultData.operationsCount);
                kruskalJson.addProperty("execution_time_ms", kruskalResultData.executionTimeMs);
                graphResult.add("kruskal", kruskalJson);

                resultsArray.add(graphResult);
            }

            // Writing final results to output JSON file
            JsonObject finalOutput = new JsonObject();
            finalOutput.add("results", resultsArray);

            Writer writer = new FileWriter("assign_3_output.json");
            gson.toJson(finalOutput, writer);
            writer.close();

            System.out.println("\nResults have been saved to assign_3_output.json successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


