#  Assignment 3 – Minimum Spanning Tree Algorithms (Prim’s & Kruskal’s)

##  Overview
This project focuses on the implementation and comparison of Prim’s and Kruskal’s algorithms for finding the Minimum Spanning Tree (MST) of different graphs.  
It aims to evaluate both correctness and performance on small, medium, and large datasets using Java.

All graph data is read from a JSON file, processed through both algorithms, and results are automatically saved and analyzed.

---

##  Project Structure

DAAassignment3/
│
├── src/
│ ├── Main.java # Main file that runs both algorithms
│ ├── Graph.java # Graph data structure
│ ├── Edge.java # Edge class (with comparable weights)
│ ├── PrimAlgorithm.java # Implementation of Prim's Algorithm
│ └── KruskalAlgorithm.java # Implementation of Kruskal's Algorithm
│
├── libs/
│ └── gson-2.10.1.jar # Library used for JSON parsing
│
├── assign_3_input.json # Input dataset containing multiple graphs
├── assign_3_output.json # Output JSON containing detailed MST results
├── assign_3_summary.csv # Summary comparison file for performance analysis
└── README.md # Project documentation



##  How It Works
1. The program reads graphs from `assign_3_input.json`.
2. It executes Prim’s and Kruskal’s algorithms for each graph.
3. For every run, it calculates:
   - MST total cost  
   - Number of vertices and edges  
   - Operation count  
   - Execution time (in milliseconds)
4. The results are saved automatically into:
   - `assign_3_output.json` — detailed per-graph data  
   - `assign_3_summary.csv` — concise performance summary  



## Example Output

Graph ID: 1
Vertices: 4
Edges: 5

Prim's Algorithm:
MST edges: [(A - B, 2), (B - C, 1), (B - D, 4)]
Total cost: 7
Operations: 4
Execution time (ms): 0.38

Kruskal's Algorithm:
MST edges: [(B - C, 1), (A - B, 2), (B - D, 4)]
Total cost: 7
Operations: 4
Execution time (ms): 0.91



##  Performance Summary
The CSV file `assign_3_summary.csv` contains all measured results.

Graph ID,Algorithm,Total Cost,Operations Count,Execution Time (ms)
1,Prim,7,4,0.3802
1,Kruskal,7,4,0.9186
2,Prim,20,7,0.0435
2,Kruskal,20,7,0.0531
3,Prim,22,9,0.0610
3,Kruskal,22,9,0.0963



##  Analysis
- Both algorithms always produce the same total MST cost, proving correctness.  
- Kruskal’s algorithm requires sorting all edges first, which makes it slightly slower on dense graphs.  
- Prim’s algorithm performs better when the graph is dense because it grows the MST step-by-step from one node.  
-  For small or sparse graphs, both algorithms perform nearly identically.  
-  The execution times and operation counts are small and consistent, confirming program efficiency.



##  Testing & Validation
Automated validation ensures that:
- The total cost of MST is identical for both algorithms.  
- Each MST contains exactly V − 1 edges.  
- No cycles exist in the MST.  
- Disconnected graphs are handled gracefully.  
- Execution time and operation counts are non-negative and consistent.



##  Technologies Used
- Java (JDK 17+)
- Gson Library 2.10.1 for JSON parsing  
- VS Code for development  
- Git & GitHub for version control  


##  References
1. Osipov, V., Sanders, P., & Singler, J. (2009). *The Filter-Kruskal Minimum Spanning Tree Algorithm*.  
   In *Proceedings of ALENEX 2009*. SIAM. https://doi.org/10.1137/1.9781611972894.5  
2. Mohan, A., Leow, W. X., & Hobor, A. (2021). *Functional Correctness of C Implementations of Dijkstra’s, Kruskal’s, and Prim’s Algorithms*.  
   In *Computer Aided Verification (LNCS, vol. 12753)*. Springer. https://doi.org/10.1007/978-3-030-81688-9_37



##  Author
Nursaya Mirman


GitHub Repository: https://github.com/nursaamirman-cmyk/mstalgorhitms
