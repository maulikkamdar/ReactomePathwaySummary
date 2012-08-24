package org.reactome.summary.client.layout;

import java.util.HashMap;
import java.util.List;

import org.reactome.summary.client.Vector;
import org.reactome.summary.client.view.CanvasEdge;
import org.reactome.summary.client.view.CanvasNode;
import org.reactome.summary.client.view.CanvasText;
import org.reactome.summary.shared.GraphNode;
import org.reactome.summary.shared.ConnectingEdge;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.user.client.Window;
/**
 * Google Summer of Code 2012 Project
 * Reactome Pathway Summary Visualization
 *
 */
/**
 * Renders the top level pathways as circular nodes, 
 * whose radius is directly proportional to the number of the participating molecules in the pathways,
 * and are connected to other pathways with connecting edges,
 * whose width is directly proportional to the similarity measure between the two pathways.
 * The nodes are arranged in a force-directed layout calculated over 100 iterations.
 * Each node has a force of Repulsion acting on it due to every other node, calculated as 
 * 1000 * (radius of node1 * radius of node2 )/ distance^2
 * and has a force of Attraction acting on it due to every edge connecting the node to other nodes, calculated as
 * Edge_similarity * distance between the nodes/ 100
 * 
 * @author maulik
 *
 */
public class ForceLayout extends Layout{

	int forceLayoutRadius = (int) (0.85* Window.getClientHeight()/2)-50;
	protected HashMap<Integer, Vector> nodesVelocity = new HashMap<Integer, Vector>();
	final double COULOMB_CONSTANT = 1000;
	final double DAMPING_CONSTANT = 0.05;
	int loopIterations;
	
	public ForceLayout(List<GraphNode> nodes, List<ConnectingEdge> edges, Vector midpoint, int loops) {
		super(nodes, edges, midpoint);
		this.loopIterations = loops;
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<CanvasNode> calculateNodePosition() {
		// TODO Auto-generated method stub
		for(int i = 0; i < allNodes.size() ; i ++) {
			double position = 2*Math.PI*i/allNodes.size();
			Vector coOrdinates = new Vector();
			coOrdinates.set(midpoint);
			coOrdinates.add(forceLayoutRadius*Math.cos(position), forceLayoutRadius*Math.sin(position));
			nodesPosition.put(allNodes.get(i).getDbId(), coOrdinates);
			nodesVelocity.put(allNodes.get(i).getDbId(), new Vector(0,0));
		}
		
		int interval = 0;
		while(interval < loopIterations) {
			for(int i = 0; i < allNodes.size() ; i ++) {
				Vector netForce = new Vector(0,0);
				for(int j = 0; j < allNodes.size() ; j++) {
					if(i == j) 
						continue;
					double x_distance = nodesPosition.get(allNodes.get(i).getDbId()).x - nodesPosition.get(allNodes.get(j).getDbId()).x;
					double y_distance = nodesPosition.get(allNodes.get(i).getDbId()).y - nodesPosition.get(allNodes.get(j).getDbId()).y;
					double distance = Math.sqrt(Math.pow(x_distance,2) + Math.pow(y_distance, 2));
					netForce.add(COULOMB_CONSTANT * allNodes.get(i).getPathwayNodeRadius() * allNodes.get(j).getPathwayNodeRadius() * x_distance/ Math.pow(distance, 3),
							COULOMB_CONSTANT * allNodes.get(i).getPathwayNodeRadius() * allNodes.get(j).getPathwayNodeRadius() * y_distance/ Math.pow(distance, 3));
				}
				for (int j = 0; j < allEdges.size(); j ++) {
					if(allEdges.get(j).getNode1() == allNodes.get(i).getDbId() || allEdges.get(j).getNode2() == allNodes.get(i).getDbId()) {
						double x_distance, y_distance = 0;
						if(allEdges.get(j).getNode1() == allNodes.get(i).getDbId()){
							x_distance = nodesPosition.get(allEdges.get(j).getNode1()).x - nodesPosition.get(allEdges.get(j).getNode2()).x;
							y_distance = nodesPosition.get(allEdges.get(j).getNode1()).y - nodesPosition.get(allEdges.get(j).getNode2()).y;
						} else {
							x_distance = nodesPosition.get(allEdges.get(j).getNode2()).x - nodesPosition.get(allEdges.get(j).getNode1()).x;
							y_distance = nodesPosition.get(allEdges.get(j).getNode2()).y - nodesPosition.get(allEdges.get(j).getNode1()).y;
						}
						netForce.sub(Math.sqrt(allEdges.get(j).getEdgeSimilarity()) * x_distance / 100, Math.sqrt(allEdges.get(j).getEdgeSimilarity()) * y_distance / 100);
					}
				}

				nodesVelocity.get(allNodes.get(i).getDbId()).add(netForce);
				nodesVelocity.get(allNodes.get(i).getDbId()).mult(DAMPING_CONSTANT);
				nodesPosition.get(allNodes.get(i).getDbId()).add(nodesVelocity.get(allNodes.get(i).getDbId()));

			}
			interval ++;
		} 
		
		for(int i = 0; i < allNodes.size() ; i ++) {
			CanvasNode canvasnode;
			if(this.colorDetails != null) {
				 canvasnode = new CanvasNode(nodesPosition.get(allNodes.get(i).getDbId()), allNodes.get(i).getPathwayNodeRadius(), colorDetails.get((int)allNodes.get(i).getDbId()));
			}
			else {
				canvasnode = new CanvasNode(nodesPosition.get(allNodes.get(i).getDbId()), allNodes.get(i).getPathwayNodeRadius());
			}
				
			nodesToPlot.add(canvasnode);
		}
		return nodesToPlot;
	}

	@Override
	public List<CanvasText> calculateTextPosition(Context2d context) {
		// TODO Auto-generated method stub
		for(int i = 0; i < allNodes.size() ; i ++) {
			Vector textCoOrdinates = new Vector();
			Vector nodeCoOrdinates = nodesPosition.get(allNodes.get(i).getDbId());
			double radius = allNodes.get(i).getPathwayNodeRadius();
			textCoOrdinates.set(nodeCoOrdinates);
			double textWidth = context.measureText(allNodes.get(i).getPathwayName()).getWidth();
			if(nodeCoOrdinates.x < midpoint.x)
				textCoOrdinates.sub(textWidth+radius, 0);
			else
				textCoOrdinates.add(radius,0); 
			CanvasText text = new CanvasText(allNodes.get(i).getPathwayName(), textCoOrdinates);
			textToPlot.add(text);
		}
		return textToPlot;
	}

	@Override
	public List<CanvasEdge> calculateEdgePosition() {
		// TODO Auto-generated method stub
		for(int i = 0; i < allEdges.size() ;  i ++) {
			Vector node1coOrdinates = nodesPosition.get(allEdges.get(i).getNode1());
			Vector node2coOrdinates = nodesPosition.get(allEdges.get(i).getNode2());
			double lineWidth = Math.sqrt(allEdges.get(i).getEdgeSimilarity());
			CanvasEdge canvasedge = new CanvasEdge(node1coOrdinates, node2coOrdinates, lineWidth);
			edgesToPlot.add(canvasedge);
		}
		return edgesToPlot;
	}

}
