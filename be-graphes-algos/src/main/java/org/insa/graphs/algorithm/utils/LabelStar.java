package org.insa.graphs.algorithm.utils;
import org.insa.graphs.model.Node; 

public class LabelStar implements Comparable<LabelStar>{ 
	
	protected Double costDest;  
	protected Node node; 
	protected boolean mark;      //marque cout minimum trouvé
	protected Double cost;       // valeur courante du plus court chemin trouvé
	protected Node father;       //sommet precédent donnant le plus court chemin vers ce sommet
	protected boolean inHeap; 
	
	public LabelStar(Node node) { 
		this.node = node;
		this.mark = false;
		this.cost = Double.POSITIVE_INFINITY ;
		this.father = null;
		this.inHeap = false;
		this.costDest=Double.POSITIVE_INFINITY ;
		
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
	public Double getCostDest() { 
		return this.costDest;
	}  
	public void setCostDest(Double costD) { 
		this.costDest=costD;
	} 
	
	public Double getTotalCost() {  
		return this.costDest + this.cost;
		
	} 
	public int compareTo(LabelStar other) { 
		if(this.getTotalCost() < other.getTotalCost()) {
			return -1;
		} else if(this.getTotalCost() == other.getTotalCost()) {
			return 0;
		} else {
			return 1;
		}
	}
}
