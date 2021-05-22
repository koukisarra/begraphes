package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.*;

public class Label implements Comparable<Label>{
	
	protected Node CurrentNode;
	private Arc FatherNode;
	private boolean Marked;
	private double Cost;
	

	public Label(Node CurrentNode) {
		this.CurrentNode = CurrentNode;
		this.FatherNode = null;
		this.Marked = false;
		this.Cost = Double.POSITIVE_INFINITY;	
	}
	
	/* Getters */ 
	public Node getCurrentNode() {
		return this.CurrentNode;
	}
	public Arc getFather() {
		return this.FatherNode;
	}
	public boolean isMarked() {
		return this.Marked;
	}
	public double getTotalCost() {
		return this.getCost();
	}
	
	/* Setters */ 
	public void setFather(Arc FatherNode) {
		this.FatherNode = FatherNode;
	}
	public void setMarkTrue() {
		this.Marked = true;
	}
	public double getCost() {
		return this.Cost;
	}
	public void setCost(double cout) {
		this.Cost = cout;
	}
	
	

	@Override
	public int compareTo(Label o) {
		return Double.compare(this.getTotalCost(), o.getTotalCost());
	}
	
}