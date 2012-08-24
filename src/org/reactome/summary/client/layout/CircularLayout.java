package org.reactome.summary.client.layout;

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
 * Renders the top level pathways as circular nodes, 
 * whose radius is directly proportional to the number of the participating molecules in the pathways,
 * and are connected to other pathways with connecting edges,
 * whose width is directly proportional to the similarity measure between the two pathways.
 * The nodes are arranged in a circular layout, as spokes on the radius of the circle.
 * 
 * @author maulik
 *
 */
public class CircularLayout extends Layout{

	int circularLayoutRadius = (int) (0.85* Window.getClientHeight()/2)-50;
	
	public CircularLayout(List<GraphNode> nodes, List<ConnectingEdge> edges, Vector midpoint) {
		super(nodes,edges,midpoint);
	}
	
	@Override
	public List<CanvasNode> calculateNodePosition() {
		// TODO Auto-generated method stub
		for(int i = 0; i < allNodes.size() ; i ++) {
			double position = 2*Math.PI*i/allNodes.size();
			Vector coOrdinates = new Vector();
			coOrdinates.set(midpoint);
			coOrdinates.add(circularLayoutRadius*Math.cos(position), circularLayoutRadius*Math.sin(position));
			
			CanvasNode canvasnode;
			if(this.colorDetails != null) {
				canvasnode = new CanvasNode(coOrdinates, allNodes.get(i).getPathwayNodeRadius(), colorDetails.get((int)allNodes.get(i).getDbId()));
			} else {
				canvasnode = new CanvasNode(coOrdinates, allNodes.get(i).getPathwayNodeRadius());
			}
			nodesToPlot.add(canvasnode);
			nodesPosition.put(allNodes.get(i).getDbId(), coOrdinates);
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
	//		System.out.println(allNodes.get(i).getPathwayName() + " " + textCoOrdinates.x+ " " + textCoOrdinates.y);
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
