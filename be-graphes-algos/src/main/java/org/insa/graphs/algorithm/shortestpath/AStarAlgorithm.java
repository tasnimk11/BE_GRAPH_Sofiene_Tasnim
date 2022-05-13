package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collections; 



import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.algorithm.utils.LabelStar;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Path;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    } 
    @Override
    protected ShortestPathSolution doRun() {
    	
        final ShortestPathData data = getInputData();     
        Graph graph = data.getGraph();
        final int nbNodes = graph.size(); 
        
        
        ShortestPathSolution solution = null;
        
        // Table of distances.
        LabelStar  labels[]= new LabelStar[nbNodes];
        
        // Binary heap of labels 
        BinaryHeap<LabelStar> tas = new BinaryHeap<LabelStar>();
        
        //Table of fathers (predecessors) 
        Arc[] predecessorArcs = new Arc[nbNodes];
        
        
        // Initialize origin
        LabelStar s = new LabelStar(data.getOrigin());  
        labels[s.getNode().getId()] = s;
        
        tas.insert(s);
        s.setInHeap();
        s.setCost(0.0); 
        s.setCostDest(data.getOrigin().getPoint().distanceTo(data.getDestination().getPoint()));
        
        notifyNodeReached(data.getOrigin());
        
        boolean found = false;
        LabelStar x, y ;
        while (!tas.isEmpty() && !found) {
        	
        	
        	
        	x = tas.deleteMin(); // Extract minimum
        	x.setMark(true);   //Mark current 
        	
        	
        	//check if this node is the destination
        	found = x.getNode() == data.getDestination();
        	
        	//Get successors of current node
        	for (Arc arc: x.getNode().getSuccessors()) {
        		// Small test to check allowed roads...
                if (!data.isAllowed(arc)) {
                    continue;
                }
        		
        		y = labels[arc.getDestination().getId()];
        		
        		//if label still does not exist
        		if (y == null) {
        			notifyNodeReached(arc.getDestination());
        			y = new LabelStar(arc.getDestination());
        			labels[arc.getDestination().getId()] = y;
        		} 
       
        		
        		//check if not marked
        		if(! y.getMark() ) {
        			
        			float Wxy = arc.getLength();
        			//if (y.getTotalCost() > (0*x.getNode().getPoint().distanceTo(data.getDestination().getPoint()))) {
        			if (y.getCost() > (x.getCost()+ Wxy)) {
        				if (y.getInHeap()) {   
        					tas.remove(y);
        					y.setCost(x.getCost()+ Wxy); 
        					y.setCostDest(x.getNode().getPoint().distanceTo(data.getDestination().getPoint()));
            				y.setFather(x.getNode());
        					tas.insert(y);
        					
        				} else {
        					y.setCost(x.getCost()+ Wxy); 
        					y.setCostDest(x.getNode().getPoint().distanceTo(data.getDestination().getPoint()));
            				y.setFather(x.getNode());
        					y.setInHeap();
        					tas.insert(y);
        				}
        				//Add this arc to predecessors that lead to s
        				predecessorArcs[arc.getDestination().getId()]= arc;
        			}
        		}
        	}
        	
        	
        	
        }

        
        // Destination has no predecessor, the solution is infeasible...
        if (predecessorArcs[data.getDestination().getId()] == null) {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        else {
        	// The destination has been found, notify the observers.
            notifyDestinationReached(data.getDestination());

            // Create the path from the array of predecessors...
            ArrayList<Arc> arcs = new ArrayList<>();
            Arc arc = predecessorArcs[data.getDestination().getId()];
            while (arc != null) {
                arcs.add(arc);
                arc = predecessorArcs[arc.getOrigin().getId()];
            }

            // Reverse the path...
            Collections.reverse(arcs);

            // Create the final solution.
            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
        }
          
        
        return solution;
    }

}


