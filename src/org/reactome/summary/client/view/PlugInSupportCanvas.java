package org.reactome.summary.client.view;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.FocusWidget;

/**
 * A test canvas class so that it can work with any canvas plug-in. Basically this is
 * copied from the original GWT canvas class implementation with some modifications.
 * @author wug
 *
 */
public class PlugInSupportCanvas extends FocusWidget {
	private static int canvasId;
	
	public PlugInSupportCanvas() {
		// Need to check plug-in first
		if (isFlashCanvasPluginInstalled()) {
			CanvasElement element = (CanvasElement) createFlashCanvasElement();
			setElement(element);
			element.setId("canvas" + canvasId ++);
		}
		else if (Canvas.isSupported()) {
			CanvasElement element = Document.get().createCanvasElement();
		    setElement(element);
		}
		else {
			GWT.log("Canvas is not supported!");
			throw new IllegalStateException("Canvas is not supported!");
		}
	}	
	
	private native boolean isFlashCanvasPluginInstalled() /*-{
      return (typeof $wnd.FlashCanvas != 'undefined');
    }-*/;
	
	private native Element createFlashCanvasElement() /*-{
		var canvas = $doc.createElement("canvas");
    	$wnd.FlashCanvas.initElement(canvas);
    	return canvas;
	}-*/;

	/**
	 * Returns the attached Canvas Element.
	 * 
	 * @return the Canvas Element
	 */
	public CanvasElement getCanvasElement() {
		return this.getElement().cast();
	}

	/**
	 * Gets the rendering context that may be used to draw on this canvas.
	 * 
	 * @param contextId the context id as a String
	 * @return the canvas rendering context
	 */
	public Context getContext(String contextId) {
		return getCanvasElement().getContext(contextId);
	}

	/**
	 * Returns a 2D rendering context.
	 * 
	 * This is a convenience method, see {@link #getContext(String)}.
	 * 
	 * @return a 2D canvas rendering context
	 */
	public Context2d getContext2d() {
		return getCanvasElement().getContext2d();
	}

	/**
	 * Gets the height of the internal canvas coordinate space.
	 * 
	 * @return the height, in pixels
	 * @see #setCoordinateSpaceHeight(int)
	 */
	public int getCoordinateSpaceHeight() {
		return getCanvasElement().getHeight();
	}

	/**
	 * Gets the width of the internal canvas coordinate space.
	 * 
	 * @return the width, in pixels
	 * @see #setCoordinateSpaceWidth(int)
	 */
	public int getCoordinateSpaceWidth() {
		return getCanvasElement().getWidth();
	}

	/**
	 * Sets the height of the internal canvas coordinate space.
	 * 
	 * @param height the height, in pixels
	 * @see #getCoordinateSpaceHeight()
	 */
	public void setCoordinateSpaceHeight(int height) {
		getCanvasElement().setHeight(height);
	}

	/**
	 * Sets the width of the internal canvas coordinate space.
	 * 
	 * @param width the width, in pixels
	 * @see #getCoordinateSpaceWidth()
	 */
	public void setCoordinateSpaceWidth(int width) {
		getCanvasElement().setWidth(width);
	}

	/**
	 * Returns a data URL for the current content of the canvas element.
	 * 
	 * @return a data URL for the current content of this element.
	 */
	public String toDataUrl() {
		return getCanvasElement().toDataUrl();
	}

	/**
	 * Returns a data URL for the current content of the canvas element, with a
	 * specified type.
	 * 
	 * @param type the type of the data url, e.g., image/jpeg or image/png.
	 * @return a data URL for the current content of this element with the
	 *         specified type.
	 */
	public String toDataUrl(String type) {
		return getCanvasElement().toDataUrl(type);
	}
}
