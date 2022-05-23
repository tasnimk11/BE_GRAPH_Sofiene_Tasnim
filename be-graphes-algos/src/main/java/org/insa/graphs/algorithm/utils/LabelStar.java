package org.insa.graphs.algorithm.utils;
import org.insa.graphs.model.Node; 

public class LabelStar extends Label { 
	
	protected Double costEst;  //Cout estimé avec distance à vol d'oiseau
	
	/*
	 * Cas : ShortestPath ==> résonnement sur la distance
	 * 
	*/
	public LabelStar(Node node, Node dest) { 
		super(node);
		this.costEst=node.getPoint().distanceTo(dest.getPoint());
		
	} 
	
	/*
	 * Cas : FastestPath ==> résonnement sur la vitesse maximale (pire des cas)
	 * 
	*/
	public LabelStar(Node node, Node dest, double maxSpeed) { 
		super(node);
		this.costEst=node.getPoint().distanceTo(dest.getPoint())/maxSpeed ;
		
	} 
	
	
	@Override
	public Double getTotalCost() {  
		return this.costEst + this.getCost();
		
	} 
}
