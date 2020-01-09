# OOP_Ex2 
this project is centered around the Graph_Algo class that implements the graph_algorithms interface.

Graph_Algo implements a few useful graph theory algorithms on graph interface implementations.

to use this class I created the graph interface implementation DGraph.

DGraph creates a directed Weighted graph using the classes implementing the node_data and edge_data interfaces.

also included in this project Graph_GUI:

this gui constructors has a graph parameter.

after initiating the gui you will be able to save the graph this graph and load ones you previously saved.

the edges of the graph are directed from the yellow to the red end.

the edges weight appear on the middle of the yellow section (if two vertices are connected by two edges, the weight is closer to the source).

to run the algorithms first choose the one you want to run.

Shortest Path: to choose the source and destination vertices, click the left and right mouse button respectively, and click run.

TSP: choose vertices with the mouse buttons, and click run.

to keep with the changes that occur in the original graph click Refresh.

if the graph is not up to date, the updated graph will be drawn.

click clear to revert back to the graph drawing. this option will not attempt to update the graph.

the scale in the gui is set to 2 and the offset to 100.
