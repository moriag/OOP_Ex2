package dataStructure;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import algorithms.Graph_Algo;
import utils.Point3D;

class DGraphTest {

	@Test
	void testDGraph() {
		DGraph g= new DGraph() ;
		for(int i=0;i<1000;i++) {
			g.addNode(new node(new Point3D(Math.random()*80,Math.random()*80,Math.random()*80)));
//			g.connect(i, i, 0);
//			g.connect(0, i, 0);
			for(int j=i-1;j>i-11&&j>=0;j--) {
//				g.connect(j, i, 0);
//				g.connect(j, i, 1);
			}
			if(i>0) {
				g.connect(i, i-1, 1);
			}
		}
		for(int i=1;i<100;i++) {
			g.connect(g.getV().size()-i,9, 0);
		}
		for(int i=900;i<1000;i++) {
//			g.connect(g.getV().size()-i,17, 0);
		}
		g.connect(0,g.getV().size()-1,1);
		Graph_Algo ga= new Graph_Algo();
		ga.init(g);
//		assert(ga.isConnected()==true);
		System.out.println(ga.shortestPathDist(0, 0)+"             yyyyyyyyyyyyyyy");
//		assert(ga.shortestPathDist(0, 0)==100);
//		assert(ga.shortestPathDist(0, 0)==1000);
		g.removeEdge(0,g.getV().size()-1);
		assert(ga.shortestPathDist(0, 0)==Double.POSITIVE_INFINITY);
		g.connect(0,0,3);
		System.out.println(ga.shortestPathDist(0, 0));
		assert(ga.shortestPathDist(0, 0)==3);
//		assert(ga.isConnected()==false);
		
	}
//
//	@Test
//	void testGetNode() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetEdge() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testAddNode() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testConnect() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetV() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetE() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testRemoveNode() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testRemoveEdge() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testNodeSize() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testEdgeSize() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetMC() {
//		fail("Not yet implemented");
//	}

}
