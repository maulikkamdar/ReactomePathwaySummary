package org.reactome.summary.client.view;

import org.reactome.summary.client.Vector;

import com.google.gwt.canvas.dom.client.Context2d;
/**
 * Google Summer of Code 2012 Project
 * Reactome Pathway Summary Visualization
 *
 */
/**
 * Canvas Object to render all pathway names as text
 * @author maulik
 *
 */
public class CanvasText extends CanvasObject{

	String pathwayName;
	Vector startPoint;
	double angle;
	Vector canvasMidPoint;
	
	/**
	 * Constructor
	 * @param pathwayName Text to be rendered
	 * @param point Vector representing the start point of the text to be rendered
	 */
	public CanvasText(String pathwayName, Vector point) {
		// TODO Auto-generated constructor stub
		this.pathwayName = pathwayName;
		this.startPoint = point;
	}
	
	/**
	 * Renders the text on the canvas depending on whether the angle for the text is set.
	 * @param context Context2d object for rendering the text
	 */
	public void plotText(Context2d context) {
		String bgColor = "rgba(0,0,0,1)";
		String strokeColor = "rgba(0,0,0,1)";
		context.setFont("12 px");
		context.setTextBaseline("middle");
		makeColor(context, bgColor, strokeColor);
		if(angle != 0.0){
			context.save();			
			if(angle > Math.PI/2 && angle < 3*Math.PI/2) {
				context.translate(canvasMidPoint.x, canvasMidPoint.y);
				context.rotate(angle-Math.PI);
				context.translate(-Math.sqrt(Math.pow((startPoint.x-canvasMidPoint.x),2)+Math.pow((startPoint.y-canvasMidPoint.y), 2)), 0);
				context.translate(-context.measureText(pathwayName).getWidth(), 0);
				context.fillText(pathwayName, 0, 0);
			}
			else {
				context.translate(canvasMidPoint.x, canvasMidPoint.y);
				context.rotate(angle);
				context.translate(Math.sqrt(Math.pow((startPoint.x-canvasMidPoint.x),2)+Math.pow((startPoint.y-canvasMidPoint.y), 2)), 0);
				context.fillText(pathwayName, 0, 0);
			}
			context.restore();
		} else {
			context.fillText(pathwayName, startPoint.x, startPoint.y);
		}
	}

	/**
	 * Sets the angle of the text (hierarchical layout)
	 * @param angle Angle in radians
	 */
	public void setAngle(double angle){
		this.angle = angle;
	}
	
	/**
	 * Sets the midpoint of the canvas (hierarchical Layout)
	 * @param midpoint Vector representing midpoint of the canvas
	 */
	public void setCanvasMidPoint(Vector midpoint){
		this.canvasMidPoint = midpoint;
	}
}
