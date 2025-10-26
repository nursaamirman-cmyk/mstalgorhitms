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

                PrimAlgorithm.Result result = PrimAlgorithm.findMST(graph);

                System.out.println("\n Prim's Algorithm Result:");
                System.out.println("MST edges: " + result.mstEdges);
                System.out.println("Total cost: " + result.totalCost);
                System.out.println("Operations count: " + result.operationsCount);
                System.out.printf("Execution time (ms): %.4f\n", result.executionTimeMs);

               
                JsonObject graphResult = new JsonObject();
                graphResult.addProperty("graph_id", id);

                JsonObject inputStats = new JsonObject();
                inputStats.addProperty("vertices", graph.getVerticesCount());
                inputStats.addProperty("edges", graph.getEdgesCount());
                graphResult.add("input_stats", inputStats);

                JsonObject primResult = new JsonObject();
                JsonArray mstEdgesArray = new JsonArray();
                for (Edge edge : result.mstEdges) {
                    JsonObject edgeJson = new JsonObject();
                    edgeJson.addProperty("from", edge.from);
                    edgeJson.addProperty("to", edge.to);
                    edgeJson.addProperty("weight", edge.weight);
                    mstEdgesArray.add(edgeJson);
                }
                primResult.add("mst_edges", mstEdgesArray);
                primResult.addProperty("total_cost", result.totalCost);
                primResult.addProperty("operations_count", result.operationsCount);
                primResult.addProperty("execution_time_ms", result.executionTimeMs);

                graphResult.add("prim", primResult);
                resultsArray.add(graphResult);
            }

            
            JsonObject finalOutput = new JsonObject();
            finalOutput.add("results", resultsArray);

            Writer writer = new FileWriter("assign_3_output.json");
            gson.toJson(finalOutput, writer);
            writer.close();

            System.out.println("\n Results have been saved to assign_3_output.json successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


