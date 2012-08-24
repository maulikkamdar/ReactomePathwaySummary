package org.reactome.summary.client.view;


import java.util.HashMap;

import org.reactome.summary.client.Vector;
import org.reactome.summary.shared.PathwaySummary;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.TextBox;
/**
 * Google Summer of Code 2012 Project
 * Reactome Pathway Summary Visualization
 *
 */
/**
 * Composite Panel wrapping the main user interface of the application
 * @author maulik
 *
 */
public class SummaryPanel extends Composite implements RequiresResize{
	
	private AbsolutePanel mainInterface;
	private ExpressionPanel expressionPanel;
	private ExpressionPopupPanel expressionPopupPanel;
	private LimitSliderBar sliderBar;
	private ListBox layoutOptions;
	private ListBox speciesOptions;
	private TextBox thresholdValue;
	private Label speciesLabel = new Label("Species");
	private Label layoutLabel = new Label("Layout");
	private Label thresholdLabel = new Label("Similarity Threshold");
	private Button expressionButton;
	private Button expressionSampleButton;
	private SummaryCanvas canvas;
	
	public static int threshold = 0;
	final String upgradeMessage = "Your browser does not support the HTML5 Canvas. Please upgrade your browser to view this demo.";
   	int interfaceWidth, interfaceHeight;
	
	public SummaryPanel() {
		init();
	}
	
	private void init() {
		// TODO Auto-generated method stub
		mainInterface = new AbsolutePanel();
		mainInterface.setStyleName("mainInterface");
		
		speciesOptions = new ListBox();
	    speciesOptions.addItem("Homo Sapiens");
	    speciesOptions.setVisibleItemCount(1);
	    
		layoutOptions = new ListBox();
	    layoutOptions.addItem("Circular");
	    layoutOptions.addItem("Radial-Tree");
	    layoutOptions.addItem("Force-Directed");
	    layoutOptions.addItem("Circle-Packed");
	    layoutOptions.setVisibleItemCount(1);
	
		sliderBar = new LimitSliderBar(100, "150px");	
		
		thresholdValue = new TextBox();
		thresholdValue.setText("" + threshold);
		thresholdValue.setWidth("20px");
		thresholdValue.setHeight("15px");
		
		expressionButton = new Button("Expression Analysis");
		expressionSampleButton = new Button("Sample Analysis");
		expressionPanel = new ExpressionPanel(); 
		expressionPanel.setVisible(false);
		
		expressionPopupPanel = new ExpressionPopupPanel();
	
		canvas = new SummaryCanvas(this);
		if (canvas == null) {
			return;
		}
		initWidget(mainInterface);
	}

	public ListBox getLayoutOptions(){
		return layoutOptions;
	}
	
	public ExpressionPopupPanel getExpressionPopup(){
		return expressionPopupPanel;
	}
	
	public Button getExpressionPanelButton() {
		return expressionButton;
	}
	
	public Button getExpressionSampleButton() {
		return expressionSampleButton;
	}
	
	public ExpressionPanel getExpressionPanel() {
		return expressionPanel;
	}
	
	public LimitSliderBar getThresholdSliderBar() {
		return sliderBar;
	}
	
	public TextBox getThresholdValueBox() {
		return thresholdValue;
	}
	
	public void setSize(int width, int height) {
		super.setSize(width + "px", height + "px");
		mainInterface.setSize(width + "px", height + "px");
        this.interfaceWidth = width;
        this.interfaceHeight = height;
        update();
	}

	@Override
	public void onResize() {
		// TODO Auto-generated method stub
		int width = getOffsetWidth();
        int height = getOffsetHeight();
        onResize(width, height);
	}
	
	private void onResize(int width, int height) {
        mainInterface.setSize(width + "px", height + "px");
        this.interfaceWidth = width;
        this.interfaceHeight = height;        
        update();
    }

	private void update() {
		// TODO Auto-generated method stub
		int canvasWidth = (int) (interfaceWidth);
	    int canvasHeight = (int) (0.85* interfaceHeight);
	    
	    canvas.setWidth(canvasWidth + "px");
		canvas.setHeight(canvasHeight + "px");
		canvas.setFocus(true);
		canvas.setStyleName("mainCanvas");
		canvas.setCoordinateSpaceWidth((int) canvasWidth);
		canvas.setCoordinateSpaceHeight((int) canvasHeight);
		
		
		int widgetHeightMidLine = 20 ;
		int widgetWidthMidLine = (int) interfaceWidth/2;
		
		expressionPopupPanel.setGlassEnabled(true);
		expressionPopupPanel.setAnimationEnabled(true);
		expressionPopupPanel.setStyleName("popup");
		expressionPopupPanel.setAutoHideEnabled(false);
	//	mainInterface.add(expressionPopupPanel, widgetWidthMidLine-50, interfaceHeight/2 -50);
		mainInterface.add(layoutOptions, widgetWidthMidLine-135, widgetHeightMidLine);
		mainInterface.add(layoutLabel, widgetWidthMidLine - 180, widgetHeightMidLine+5);
		mainInterface.add(speciesOptions, widgetWidthMidLine - 350, widgetHeightMidLine);
		mainInterface.add(speciesLabel, widgetWidthMidLine - 403, widgetHeightMidLine+5);
		mainInterface.add(sliderBar, widgetWidthMidLine + 150, widgetHeightMidLine+5);
		mainInterface.add(thresholdValue, widgetWidthMidLine + 310 , widgetHeightMidLine);
		mainInterface.add(thresholdLabel, widgetWidthMidLine+25, widgetHeightMidLine+5);
		mainInterface.add(expressionButton, widgetWidthMidLine-400, widgetHeightMidLine+35);
		mainInterface.add(expressionSampleButton, widgetWidthMidLine - 250, widgetHeightMidLine+35);
		mainInterface.add(expressionPanel, widgetWidthMidLine - 100, widgetHeightMidLine+35);
		mainInterface.add(canvas, 0, widgetHeightMidLine + 75);
	}

	public void updateSlider(double maxThresholdValue) {
		// TODO Auto-generated method stub
		sliderBar.setMaxValue((int) maxThresholdValue);
	}

	public int getThresholdValue() {
		return threshold;
	}

	public void renderWithExpression(HashMap<Integer, String> colorDetails) {
		// TODO Auto-generated method stub
		canvas.setExpression(colorDetails);
	}

	public void changeLayout(int layoutOption) {
		// TODO Auto-generated method stub
		canvas.setLayout(layoutOption);
	}

	public void renderSummary(PathwaySummary summary) {
		// TODO Auto-generated method stub
		canvas.renderSummary(summary);
	}
}
