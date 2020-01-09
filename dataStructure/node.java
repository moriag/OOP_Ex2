package dataStructure;

import java.io.Serializable;

import utils.Point3D;

public class node implements Serializable,node_data {
	private int key;
	private Point3D loc;
	private double weight;
	private int tag;
	private String info;
	
	
	public node(node_data n,int key) {
		this.loc=new Point3D(n.getLocation().x(),n.getLocation().y(),n.getLocation().z());
		this.key=key;
		this.weight=n.getWeight();
		this.tag=n.getTag();
	}
	public node(Point3D p) {
		this.loc=new Point3D(p.x(),p.y(),p.z());;
		tag=0;
	}
	public node() {
		this.loc=new Point3D(0, 0, 0);
	}
	@Override
	public int getKey() {
		return key;
	}

	@Override
	public Point3D getLocation() {
		return loc;
	}

	@Override
	public void setLocation(Point3D p) {
		this.loc=new Point3D(p);
		
	}

	@Override
	public double getWeight() {
		return weight;
	}

	@Override
	public void setWeight(double w) {
		this.weight=w;
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
