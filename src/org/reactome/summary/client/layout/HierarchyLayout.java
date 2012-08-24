package org.reactome.summary.client.layout;

import java.util.ArrayList;
import java.util.List;

import org.reactome.summary.client.Vector;
import org.reactome.summary.client.view.CanvasEdge;
import org.reactome.summary.client.view.CanvasNode;
import org.reactome.summary.client.view.CanvasText;
import org.reactome.summary.shared.ConnectingEdge;
import org.reactome.summary.shared.GraphNode;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.user.client.Window;
/**
 * Google Summer of Code 2012 Project
 * Reactome Pathway Summary Visualization
 *
 */
/**
 * Renders the top level pathways as circular nodes, and are connected to other pathways with connecting edges,
 * whose width is directly proportional to the similarity measure between the two pathways.
 * Renders first-level child pathways for each top level pathway and 
 * each group of child pathways is connected to its parent pathway by connecting edges of constant width.
 * The nodes are arranged in a radial treemap layout with top-level pathways as first layer 
 * and child pathways linked to top level pathways as second layer.
 * 
 * @author maulik
 *
 */
public class HierarchyLayout extends Layout{

	int innerCircularLayoutRadius = (int) (0.30* Window.getClientHeight()/2)-50;
	int outerCircularLayoutRadius = (int) (0.70* Window.getClientHeight()/2)-50;
	List<GraphNode> childNodes;
	
	public HierarchyLayout(List<GraphNode> nodes, List<ConnectingEdge> edges,
			Vector canvasMidPoint) {
		super(nodes, edges, canvasMidPoint);
		childNodes = new ArrayList<GraphNode>();
	}

	@Override
	public List<CanvasNode> calculateNodePosition() {
		// TODO Auto-generated method stub
		
		for(int i = 0; i < allNodes.size() ; i ++) {
			double position = 2*Math.PI*i/allNodes.size();
			Vector coOrdinates = new Vector();
			coOrdinates.set(midpoint);
			coOrdinates.add(innerCircularLayoutRadius*Math.cos(position), innerCircularLayoutRadius*Math.sin(position));
			
			CanvasNode canvasnode;
			if(this.colorDetails != null) {
				canvasnode = new CanvasNode(coOrdinates, 5, colorDetails.get((int)allNodes.get(i).getDbId()));
			} else {
				canvasnode = new CanvasNode(coOrdinates, 5);
			}
			nodesToPlot.add(canvasnode);
			nodesPosition.put(allNodes.get(i).getDbId(), coOrdinates);
		}
		
		for(int i = 0; i < allNodes.size() ; i ++){
			for(int j = 0; j < allNodes.get(i).getChildNodes().size(); j ++){
				if(nodesPosition.containsKey(allNodes.get(i).getChildNodes().get(j).getDbId()))
					continue;
				nodesPosition.put(allNodes.get(i).getChildNodes().get(j).getDbId(), new Vector(0,0));
				childNodes.add(allNodes.get(i).getChildNodes().get(j));
			}
		}
		
		for(int i = 0; i < childNodes.size() ; i ++) {
			double position = 2*Math.PI*i/childNodes.size();
			Vector coOrdinates = new Vector();
			coOrdinates.set(midpoint);
			coOrdinates.add(outerCircularLayoutRadius*Math.cos(position), outerCircularLayoutRadius*Math.sin(position));
			
			CanvasNode canvasnode;
			if(this.colorDetails != null) {
				canvasnode = new CanvasNode(coOrdinates, 3, colorDetails.get((int)childNodes.get(i).getDbId()));
			} else {
				canvasnode = new CanvasNode(coOrdinates, 3);
			}
			nodesToPlot.add(canvasnode);
			nodesPosition.put(childNodes.get(i).getDbId(), coOrdinates);
		}
		return nodesToPlot;
	}

	@Override
	public List<CanvasText> calculateTextPosition(Context2d context) {
		// TODO Auto-generated method stub
		for(int i = 0; i < allNodes.size() ; i ++) {
			Vector nodeCoOrdinates = nodesPosition.get(allNodes.get(i).getDbId());
			CanvasText text = new CanvasText(allNodes.get(i).getPathwayName(), nodeCoOrdinates);
			text.setCanvasMidPoint(midpoint);
			text.setAngle(2*Math.PI*i/allNodes.size());
			textToPlot.add(text);
		}
		
		for(int i = 0; i < childNodes.size() ; i ++) {
			Vector nodeCoOrdinates = nodesPosition.get(childNodes.get(i).getDbId());
			CanvasText text = new CanvasText(childNodes.get(i).getPathwayName(), nodeCoOrdinates);
			text.setCanvasMidPoint(midpoint);
			text.setAngle(2*Math.PI*i/childNodes.size());
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
		
		for(int i =0; i < allNodes.size() ; i++){
			Vector node1coOrdinates = nodesPosition.get(allNodes.get(i).getDbId());
			for(int j =0 ; j < allNodes.get(i).getChildNodes().size(); j++){
				Vector node2coOrdinates = nodesPosition.get(allNodes.get(i).getChildNodes().get(j).getDbId());
				double lineWidth = 1;
				CanvasEdge canvasedge = new CanvasEdge(node1coOrdinates, node2coOrdinates, lineWidth);
				canvasedge.setType("subEdge");
				edgesToPlot.add(canvasedge);
			}
		}
		return edgesToPlot;
	}

}
