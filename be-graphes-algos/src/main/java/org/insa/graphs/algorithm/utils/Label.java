package org.insa.graphs.algorithm.utils;
import org.insa.graphs.model.Node;

public class Label implements Comparable<Label>{
	protected Node node; 
	protected boolean mark;      //marque cout minimum trouvé
	protected Double cost;       // valeur courante du plus court chemin trouvé
	protected Node father;       //sommet precédent donnant le plus court chemin vers ce sommet
	protected boolean inHeap;
	
	
	public Label (Node node) {
		this.node = node;
		this.mark = false;
		this.cost = Double.POSITIVE_INFINITY ;
		this.father = null;
		this.inHeap = false;
	}
	
	public Node getNode() { 
		return this.node;
	}
	public Boolean getMark() { 
		return this.mark;
	}
	public Double getCost() { 
		return this.cost;
	}
	public Node getFather() { 
		return this.father;
	}
	public boolean getInHeap() { 
		return this.inHeap;
	}
	
	
	public void setNode(Node node) { 
		this.node = node ;
	}
	public void setMark(boolean mark) { 
		this.mark = mark;
	}
	public void setCost(Double cost) { 
		this.cost = cost;
	}
	public void setFather(Node father) { 
		this.father = father;
	}
	public void setInHeap() { 
		this.inHeap = true;
	}

	@Override
	public int compareTo(Label other) {
		
		if(this.getCost() < other.getCost()) {
			return -1;
		} else if(this.getCost() == other.getCost()) {
			return 0;
		} else {
			return 1;
		}
		
	}
	
}
