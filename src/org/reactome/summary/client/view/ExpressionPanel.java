package org.reactome.summary.client.view;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
/**
 * Google Summer of Code 2012 Project
 * Reactome Pathway Summary Visualization
 *
 */
/**
 * Expression toolbar to be displayed when expression analysis is successfully carried out.
 * Renders the Radio Button Group for user to select the time of the expression 
 * and the expression scale for the user to map the color value back to the expression level
 * @author maulik
 *
 */
public class ExpressionPanel extends Composite{

	private HorizontalPanel mainInterface;
	private RadioButton time1,time2,time3,time4,time5;
	private GradientCanvas expressionGradient;
	private Label minValue,maxValue;
	private FormPanel statusForm = new FormPanel();
	private Hidden parameter;
	public ExpressionPanel() {
		init();
	}
	
	private void init() {
		mainInterface = new HorizontalPanel();
		minValue = new Label();
		maxValue = new Label();
		  
		time1 = new RadioButton("expressionTime", "Time 1");
	    time2 = new RadioButton("expressionTime", "Time 2");
	    time3 = new RadioButton("expressionTime", "Time 3");
	    time4 = new RadioButton("expressionTime", "Time 4");
	    time5 = new RadioButton("expressionTime", "Time 5");
	    
	    expressionGradient = new GradientCanvas(0,150);
	    expressionGradient.setWidth("150px");
	    expressionGradient.setHeight("20px");
	    expressionGradient.setCoordinateSpaceHeight(20);
	    expressionGradient.setCoordinateSpaceWidth(150);
	    mainInterface.add(minValue);
	    mainInterface.add(expressionGradient);
	    mainInterface.add(maxValue);
	    mainInterface.add(time1);
	    mainInterface.add(time2);
	    mainInterface.add(time3);
	    mainInterface.add(time4);
	    mainInterface.add(time5);
	    
	    parameter = new Hidden();
	    statusForm.setAction("http://localhost:7080/ReactomeGWT/entrypoint/analysis");
	    statusForm.setEncoding(FormPanel.ENCODING_MULTIPART);
	    statusForm.setMethod(FormPanel.METHOD_POST);
	    statusForm.setVisible(false);
	    statusForm.add(parameter);
	    mainInterface.add(statusForm);
	    initWidget(mainInterface);
	}

	public FormPanel getStatusForm(){
		return statusForm;
	}
	
	public void setHiddenFieldName(String parameterName){
		this.parameter.setName(parameterName);
	}
	
	public String getHiddenFieldName(){
		return parameter.getName();
	}
	
	public void setHiddenFieldValue(String parameterValue){
		this.parameter.setValue(parameterValue);
	}
	
	public RadioButton getTime1() {
		return time1;
	}
	
	public RadioButton getTime2() {
		return time2;
	}
	
	public RadioButton getTime3() {
		return time3;
	}
	
	public RadioButton getTime4() {
		return time4;
	}
	
	public RadioButton getTime5() {
		return time5;
	}
	
	public GradientCanvas getExpressionScale(){
		return expressionGradient;
	}
	
	public Label getMinLabel(){
		return minValue;
	}
	
	public Label getMaxLabel(){
		return maxValue;
	}
}
