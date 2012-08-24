package org.reactome.summary.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.reactome.summary.client.view.SummaryPanel;
import org.reactome.summary.shared.ExpressionData;
import org.reactome.summary.shared.PathwaySummary;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.kiouri.sliderbar.client.event.BarValueChangedEvent;
import com.kiouri.sliderbar.client.event.BarValueChangedHandler;
/**
 * Google Summer of Code 2012 Project
 * Reactome Pathway Summary Visualization
 * @TODO Create a SelectionEvent class and shift all handler related methods to it.
 *
 */
/**
 * Queries the Server via Remote Procedure Calls for Pathway Summary Data.
 * Parses the Pathway Summary Data and Expression Analysis Data into renderable format.
 * Implements Click Handlers on User Interface Elements.
 * Methods to interpret various Click Events (like Change of Layout, Slider Bar etc. 
 * @author maulik
 *
 */

public class AppController {
	
	QueryServiceAsync rpcService;
	SummaryPanel mainPanelControls;
	String xmlResponse;
    ExpressionRenderer expression;
    String analysisId;
    PathwaySummary summary;
    
    /**
     * Constructor for the AppController Class
     * @param rpcService The Remote Procedure Service
     * @param controls The Main Interface Panel
     */
	public AppController(QueryServiceAsync rpcService, SummaryPanel controls) {
		this.mainPanelControls = controls;
		this.rpcService = rpcService;
		init();
		addControlHandlers();
	}
	
	/**
	 * Primary Method to query the server to make GET Request against Reactome RESTful API 
	 * Implements getSummary Remote Procedure Call
	 */
	private void init() {
			rpcService.getSummary(new AsyncCallback<PathwaySummary>(){
				@Override
				public void onFailure(Throwable caught) {
					requestFailed(caught);	
				}
				@Override
				public void onSuccess(PathwaySummary result) {
					renderSummary(result);
				}				
			});
		 
	}
	
	/**
	 * Generates a maximum threshold value depending on the width of the connecting edges
	 * Links the PathwaySummary instance to the Main Interface
	 * @param summary PathwaySummary instance retrieved from the server
	 */
	protected void renderSummary(PathwaySummary summary){
		this.summary = summary;
		double maxThresholdValue = this.summary.generateThreshold();
		mainPanelControls.updateSlider(maxThresholdValue);	
		mainPanelControls.renderSummary(summary);
	}
	
	/**
	 * Request Failed Exception to be executed when Client-Server communication or REST requests fail
	 * @param exception Exception thrown
	 */
	protected void requestFailed(Throwable exception) {
        Window.alert("Problems detected with the Summary " + exception.getMessage());
    } 
	
	/**
	 * Render the PathwaySummary instance with Expression Data mapped to a Color Scale
	 * @param colorDetails Expression Data Mapped to a Color Scale
	 */
	public void renderWithExpression(HashMap<Integer,String> colorDetails) {
		mainPanelControls.renderWithExpression(colorDetails);
		mainPanelControls.renderSummary(summary);
	}
	
	/**
	 * Render the Pathway Summary instance with the new layout selected 
	 * @param layoutOption Selected layout
	 */
	public void changeLayout(int layoutOption){
		mainPanelControls.changeLayout(layoutOption);
		mainPanelControls.renderSummary(summary);
	}
	
	/**
	 * Parses the expression data obtained by POST request to the servlet using RESULTS parameter
	 * @param jObject The Response obtained in JSON Format
	 */
	public void parseExpression(JSONObject jObject){
			if(summary == null)
				return;
			ArrayList<ExpressionData> expDetails = new ArrayList<ExpressionData>();
			try {
				JSONArray expressionValues = jObject.get("rows").isArray();
				for(int j = 0; j < expressionValues.size(); j++){
					if(expressionValues.get(j).isArray().get(1).isObject().get("value").isObject().get("DB_ID").toString().matches("48887")){
						int pathwayId = Integer.parseInt(expressionValues.get(j).isArray().get(0).isObject().get("value").isObject().get("DB_ID").toString());
						for(int i = 0; i < summary.getAllDbIds().size() ; i ++){
							if(pathwayId  < summary.getAllDbIds().get(i))
								break;
							if(pathwayId == summary.getAllDbIds().get(i)){
								ExpressionData expressionValue = new ExpressionData(pathwayId);
								expressionValue.setExpressionLevels(
										expressionValues.get(j).isArray().get(3).isObject().get("value").isObject().get("level").toString(),
										expressionValues.get(j).isArray().get(4).isObject().get("value").isObject().get("level").toString(),
										expressionValues.get(j).isArray().get(5).isObject().get("value").isObject().get("level").toString(),
										expressionValues.get(j).isArray().get(6).isObject().get("value").isObject().get("level").toString(),
										expressionValues.get(j).isArray().get(7).isObject().get("value").isObject().get("level").toString());
								expDetails.add(expressionValue);
								break;
							}
						}
					}
				}
			} catch (Exception e){
				requestFailed(e);
			}
			expression = new ExpressionRenderer(expDetails);
			expression.setLimits();
			List<Double> limits = expression.getLimits();
			mainPanelControls.getExpressionPanel().getMinLabel().setText("" + limits.get(0));
			mainPanelControls.getExpressionPanel().getMaxLabel().setText("" + limits.get(1));
			mainPanelControls.getExpressionPanel().getExpressionScale().addColorMaps(limits.get(0), limits.get(1));
			mainPanelControls.getExpressionPanel().setVisible(true);
	}
	
	/**
	 * Method to implement Click Handlers on various User Interface Elements
	 * @TODO Needs to be moved to independent Class
	 */
	private void addControlHandlers() {
		mainPanelControls.getExpressionPanelButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				mainPanelControls.getExpressionPopup().center();
			}
		});
		
		mainPanelControls.getExpressionPopup().getExampleButton().addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				rpcService.getExampleDataSet(new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						mainPanelControls.getExpressionPopup().enableAllButtons();
						requestFailed(caught);
					}

					@Override
					public void onSuccess(String result) {
						// TODO Auto-generated method stub
						mainPanelControls.getExpressionPopup().enableAllButtons();
						mainPanelControls.getExpressionPopup().getPastedDataTextArea().setText(result);
					}
				});
			}			
		});
		
		mainPanelControls.getExpressionPopup().getClearButton().addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				mainPanelControls.getExpressionPopup().getPastedDataTextArea().setText("");
			}
			
		});
		
		mainPanelControls.getExpressionPopup().getCloseButton().addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				mainPanelControls.getExpressionPopup().hide();
			}
			
		});
		
		mainPanelControls.getExpressionPopup().getAnalysisButton().addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				mainPanelControls.getExpressionPopup().getForm().submit();
			}
			
		});
		
		mainPanelControls.getExpressionPopup().getForm().addSubmitCompleteHandler(new SubmitCompleteHandler(){

			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				// TODO Auto-generated method stub
				if (event == null) {
					Window.alert("onSubmitComplete: event is null!!");
					return;
				}		
				String results = event.getResults();
				if (results != null) {
					results = results.replaceAll("</*[pP][rR][eE][^>]*>", "");
					if (!results.isEmpty()){
						results = results.trim();
						results = results.replaceAll("^\"", "");
						results = results.replaceAll("\"$", "");
					}
				}
				analysisId = results;
				mainPanelControls.getExpressionPanel().setHiddenFieldName("STATUS");
				mainPanelControls.getExpressionPanel().setHiddenFieldValue(analysisId);
				mainPanelControls.getExpressionPanel().getStatusForm().submit();
			}
			
		});
		
		mainPanelControls.getExpressionPanel().getStatusForm().addSubmitCompleteHandler(new SubmitCompleteHandler(){
			
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event){
				if (event == null) {
					Window.alert("onSubmitComplete: event is null!!");
					return;
				}		
				String results = event.getResults();
				if (results != null) {
					results = results.replaceAll("</*[pP][rR][eE][^>]*>", "");
					JSONValue jsonResult = JSONParser.parseStrict(results);
					JSONObject jObject = jsonResult.isObject();
					if(mainPanelControls.getExpressionPanel().getHiddenFieldName().matches("STATUS")){
						String status = jObject.get("name").toString();
						if (!status.isEmpty()){
							status = status.trim();
							status = status.replaceAll("^\"", "");
							status = status.replaceAll("\"$", "");
						} 
						if(status.matches("Starting")){
							mainPanelControls.getExpressionPanel().setHiddenFieldName("STATUS");
							mainPanelControls.getExpressionPanel().setHiddenFieldValue(analysisId);
							mainPanelControls.getExpressionPanel().getStatusForm().submit();
						} else {
						//	Window.alert(status);
							mainPanelControls.getExpressionPanel().setHiddenFieldName("RESULTS");
							mainPanelControls.getExpressionPanel().setHiddenFieldValue(analysisId);
							mainPanelControls.getExpressionPanel().getStatusForm().submit();
						}
					} else {
						mainPanelControls.getExpressionPopup().hide();
						
					//	parseExpression(jObject);
					}
				}
			}
		});
		
		mainPanelControls.getThresholdSliderBar().addBarValueChangedHandler(new BarValueChangedHandler() {
            public void onBarValueChanged(BarValueChangedEvent event) {
              //	WindowControls.prevThreshold = WindowControls.threshold;
            	SummaryPanel.threshold = event.getValue();
            	mainPanelControls.getThresholdValueBox().setText("" + event.getValue());
            	mainPanelControls.renderSummary(summary);
            }
		});
		
		mainPanelControls.getExpressionPanel().getTime1().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				HashMap<Integer,String> colorDetails = expression.getColorDetails(1);
				renderWithExpression(colorDetails);
			}
		});
		
		mainPanelControls.getExpressionPanel().getTime2().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				HashMap<Integer,String> colorDetails = expression.getColorDetails(2);
				renderWithExpression(colorDetails);
			}
		});
		
		mainPanelControls.getExpressionPanel().getTime3().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				HashMap<Integer,String> colorDetails = expression.getColorDetails(3);
				renderWithExpression(colorDetails);
			}
		});
		
		mainPanelControls.getExpressionPanel().getTime4().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				HashMap<Integer,String> colorDetails = expression.getColorDetails(4);
				renderWithExpression(colorDetails);
			}
		});
		
		mainPanelControls.getExpressionPanel().getTime5().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				HashMap<Integer,String> colorDetails = expression.getColorDetails(5);
				renderWithExpression(colorDetails);
			}
		});
		
		mainPanelControls.getLayoutOptions().addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				// TODO Auto-generated method stub
				int selectedIndex = mainPanelControls.getLayoutOptions().getSelectedIndex();
				changeLayout(selectedIndex);
			}
			
		});
		
		mainPanelControls.getExpressionSampleButton().addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				rpcService.getExpressionData(new AsyncCallback<ArrayList<ExpressionData>>() {
					public void onFailure(Throwable caught) {
						requestFailed(caught);
					}

					@Override
					public void onSuccess(ArrayList<ExpressionData> expressionData) {
						expression = new ExpressionRenderer(expressionData);
						expression.setLimits();
						List<Double> limits = expression.getLimits();
						mainPanelControls.getExpressionPanel().getMinLabel().setText("" + limits.get(0));
						mainPanelControls.getExpressionPanel().getMaxLabel().setText("" + limits.get(1));
						mainPanelControls.getExpressionPanel().getExpressionScale().addColorMaps(limits.get(0), limits.get(1));
						mainPanelControls.getExpressionPanel().setVisible(true);
					}
				});
			}
		});
	}
}
