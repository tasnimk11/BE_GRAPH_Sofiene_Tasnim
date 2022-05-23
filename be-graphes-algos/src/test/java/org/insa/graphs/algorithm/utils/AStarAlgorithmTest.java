package org.insa.graphs.algorithm.utils;
import java.util.ArrayList;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Arrays;

import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.*;
import org.insa.graphs.model.*;
import org.insa.graphs.model.RoadInformation.RoadType;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.junit.BeforeClass;
import org.junit.Test;


public class AStarAlgorithmTest {
	
	// Small graph used for tests
    private static Graph graph;
    
    // Real map used for tests
    private static Graph graphMap;

    // List of nodes of simple graph
    private static Node[] nodes;
    
    
    // List of arcs in the graph, a2b is the arc from node A (0) to B (1).
    @SuppressWarnings("unused")
    private static Arc a2b, a2c, a2e, b2c, c2d_1, c2d_2, c2d_3, c2a, d2a, d2e, e2d;

   
    
    @BeforeClass
    public static void initAll() throws IOException { 
      //	
      // INIT small graph
      //	
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
        
        
      //	
      // INIT real map : haiti-and-domrep.
      //
        
        final String mapName = "/home/tkammoun/Bureau/commetud/3emeAnneMIC/Graphes-et-Algorithmes/Maps/haiti-and-domrep.mapgr";
        final GraphReader reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
        graphMap = reader.read();
    

    }
    
    @Test 
    public void testShortestPathToAllNodes () {
    	for (int origin=0; origin< nodes.length; origin++) {
    		
    		for(int dest=0 ; dest<nodes.length; dest++) {
    			if(nodes[origin] != nodes[dest]) {
	    			//check if origin==dest
	    			ArcInspector inspecteur=new ArcInspectorFactory().getAllFilters().get(0);
					ShortestPathData data = new ShortestPathData(graph, nodes[origin] ,nodes[dest], inspecteur);
					DijkstraAlgorithm se= new DijkstraAlgorithm(data); 
					AStarAlgorithm sd= new AStarAlgorithm(data); 
					BellmanFordAlgorithm sb = new BellmanFordAlgorithm(data);  
					
					
					
					ShortestPathSolution sattendu2 = se.run();
					ShortestPathSolution strouve = sd.run();
					ShortestPathSolution sattendu = sb.run(); 
					   
					
					
	    			if(nodes[origin] == nodes[dest]) {
	    				System.out.println("Origin == Destination ");  
	    				assertEquals(strouve.getPath().getLength(),0,0);
	    			} else { 
	    				
	    				
	    				// si la solution n'existe pas
	    				if (strouve.getPath()==null) { 
	    					assertEquals(strouve.getPath(), sattendu.getPath()); 
	    				}else { 
	    					float lengthTrouve=strouve.getPath().getLength();  
	    					float lengthAttendu=sattendu.getPath().getLength();  
	    					assertEquals(lengthTrouve, lengthAttendu,0); 
	    					
	    				}    				
	    			}	
    			}
    		}
    	}    	
    } 
    
    
    
    @Test 
    public void TestNullPath(){
    	//Fix origin = destination 
		int origin = 3;
		int dest   = 3;
		//Create Path
		ArcInspector inspecteur=new ArcInspectorFactory().getAllFilters().get(0);
		ShortestPathData data = new ShortestPathData(graph, nodes[origin] ,nodes[dest], inspecteur);
		AStarAlgorithm solD= new AStarAlgorithm(data); 
		ShortestPathSolution strouve = solD.run(); 
		
		assertNull(strouve.getPath());
	 }
    
    @Test
    public void TestNonConnexPath() {
    	//Fix origin and destination, each on different side of the land
		int origin = 265352;
		int dest   = 48311;
		//Create Path
		ArcInspector inspecteur=new ArcInspectorFactory().getAllFilters().get(0);
		ShortestPathData data = new ShortestPathData(graphMap,graphMap.getNodes().get(origin) ,graphMap.getNodes().get(dest), inspecteur);
		AStarAlgorithm solD= new AStarAlgorithm(data); 
		ShortestPathSolution strouve = solD.run(); 
		
		assertNull(strouve.getPath());
    }
 
    
}
