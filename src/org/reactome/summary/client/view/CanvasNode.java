package org.reactome.summary.client.view;

import org.reactome.summary.client.Vector;

import com.google.gwt.canvas.dom.client.Context2d;
/**
 * Google Summer of Code 2012 Project
 * Reactome Pathway Summary Visualization
 *
 */
/**
 * Canvas Object to render all the graph nodes as circles
 * @author maulik
 *
 */
public class CanvasNode extends CanvasObject{

	Vector center;
	double radius;
	String bgColor;
	
	/**
	 * Constructor when expression analysis is carried out
	 * @param coOrdinates Vector representing the center of the circle
	 * @param pathwayNodeRadius The radius of the circle
	 * @param color Expression Level mapped as color string
	 */
	public CanvasNode(Vector coOrdinates, double pathwayNodeRadius, String color) {
		// TODO Auto-generated constructor stub
		this.center = coOrdinates;
		this.radius = pathwayNodeRadius;
		this.bgColor = color;
	}
	
	/**
	 * Default constructor
	 * @param coOrdinates Vector representing the center of the circle
	 * @param pathwayNodeRadius The radius of the circle
	 */
	public CanvasNode(Vector coOrdinates, double pathwayNodeRadius) {
		// TODO Auto-generated constructor stub
		this.center = coOrdinates;
		this.radius = pathwayNodeRadius;
		this.bgColor = "rgba(0,191,255,1)";
	}
	
	/**
	 * Renders the graph node as circle
	 * @param context Context2d object for rendering the circle
	 */
	public void plotNode(Context2d context) {
		String strokeColor = "rgba(0,0,0,1)";
		makeColor(context, bgColor, strokeColor);
		context.setLineWidth(1);
		context.beginPath();
	    context.arc(center.x, center.y, this.radius, 0, 2*Math.PI);
    	context.fill();
    	context.stroke();
    	context.closePath();
	}

}
