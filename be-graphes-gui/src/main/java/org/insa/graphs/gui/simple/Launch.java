package org.insa.graphs.gui.simple;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.AStarAlgorithm;
import org.insa.graphs.algorithm.shortestpath.BellmanFordAlgorithm;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.gui.drawing.Drawing;
import org.insa.graphs.gui.drawing.components.BasicDrawing;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.BinaryPathReader;
import org.insa.graphs.model.io.GraphReader;
import org.insa.graphs.model.io.PathReader;


public class Launch {

    /**
     * Create a new Drawing inside a JFrame an return it.
     * 
     * @return The created drawing.
     * 
     * @throws Exception if something wrong happens when creating the graph.
     */
    public static Drawing createDrawing() throws Exception {
        BasicDrawing basicDrawing = new BasicDrawing();
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("BE Graphes - Launch");
                frame.setLayout(new BorderLayout());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                frame.setSize(new Dimension(800, 600));
                frame.setContentPane(basicDrawing);
                frame.validate();
            }
        });
        return basicDrawing;
    }

    public static void main(String[] args) throws Exception {

        // Visit these directory to see the list of available files on Commetud.
        final String mapName = "/home/tkammoun/Bureau/commetud/3emeAnneMIC/Graphes-et-Algorithmes/Maps/insa.mapgr";
        final String pathName = "/home/tkammoun/Bureau/commetud/3emeAnneMIC/Graphes-et-Algorithmes/Paths/path_fr31insa_rangueil_r2.path";

        // Create a graph reader.
        final GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));

        // TODO: Read the graph.
        final Graph graph = reader.read();

        // Create the drawing:
        final Drawing drawing = createDrawing();

        // TODO: Draw the graph on the drawing.
        drawing.drawGraph(graph);
        
        // TODO: Create a PathReader.
        final PathReader pathReader = new BinaryPathReader(
        		new DataInputStream(new BufferedInputStream(new FileInputStream(pathName))));
        // TODO: Read the path.
        final Path path = pathReader.readPath(graph);

        // TODO: Draw the path.
        drawing.drawPath(path);
        
        
        //Test ASTAR
        java.util.List<Node> nodes = graph.getNodes();
        boolean test = true;
        for (int origin=0; origin< nodes.size(); origin++) {
	        for(int dest=0 ; dest<nodes.size(); dest++) {
				if(nodes.get(origin) != nodes.get(dest)) {
					ArcInspector inspecteur=new ArcInspectorFactory().getAllFilters().get(0);
					ShortestPathData data = new ShortestPathData(graph, nodes.get(origin) ,nodes.get(dest), inspecteur);
					
					DijkstraAlgorithm sD= new DijkstraAlgorithm(data); 
					AStarAlgorithm sDA= new AStarAlgorithm(data); 
					BellmanFordAlgorithm sB = new BellmanFordAlgorithm(data);  
					
					ShortestPathSolution sattenduD = sD.run();
					ShortestPathSolution strouveDA = sDA.run();
					ShortestPathSolution sattenduB = sB.run(); 
					
					if(nodes.get(origin) == nodes.get(dest)) {
	    				//System.out.println("Origin == Destination ");   			
	    				test = test && strouveDA.getPath().getLength()<=0.1;
					} else { 				
    					// si la solution n'existe pas
	    				if (strouveDA.getPath()==null) { 
	    					//System.out.println("Trouve : strouveDA null"); 
	    				} else { 
	    					float lengthTrouve=strouveDA.getPath().getLength();  
	    					float lengthAttendu=sattenduD.getPath().getLength();
	    					float delta = lengthTrouve-lengthAttendu;
	    					
	    					test = test && (int)delta==0;
	    				}
					}
				}
	        }
        
        }
        System.out.println("test ShortestPathSolution : "+test);
        

 }
}
