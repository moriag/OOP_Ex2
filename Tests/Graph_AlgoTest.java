package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

import algorithms.Graph_Algo;
import algorithms.graph_algorithms;
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node;
import dataStructure.node_data;
import gui.Graph_GUI;
import utils.Point3D;

class Graph_AlgoTest {
	class myAlgos{
		public myAlgos(int k) {
			algos=new graph_algorithms[10];
			graphs=new graph[10];
			for(int i=0;i<algos.length;i++) {
				graphs[i]=new DGraph();
				algos[i]=new Graph_Algo();
				algos[i].init(graphs[i]);
				for(int j=0;j<k&&(i!=1||j<100);j++) {
					graphs[i].addNode(new node(new Point3D(Math.random()*80,Math.random()*80,Math.random()*80)));
				}
			}
		}
		graph_algorithms[] algos;
		graph[] graphs;
		public void edges(String s) {
			int i;
//			for(i=0;i<graphs[0].getV().size();i++) {
//				graphs[0].connect(i, i, 1);//Vertex loops
//			}
			for(i=0;i<graphs[1].getV().size();i++) {//complete
				for(int j=i+1;j<graphs[1].getV().size();j++) {
					graphs[1].connect(i, j, i+j);
					graphs[1].connect(j, i, i+j);
				}
			}
			for(i=0;i<graphs[2].getV().size()-1;i++) {
				graphs[2].connect(i, i+1, 1);
				graphs[3].connect(i, i+1, 1);//line
				graphs[4].connect(i, i+1, 1);
				graphs[5].connect(i, i+1, 1);
			}
			graphs[2].connect(graphs[2].getV().size()-1,0,1);//circle
			if(s=="ic")return;
			graphs[4].connect(graphs[4].getV().size()-1,0,1);
			for(i=11;i<graphs[4].getV().size();i++) {
				graphs[4].connect(9, i, 1);//skip length-10
			}
			graphs[5].connect(graphs[5].getV().size()-1,0,1);
			for(i=0;i<graphs[5].getV().size();i++) {
				graphs[5].connect(i, 0, 1);//skip length-i
			}
			if(s=="spd")return;
			
			for(i=0;i<graphs[6].getV().size();i++) {
				graphs[6].connect(i, i, Math.random());//Vertex loops
			}
			for(i=0;i<graphs[7].getV().size();i++) {//complete
				for(int j=0;j<graphs[7].getV().size();j++) {
					if(i!=j)graphs[7].connect(i, j,Math.random()+1);
				}
			}
			for(i=0;i<graphs[8].getV().size()-1;i++) {
				graphs[8].connect(i, i+1, Math.random()+1);
			}
			graphs[8].connect(graphs[8].getV().size()-1,0,Math.random()+1);//circle
		
			double j;
			int k,m;
			for(i=0;i<graphs[9].getV().size()*5;i++) {
				j=Math.random()*40;
				j-=j%1;
				k=(int)Math.floor(Math.random()*graphs[9].nodeSize());
				m=(int)Math.floor(Math.random()*graphs[9].nodeSize());
				if(k==m) {i--;continue;}
				graphs[9].connect(k, m, j+1);
			}
			
		}
	}
	@Test
	void testInitGraph() {
		myAlgos A=new myAlgos(100);
	}


	@Test
	void testIsConnected() {
		myAlgos A=new myAlgos(100000);
		A.edges("ic");
		assert(A.algos[0].isConnected()==false);//Vertex loops
		assert(A.algos[1].isConnected()==true);//complete

		assert(A.algos[2].isConnected()==true);//circle
		assert(A.algos[3].isConnected()==false);//line
	}
	
	@Test
	void testShortestPathDist() {
		myAlgos al=new myAlgos(1000);
		al.edges("spd");


		for(int i=0;i<al.graphs[1].getV().size();i++) {
			for(int j=i+1;j<al.graphs[1].getV().size();j++) {

			assert(al.algos[1].shortestPathDist(i,j)==i+j);//complete
			}
		}
		for(int i=1;i<al.graphs[2].getV().size();i++) {
			assert(al.algos[2].shortestPathDist(i,i-1)==al.graphs[2].getV().size()-1);//circle
		}
		for(int i=0;i<al.graphs[3].getV().size();i++) {
			assert(al.algos[3].shortestPathDist(0,i)==i);//line

		}
		for(int i=10;i<al.graphs[4].getV().size();i++) {
			assert(al.algos[4].shortestPathDist(0,i)==10);//skip
			
		}
		
		for(int i=1;i<al.graphs[5].getV().size();i++) {

			assert(al.algos[5].shortestPathDist(i,i-1)==i);//skip
		}
	
	}

	@Test
	void testShortestPath() {
		myAlgos al=new myAlgos(100);
		al.edges("sp");
		List<node_data> a;
		double dist;
	
		for(int i=0; i<10;i++) {
		
			for(int j=0;j<al.graphs[i].nodeSize();j++) {
				for(int k=0;k<al.graphs[i].nodeSize();k++) {
//					System.out.println("i="+i+",  j="+j+",  k="+k);
					dist = al.algos[i].shortestPathDist(j,k);
					
					a = al.algos[i].shortestPath(j,k);
					
					if(dist==Double.POSITIVE_INFINITY) {
						assert(a==null);
						continue;
					}
					assert(a.get(a.size()-1).getKey()==k);
					assert(a.get(0).getKey()==j);

					for(int e=1;e<a.size();e++) {

						dist-=al.graphs[i].getEdge(a.get(e-1).getKey(),a.get(e).getKey()).getWeight();

					}
					

					assert(Math.abs(dist)<0.0000001);

					}
				
				}
		}
	}
	@Test
	void testTSP() {
		myAlgos al=new myAlgos(100);
		al.edges("sp");
		List<node_data> a,a1;
		List<Integer> b;
		double dist,dist1;

		for(int i=0; i<10;i++) {
		
			for(int j=0;j<al.graphs[i].nodeSize();j++) {
				for(int k=0;k<al.graphs[i].nodeSize();k++) {
					dist=dist1 = al.algos[i].shortestPathDist(j,k);
					if(dist==0)continue;
					b=new ArrayList<Integer>();
					a1=al.algos[i].shortestPath(j,k);
					if(a1==null||a1.size()<2)continue;
					for(node_data n: a1) {
//						System.out.println(n.getKey());
						b.add(n.getKey());
					}
					a = al.algos[i].TSP(b);
//					if(b!=null)System.out.println("b size:  "+b.size()+"  i="+i+", j="+j+", k="+k);
//					System.out.println("end");
					
					
					assert(a.containsAll(a1));
					if(i==9)continue;
					
					dist=0;
					for(int e=1;a!=null&&e<a.size();e++) {
						dist+=al.graphs[i].getEdge(a.get(e-1).getKey(),a.get(e).getKey()).getWeight();
					}
					

					assert(dist<=dist1);
				}
				
			}
//		System.out.println(Err+","+drr+" ,tspdistm : "+mean/(Err)+" ,distm : "+mean2/(Err+drr));
		}
	}	

	@Test
	void testCopy() {
		graph org=new DGraph();
		org.addNode(new node(new Point3D(Math.random(),Math.random(),Math.random())));
		org.addNode(new node(new Point3D(Math.random(),Math.random(),Math.random())));
		org.connect(0,1,Math.random());
		Graph_Algo g_a=new Graph_Algo();
		g_a.init(org);
		graph copy=g_a.copy();
		assert(org.getNode(0).getLocation().distance3D(copy.getNode(0).getLocation())==0);
		assert(org.getNode(1).getLocation().distance3D(copy.getNode(1).getLocation())==0);
		assert(org.getEdge(0,1).getWeight()==copy.getEdge(0,1).getWeight());
		assert(org.getEdge(1,0)==copy.getEdge(1,0));//null
		copy.removeEdge(0,1);
		assert(org.getEdge(0,1)!=null);
		org.removeNode(0);
		assert(copy.getNode(0)!=null);
		
	}
}

