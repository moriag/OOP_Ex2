package gui;

import java.util.Random;

import algorithms.Graph_Algo;
import algorithms.graph_algorithms;
import dataStructure.DGraph;
import dataStructure.graph;
import dataStructure.node;
import utils.Point3D;

public class GraphGuiMain {
	
	// 1) Create a method that creates graph
	// 2) Save the graph into file
	// 3) Open the graph from the gui, and create graph algo for it
	// 4) implement algos on this graph
	private static graph_algorithms generateGraph() {
		graph_algorithms graphAlgo = new Graph_Algo();
		graph dGraph = new DGraph();
		
		graphAlgo.init(dGraph);
		int numOfNodes = 20;
		int numOfEdges = 50;
		float graphSizeLimit = 200; 
		for(int j=0; j < numOfNodes; j++) {
			Point3D  loc = new Point3D(graphSizeLimit * Math.random(),
					graphSizeLimit * Math.random(),
					graphSizeLimit * Math.random());
			dGraph.addNode(new node(loc));
		}
		
		for(int j=0; j < numOfEdges; j++) {
			int src = new Random().nextInt(numOfNodes);
			int dest;
			do {
				dest = new Random().nextInt(numOfNodes);
			} while (dest == src);
			double weight = Math.random();
			dGraph.connect(src, dest, weight);
		}
		String fileName = "example.graph";
		graphAlgo.save(fileName);
		
		return graphAlgo;
	}	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Graph GUI");
		graph_algorithms graphAlgo = generateGraph();
		graph graph=graphAlgo.copy();
		Graph_GUI gui = new Graph_GUI(graph);
		gui.setVisible(true);
	}

}
