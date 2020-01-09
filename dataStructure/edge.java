package dataStructure;

import java.io.Serializable;

public class edge implements Serializable, edge_data {
	private int src;
	private int dest;
	private double weight;
	private int tag;
	private String info;

	public edge(int src, int dest, double w) {
		this.src=src;
		this.dest=dest;
		this.weight=w;
	}

	@Override
	public int getSrc() {
		return src;
	}

	@Override
	public int getDest() {
		return dest;
	}

	@Override
	public double getWeight() {
		return weight;
	}

	@Override
	public String getInfo() {
		return info;
	}

	@Override
	public void setInfo(String s) {
		info=s;
		
	}

	@Override
	public int getTag() {
		return tag;
	}

	@Override
	public void setTag(int t) {
		this.tag=t;
	}

}
