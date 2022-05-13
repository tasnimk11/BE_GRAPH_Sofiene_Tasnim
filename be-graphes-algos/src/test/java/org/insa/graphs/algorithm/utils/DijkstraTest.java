package org.insa.graphs.algorithm.utils;
import java.util.ArrayList;

import static org.junit.Assert.*;

import java.io.IOException;

import java.util.Arrays;

import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.*;
import org.insa.graphs.model.*;
import org.insa.graphs.model.RoadInformation.RoadType;
import org.junit.BeforeClass;
import org.junit.Test;


public class DijkstraTest {
	
	// Small graph use for tests
    private static Graph graph;

    // List of nodes
    private static Node[] nodes;
    
    // List of arcs in the graph, a2b is the arc from node A (0) to B (1).
    @SuppressWarnings("unused")
    private static Arc a2b, a2c, a2e, b2c, c2d_1, c2d_2, c2d_3, c2a, d2a, d2e, e2d;

   
    
    @BeforeClass
    public static void initAll() throws IOException { 
    	

        // 10 and 20 meters per seconds
        RoadInformation speed10 = new RoadInformation(RoadType.MOTORWAY, null, true, 36, ""),
                speed20 = new RoadInformation(RoadType.MOTORWAY, null, true, 72, "");

        // Create nodes
        nodes = new Node[5];
        for (int i = 0; i < nodes.length; ++i) {
            nodes[i] = new Node(i, null);
        }

        // Add arcs...
        a2b = Node.linkNodes(nodes[0], nodes[1], 10, speed10, null);
        a2c = Node.linkNodes(nodes[0], nodes[2], 15, speed10, null);
        a2e = Node.linkNodes(nodes[0], nodes[4], 15, speed20, null);
        b2c = Node.linkNodes(nodes[1], nodes[2], 10, speed10, null);
        c2d_1 = Node.linkNodes(nodes[2], nodes[3], 20, speed10, null);
        c2d_2 = Node.linkNodes(nodes[2], nodes[3], 10, speed10, null);
        c2d_3 = Node.linkNodes(nodes[2], nodes[3], 15, speed20, null);
        d2a = Node.linkNodes(nodes[3], nodes[0], 15, speed10, null);
        d2e = Node.linkNodes(nodes[3], nodes[4], 22.8f, speed20, null);
        e2d = Node.linkNodes(nodes[4], nodes[0], 10, speed10, null);

        graph = new Graph("ID", "", Arrays.asList(nodes), null);


    }
    
    @Test 
    public void testShortestPathToAllNodes () {
    	for (int origin=0; origin< nodes.length; origin++) {
    		
    		for(int dest=0 ; dest<nodes.length; dest++) {
    			if(nodes[origin] != nodes[dest]) {
	    			//check if origin==dest
	    			ArcInspector inspecteur=new ArcInspectorFactory().getAllFilters().get(0);
					ShortestPathData data = new ShortestPathData(graph, nodes[origin] ,nodes[dest], inspecteur);
					DijkstraAlgorithm sd= new DijkstraAlgorithm(data); 
					BellmanFordAlgorithm sb = new BellmanFordAlgorithm(data);  
					
					ShortestPathSolution sattendu = sb.run(); 
					ShortestPathSolution strouve = sd.run();  
					
					
	    			if(nodes[origin] == nodes[dest]) {
	    				System.out.println("Origin == Destination ");  
	    				assertEquals(strouve.getPath().getLength(),0,0);
	    			} else { 
	    				
	    				
	    				// si la solution dijkstra n'existe pas
	    				if (strouve.getPath()==null) { 
	    					assertEquals(strouve.getPath(), sattendu.getPath()); 
	    				}else { 
	    					float lengthTrouve=strouve.getPath().getLength();  
	    					float lengthAttendu=strouve.getPath().getLength();  
	    					assertEquals(lengthTrouve, lengthAttendu,0); 
	    					//System.out.println("Le dijkstra donne " +lengthTrouve + "alors que le bellman ford " + lengthAttendu); 
	
	    				}    				
	    			}	
    			}
    		}
    	}    	
    } 
    

   
    
}
