package org.reactome.summary.client.view;

import org.reactome.summary.client.Vector;

import com.google.gwt.canvas.dom.client.Context2d;
/**
 * Google Summer of Code 2012 Project
 * Reactome Pathway Summary Visualization
 *
 */
/**
 * Canvas Object to render all the connecting edges as straight lines. 
 * @author maulik
 *
 */
public class CanvasEdge extends CanvasObject{
	Vector point1;
	Vector point2;
	double linewidth;
	String type;
	
	/**
	 * Constructor
	 * @param point1 Vector representing the start point of the line
	 * @param point2 Vector representing the end point of the line
	 * @param lineWidth The width of the line
	 */
	public CanvasEdge(Vector point1, Vector point2, double lineWidth) {
		this.point1 = point1;
		this.point2 = point2;
		this.linewidth = lineWidth;
	}
	
	/**
	 * Renders the connecting edge as a line, depending on whether it exceeds the threshold value set
	 * The color of the connecting edge depends on the type of the connecting edge
	 * @param context Context2d object for rendering the line
	 * @param thresholdValue The threshold value for rendering the line
	 */
	public void plotLine(Context2d context, int thresholdValue) {
		if(type != null){
			String bgColor = "rgba(204,255,204,1)";
			String strokeColor = "rgba(176,176,176,1)";
			makeColor(context, bgColor, strokeColor);
			context.setLineWidth(linewidth);
			context.beginPath();
			context.moveTo(point1.x, point1.y);
			context.lineTo(point2.x, point2.y);
			context.stroke();
			context.closePath();
		} else {
			String bgColor = "rgba(204,255,204,1)";
			String strokeColor = "rgba(0,0,0,1)";
			makeColor(context, bgColor, strokeColor);
			if(linewidth > Math.sqrt(thresholdValue)) {
				context.setLineWidth(linewidth);
				context.beginPath();
				context.moveTo(point1.x, point1.y);
				context.lineTo(point2.x, point2.y);
				context.stroke();
				context.closePath();
			}
		}
	}

	/**
	 * Sets the type of the connecting edge, whether it is an edge connecting two top-level pathways
	 * or an edge connecting a parent pathway to child pathway (hierarchical layout)
	 * @param string type of connecting edge
	 */
	public void setType(String string) {
		// TODO Auto-generated method stub
		this.type = string;
	}
}
