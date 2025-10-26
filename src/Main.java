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

            // Create CSV file for summary
            PrintWriter csvWriter = new PrintWriter(new FileWriter("assign_3_summary.csv"));
            csvWriter.println("Graph ID,Algorithm,Total Cost,Operations Count,Execution Time (ms)");

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

                // Prim's Algorithm
                PrimAlgorithm.Result primResult = PrimAlgorithm.findMST(graph);
                System.out.println("\n Prim's Algorithm Result:");
                System.out.println("MST edges: " + primResult.mstEdges);
                System.out.println("Total cost: " + primResult.totalCost);
                System.out.println("Operations count: " + primResult.operationsCount);
                System.out.printf("Execution time (ms): %.4f\n", primResult.executionTimeMs);

                // Kruskal's Algorithm
                KruskalAlgorithm.Result kruskalResult = KruskalAlgorithm.findMST(graph);
                System.out.println("\n Kruskal's Algorithm Result:");
                System.out.println("MST edges: " + kruskalResult.mstEdges);
                System.out.println("Total cost: " + kruskalResult.totalCost);
                System.out.println("Operations count: " + kruskalResult.operationsCount);
                System.out.printf("Execution time (ms): %.4f\n", kruskalResult.executionTimeMs);

                // saving results to CSV
                csvWriter.printf("%d,Prim,%d,%d,%.4f\n",
                        id, primResult.totalCost, primResult.operationsCount, primResult.executionTimeMs);
                csvWriter.printf("%d,Kruskal,%d,%d,%.4f\n",
                        id, kruskalResult.totalCost, kruskalResult.operationsCount, kruskalResult.executionTimeMs);

                // saving results to JSON
                JsonObject graphResult = new JsonObject();
                graphResult.addProperty("graph_id", id);

                JsonObject inputStats = new JsonObject();
                inputStats.addProperty("vertices", graph.getVerticesCount());
                inputStats.addProperty("edges", graph.getEdgesCount());
                graphResult.add("input_stats", inputStats);

                // Prim
                JsonObject primJson = new JsonObject();
                JsonArray primEdges = new JsonArray();
                for (Edge edge : primResult.mstEdges) {
                    JsonObject edgeJson = new JsonObject();
                    edgeJson.addProperty("from", edge.from);
                    edgeJson.addProperty("to", edge.to);
                    edgeJson.addProperty("weight", edge.weight);
                    primEdges.add(edgeJson);
                }
                primJson.add("mst_edges", primEdges);
                primJson.addProperty("total_cost", primResult.totalCost);
                primJson.addProperty("operations_count", primResult.operationsCount);
                primJson.addProperty("execution_time_ms", primResult.executionTimeMs);

                // Kruskal
                JsonObject kruskalJson = new JsonObject();
                JsonArray kruskalEdges = new JsonArray();
                for (Edge edge : kruskalResult.mstEdges) {
                    JsonObject edgeJson = new JsonObject();
                    edgeJson.addProperty("from", edge.from);
                    edgeJson.addProperty("to", edge.to);
                    edgeJson.addProperty("weight", edge.weight);
                    kruskalEdges.add(edgeJson);
                }
                kruskalJson.add("mst_edges", kruskalEdges);
                kruskalJson.addProperty("total_cost", kruskalResult.totalCost);
                kruskalJson.addProperty("operations_count", kruskalResult.operationsCount);
                kruskalJson.addProperty("execution_time_ms", kruskalResult.executionTimeMs);

                graphResult.add("prim", primJson);
                graphResult.add("kruskal", kruskalJson);
                resultsArray.add(graphResult);
            }

            // close CSV writer
            csvWriter.close();

            // Save JSON results to file
            JsonObject finalOutput = new JsonObject();
            finalOutput.add("results", resultsArray);
            Writer writer = new FileWriter("assign_3_output.json");
            gson.toJson(finalOutput, writer);
            writer.close();

            System.out.println("\n Results have been saved to assign_3_output.json and assign_3_summary.csv successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



