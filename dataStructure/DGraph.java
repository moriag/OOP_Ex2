package dataStructure;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

public class DGraph implements Serializable ,graph{
	private HashMap<Integer,node_data> nodes;
	private HashMap<Integer,HashMap<Integer,edge_data>> edges;
	private int next;//next key to add and avoid replacing existing key values 
	private int E;//num of edges
	private int mod_count;
	
	/**
	 * empty graph
	 */
	public DGraph() {
		this.E=this.mod_count=this.next=0;
		this.edges=new HashMap<Integer, HashMap<Integer,edge_data>>();
		this.nodes=new HashMap<Integer, node_data>();
	}
	
	
	
	/**this will become a reverse graph to g.
	 * the nodes of this graph are shallow copies of g nodes  
	 * @param g
	 */
	public void reverseDGraph(graph g){
		this.mod_count=this.E=this.next=0;
		this.edges=new HashMap<Integer, HashMap<Integer,edge_data>>();
		this.nodes=new HashMap<Integer, node_data>();
		
		for(node_data v1: g.getV()) {
			next=Math.max(next, v1.getKey());
			nodes.put(v1.getKey(),v1);
			edges.put(v1.getKey(), new HashMap<Integer, edge_data>());
		}
		next++;
		for(node_data v: g.getV()) {
			for(edge_data e: g.getE(v.getKey())) {
				this.connect(e.getDest(), e.getSrc(), 0);
			}
		}
		this.mod_count=g.getMC();
	}
	
	
	
	/**this will be a deep copy of graph g
	 * @param g
	 */
	public void copy(graph g){
		this.mod_count=this.E=this.next=0;
		this.edges=new HashMap<Integer, HashMap<Integer,edge_data>>();
		this.nodes=new HashMap<Integer, node_data>();
		for(node_data v1: g.getV()) {
//			next=Math.max(next, v1.getKey());
			this.forceAdd(v1);
//			nodes.put(v1.getKey(),v1);
			edges.put(v1.getKey(), new HashMap<Integer, edge_data>());
		}
//		next++;
		for(node_data v: g.getV()) {
			for(edge_data e: g.getE(v.getKey())) {
				this.connect( e.getSrc(),e.getDest(), e.getWeight());
			}
		}
		this.mod_count=g.getMC();
	}
	
	@Override
	public node_data getNode(int key) {
		return nodes.get(key);
	}

	@Override
	public edge_data getEdge(int src, int dest) {
		return edges.get(src).get(dest);
		
	}

	/**
	 * adds a copy of n to this, the key of the copy will be changed to avoid overlapping keys  
	 */
	@Override
	public void addNode(node_data n) {
		mod_count++;
		nodes.put(next,new node(n,next));
		edges.put(next, new HashMap<Integer, edge_data>());
		next++;
	}
	/**
	 * adds a copy of n to this, the key of the copy will be changed to avoid overlapping keys.
	 * @return the key of the new node  
	 */
	public int addNodegetkey(node_data n) {
		addNode(n);
		return next-1;
	}

	/**
	 *Connect or replace an edge between node src to node dest 
	 *the new edge weight is set to w
	 */
	@Override
	public void connect(int src, int dest, double w) {
		mod_count++;
		if(edges.get(src).containsKey(dest))E--;
		edges.get(src).put(dest,new edge(src,dest,w));
		E++;
	}

	@Override
	public Collection<node_data> getV() {
		return this.nodes.values();
	}

	@Override
	public Collection<edge_data> getE(int node_id) {
		return edges.get(node_id).values();
	}

	@Override
	public node_data removeNode(int key) {
		mod_count++;
		node_data ans=nodes.remove(key);
		if(ans==null)return null;
		E-=edges.remove(key).size();
		for(HashMap<Integer,edge_data> outE: edges.values()) {
			if(outE.remove(key)!=null)E--;
		}
		return ans;
	}

	@Override
	public edge_data removeEdge(int src, int dest) {
		mod_count++;
		edge_data ans=edges.get(src).remove(dest);
		if(ans!=null)E--;
		return ans;
	}

	@Override
	public int nodeSize() {
		return nodes.size();
	}

	@Override
	public int edgeSize() {
		return E;
	}

	@Override
	public int getMC() {
		return mod_count;
	}

	/**forcibly adds node with its key
	 * if this already contain a value mapped to node.key the node will be replaced.
	 * no changes will occur to this edges   
	 * @param node
	 */
	public void forceAdd(node_data node) {
		mod_count++;
		nodes.put(node.getKey(),node);
		next=Math.max(next, node.getKey());
		next++;
		
	}
	

}
