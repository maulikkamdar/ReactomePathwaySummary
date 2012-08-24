package org.reactome.summary.client;

import org.reactome.summary.client.view.SummaryPanel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
/**
 * Google Summer of Code 2012 Project
 * Reactome Pathway Summary Visualization
 *
 */
/**
 * Entrypoint class for Reactome Pathway Summary Visualization Browser
 * @author maulik
 *
 */
public class ReactomePathwaySummary implements EntryPoint {

	private final QueryServiceAsync queryService = GWT
			.create(QueryService.class);
	
    SummaryPanel controls;
    AppController controller;
    static int refreshRate = 100;
	
	public void onModuleLoad() {
		
	    controls = new SummaryPanel();
	    controls.setSize((int) (0.95* Window.getClientWidth()), (int) (0.97 * Window.getClientHeight()));
	    RootPanel.get().add(controls);
		
		final AppController controller = new AppController(queryService, controls);
	}	
	
	
}
