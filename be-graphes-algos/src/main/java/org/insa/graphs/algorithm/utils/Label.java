package org.insa.graphs.algorithm.utils;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Arc;

public class Label implements Comparable<Label>{
	protected Node node; 
	protected boolean mark;      //marque cout minimum trouvé
	protected Double cost;       // valeur courante du plus court chemin trouvé
	protected Arc father;       //sommet precédent donnant le plus court chemin vers ce sommet
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
	public Double getTotalCost() { 
		return this.cost;
	}
	public Arc getFather() { 
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
	public void setFather(Arc father) { 
		this.father = father;
	}
	public void setInHeap() { 
		this.inHeap = true;
	}

	@Override
	public int compareTo(Label other) {
		
		if(this.getTotalCost() < other.getTotalCost()) {
			return -1;
		} else  { 
			if(this.getTotalCost() > other.getTotalCost()) {
				return 1;
			}else {	//this.getTotalCost() == other.getTotalCost()
				if (this.getTotalCost()-this.getCost()<other.getTotalCost()-other.getCost()) {
					return -1;
				} else if (this.getTotalCost()-this.getCost()>other.getTotalCost()-other.getCost()) {
					return 1;
				} else {
					return 0;
				}
			}
		}
		
	}
	
}
