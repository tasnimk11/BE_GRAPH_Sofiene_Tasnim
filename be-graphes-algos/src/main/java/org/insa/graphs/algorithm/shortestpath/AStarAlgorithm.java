package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collections; 



import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.algorithm.utils.Label;
import org.insa.graphs.algorithm.utils.LabelStar;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    } 
    
    
    @Override
    
    //fonction qui initialise le tableau de distances
    
    protected void initLabels () {
    	
    	Double maxSpeed =  50.0 ; //Valeur arbitraire => à mesurer
    	
    	if(AbstractInputData.Mode.TIME == data.getMode()) { //FastestPath
    		maxSpeed = (double) graph.getGraphInformation().getMaximumSpeed();
    	}
    	
    	int i=0;
    	for(Node n:graph.getNodes()) {
    		if(AbstractInputData.Mode.TIME == data.getMode()) { //FastestPath
    			labels[i] = new LabelStar(n,data.getDestination(),maxSpeed);
    		} else { //ShortestPath
    			labels[i] = new LabelStar(n,data.getDestination());
    		}
    		i++;
    	}   
    }

}


