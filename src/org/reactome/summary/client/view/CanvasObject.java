package org.reactome.summary.client.view;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
/**
 * Google Summer of Code 2012 Project
 * Reactome Pathway Summary Visualization
 *
 */
/**
 * @author maulik
 *
 */
public class CanvasObject {

	/**
	 * Color mixer depending on the input stroke and background color. Used by all canvas objects.
	 * @param context Context2d object used to render the canvas object
	 * @param bgColor Background Color value
	 * @param strokeColor Stroke Color value
	 */
	public void makeColor(Context2d context, String bgColor, String strokeColor) {
		CssColor strokeStyleColor = CssColor.make(strokeColor);
        context.setStrokeStyle(strokeStyleColor);
        CssColor fillStyleColor = CssColor.make(bgColor);
        context.setFillStyle(fillStyleColor);
	}
}
