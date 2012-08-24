package org.reactome.summary.client.view;

import java.util.HashMap;
import java.util.List;

import org.reactome.summary.client.Vector;
import org.reactome.summary.client.layout.*;
import org.reactome.summary.shared.ConnectingEdge;
import org.reactome.summary.shared.GraphNode;
import org.reactome.summary.shared.PathwaySummary;

import com.google.gwt.canvas.dom.client.Context2d;
/**
 * Google Summer of Code 2012 Project
 * Reactome Pathway Summary Visualization
 *
 */
/**
 * Special class extending the PlugInSupportCanvas to visualize the PathwaySummary instance, depending on user defined layout.
 * @author maulik
 *
 */
public class SummaryCanvas extends PlugInSupportCanvas{
	
	HashMap<Integer,String> colorDetails;
	int layoutOption;
	SummaryPanel controls;
	Context2d context;
	
	/**
	 * Constructor
	 * @param controls The Main Interface Panel
	 */
	public SummaryCanvas(SummaryPanel controls) {
		this.controls = controls;
		context = getContext2d();
	}
	
	/**
	 * Set the color of the nodes, whenever expression analysis is carried out
	 * @param colorDetails Expression Levels mapped to a color Scale
	 */
	public void setExpression(HashMap<Integer,String> colorDetails) {
		this.colorDetails = colorDetails;
	}
	
	/**
	 * Set the user-selected layout
	 * @param layout Layout option
	 */
	public void setLayout(int layout) {
		this.layoutOption = layout;
	}
	
	/**
	 * Calculates the nodes and edges position depending on the layout and renders them on the canvas
	 * @param layout User Defined Layout
	 */
	private void render(Layout layout){
		context.clearRect(0, 0, this.getCoordinateSpaceWidth(), this.getCoordinateSpaceHeight());
		List<CanvasNode> canvasNodes = layout.calculateNodePosition();
		List<CanvasEdge> canvasEdges = layout.calculateEdgePosition();
		List<CanvasText> canvasTextLines = layout.calculateTextPosition(context);
		
		for (int i = 0 ; i < canvasEdges.size() ; i ++){
			canvasEdges.get(i).plotLine(context, controls.getThresholdValue());
		} 
		for (int i = 0 ; i < canvasNodes.size() ; i ++){
			canvasNodes.get(i).plotNode(context);
		}
		for (int i = 0 ; i < canvasTextLines.size() ; i ++){
			canvasTextLines.get(i).plotText(context);
		}
	}

	/**
	 * Renders the PathwaySummary using the user-defined layout (when layout is not user defined, circular layout is default)
	 * @param summary PathwaySummary instance to be rendered
	 */
	public void renderSummary(PathwaySummary summary) {
		// TODO Auto-generated method stub
		if(summary == null) {
			System.out.println("nothing to do here");
			return;
		}
		
		List<ConnectingEdge> edges = summary.getEdges();
		List<GraphNode> nodes = summary.getNodes();
		Vector mid = new Vector(this.getCoordinateSpaceWidth()/2, this.getCoordinateSpaceHeight()/2);
		
		Layout layout;
		switch(layoutOption){
		case 0 : layout = new CircularLayout(nodes,edges,mid);
			break;
		case 1 : layout = new HierarchyLayout(nodes,edges,mid);
			break;
		case 2 : layout = new ForceLayout(nodes,edges,mid,100);
			break;
		case 3 : layout = new CirclePackedLayout(nodes,edges,mid);
			break;
		default : layout = new CircularLayout(nodes,edges,mid);
		}
		if(colorDetails != null) {
			layout.setColorDetails(colorDetails);
		}
		render(layout);
	}

}
