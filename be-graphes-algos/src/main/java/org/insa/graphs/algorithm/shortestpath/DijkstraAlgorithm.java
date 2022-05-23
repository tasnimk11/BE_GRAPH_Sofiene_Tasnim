package org.insa.graphs.algorithm.shortestpath;


import org.insa.graphs.algorithm.utils.*;
import org.insa.graphs.model.*;

import java.util.ArrayList;
import java.util.Collections;

//import org.insa.graphs.algorithm.AbstractAlgorithm;
import org.insa.graphs.algorithm.AbstractSolution.Status;


public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    //Définiton variables à initialiser
    final ShortestPathData data = getInputData();   
    Graph graph = data.getGraph();
    int nbNodes = graph.size();
    // Table of distances.
    protected Label [] labels= new Label[nbNodes];
    
    
    //fonction qui initialise le tableau de distances
    
    protected void initLabels () {
    	int i=0;
    	for(Node n:graph.getNodes()) {
    		labels[i] = new Label(n);
    		i++;
    	}   
    }
    
    
    @Override
    protected ShortestPathSolution doRun() {
    	/*
    		Declarations
        */
        ShortestPathSolution solution = null;     // Binary heap of labels 
        BinaryHeap<Label> tas = new BinaryHeap<Label>();
	    Label x, y ;
	    /*
	     	Initializations
	     */
	    initLabels();
        Label s = new Label(data.getOrigin());   // Initialize origin
        s.setInHeap();
        s.setCost(0.0);
        labels[s.getNode().getId()] = s;
        
        tas.insert(s);
        
        
        notifyNodeReached(data.getOrigin());
        
        boolean found = false;
        
        /*
     		Dijkstra Algorithm
        */
               
        while (!tas.isEmpty() && !found) {
        	
        	x = tas.deleteMin(); // Extract minimum
        	x.setMark(true);   //Mark current 
        	notifyNodeReached(x.getNode());
        	
        	
        	found = (x.getNode() == data.getDestination()) ; //check if this node is the destination
        	
        	
        	
        	for (Arc arc: x.getNode().getSuccessors()) { //Get successors of current node
        		// Small test to check allowed roads...
                if (!data.isAllowed(arc)) {
                    continue;
                }
        		
        		y = labels[arc.getDestination().getId()];
        		
        		//check if not marked
        		if(! y.getMark() ) {	
        			Double Wxy = data.getCost(arc);
        			if (y.getCost() > (x.getCost()+ Wxy)) {
        				
        				if (y.getInHeap()) { // cost(y) <infinity
        					tas.remove(y);
        				} 
    					y.setCost(x.getCost()+ Wxy);
        				y.setFather(arc);
    					y.setInHeap();
    					tas.insert(y);
        			}
        		}
        	}
        	
        	
        	
        }

        
        // Destination has no predecessor, the solution is infeasible...
        if (labels[data.getDestination().getId()] == null || !found) {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }  else {
        	// The destination has been found, notify the observers.
            notifyDestinationReached(data.getDestination());

            // Create the path from the array of predecessors...
            ArrayList<Arc> arcs = new ArrayList<>();
            Arc arc = labels[data.getDestination().getId()].getFather();
            while (arc != null) {
                arcs.add(arc);
                arc = labels[arc.getOrigin().getId()].getFather();
            }

            // Reverse the path...
            Collections.reverse(arcs);

            // Create the final solution.
            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
        }
          
        
        return solution;
    }

}
