package org.reactome.summary.client.layout;

import org.reactome.summary.client.Vector;
import org.reactome.summary.client.view.CanvasEdge;
import org.reactome.summary.client.view.CanvasNode;
import org.reactome.summary.client.view.CanvasText;
import org.reactome.summary.shared.ConnectingEdge;
import org.reactome.summary.shared.GraphNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.google.gwt.canvas.dom.client.Context2d;
/**
 * Google Summer of Code 2012 Project
 * Reactome Pathway Summary Visualization
 *
 */
/**
 * Abstract Layout Class
 * @author maulik
 *
 */
public abstract class Layout {
	List<GraphNode> allNodes;
	List<ConnectingEdge> allEdges;
	Vector midpoint;
	HashMap<Integer,String> colorDetails;
	protected List<CanvasNode> nodesToPlot = new ArrayList<CanvasNode>();
	protected List<CanvasEdge> edgesToPlot = new ArrayList<CanvasEdge>();
	protected List<CanvasText> textToPlot = new ArrayList<CanvasText>();
	protected HashMap<Integer, Vector> nodesPosition = new HashMap<Integer, Vector>();
	
	public Layout(List<GraphNode> nodes, List<ConnectingEdge> edges, Vector canvasMidPoint) {
		this.allEdges = edges;
		this.allNodes = nodes;
		this.midpoint = canvasMidPoint;
	}
	
	/**
	 * Set the color details of the nodes if expression analysis is carried out
	 * @param colorDetails Expression Levels mapped to the color scale
	 */
	public void setColorDetails(HashMap<Integer,String> colorDetails){
		this.colorDetails = colorDetails;
	}
	
	/**
	 * Calculate the position of all the nodes depending on the layout.
	 * @return an array containing the vector positions of the nodes and their names
	 */
	abstract public List<CanvasNode> calculateNodePosition();
	
	/**
	 * Calculate the position of all text attached to the nodes, depending on the layout.
	 * @param context The context used to measure the text width
	 * @return an array containing the vector positions, and the string of the text to be rendered.
	 */
	abstract public List<CanvasText> calculateTextPosition(Context2d context);
	
	/**
	 * Calculate the start and the end positions of the edges to be rendered as lines
	 * @return an array containing the vector start and end positions of the edge and its thickness.
	 */
	abstract public List<CanvasEdge> calculateEdgePosition();
	
}
