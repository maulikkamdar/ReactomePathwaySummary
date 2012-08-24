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
 * Similar to the circular layout, but the top-level pathway nodes are rendered with fixed radius.
 * The child nodes are represented as smaller secondary circles. 
 * The distance of child nodes from the center of the top-level pathway nodes 
 * is indicative of the total pathway participants in the top-level pathways.
 * 
 * @author maulik
 *
 */
public class CirclePackedLayout extends Layout{

	int circularLayoutRadius = (int) (0.85* Window.getClientHeight()/2)-50;
	
	public CirclePackedLayout(List<GraphNode> nodes, List<ConnectingEdge> edges, Vector midpoint) {
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
				canvasnode = new CanvasNode(coOrdinates, 35, colorDetails.get((int)allNodes.get(i).getDbId()));
			} else {
				canvasnode = new CanvasNode(coOrdinates, 35);
			}
			nodesToPlot.add(canvasnode);
			nodesPosition.put(allNodes.get(i).getDbId(), coOrdinates);
			
			List<GraphNode> childNodes = allNodes.get(i).getChildNodes();
			for(int j = 0; j < childNodes.size(); j ++){
				double childNodePosition = 2*Math.PI*j/childNodes.size();
				Vector childcoOrdinates = new Vector();
				childcoOrdinates.set(coOrdinates);
				childcoOrdinates.add(allNodes.get(i).getPathwayNodeRadius()*Math.cos(childNodePosition),allNodes.get(i).getPathwayNodeRadius()*Math.sin(childNodePosition));
				CanvasNode childCanvasNode;
				if(this.colorDetails != null){
					childCanvasNode = new CanvasNode(childcoOrdinates,5, colorDetails.get((int)childNodes.get(j).getDbId()));
				} else {
					childCanvasNode = new CanvasNode(childcoOrdinates, 5);
				}
				nodesToPlot.add(childCanvasNode);
				nodesPosition.put(childNodes.get(j).getDbId(), childcoOrdinates);
			}
			
		}
		return nodesToPlot;
	}

	@Override
	public List<CanvasText> calculateTextPosition(Context2d context) {
		// TODO Auto-generated method stub
		for(int i = 0; i < allNodes.size() ; i ++) {
			Vector textCoOrdinates = new Vector();
			Vector nodeCoOrdinates = nodesPosition.get(allNodes.get(i).getDbId());
			double radius = 35;
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
