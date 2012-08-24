package org.reactome.summary.client.view;

import org.reactome.summary.client.Color;
import org.reactome.summary.client.ExpressionColorPicker;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
/**
 * Google Summer of Code 2012 Project
 * Reactome Pathway Summary Visualization
 *
 */
/**
 * Special implementation of the PlugInSupportCanvas to render the expression scale as a gradient.
 * Used as a legend for users to map color values back to expression level.
 * @author maulik
 *
 */
public class GradientCanvas extends PlugInSupportCanvas{
	
	double vLow, vHigh;
	public GradientCanvas(double vLow, double vHigh) {
		this.vLow = vLow;
		this.vHigh = vHigh;
	}
	
	public void addColorMaps(double min, double max) {
		Context2d context = getContext2d();
		double interval = (max-min)/(vHigh-vLow);
		for(int i = 0; i < vHigh ; i++) {
			double level = min + i*interval;
	        ExpressionColorPicker expressionColorPicker = new ExpressionColorPicker(min, max);
	        Color foregroundColor = expressionColorPicker.pickForegroundColor(level);
	        String foregroundColorString = foregroundColor.getHexValue();
	        CssColor fillStyleColor = CssColor.make(foregroundColorString);
	        context.setFillStyle(fillStyleColor);
	        context.fillRect(i, 0, 1, 20);
	        context.fill();
		}
		
	}
}
