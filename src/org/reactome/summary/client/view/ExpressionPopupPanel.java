package org.reactome.summary.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
/**
 * Google Summer of Code 2012 Project
 * Reactome Pathway Summary Visualization
 *
 */
/**
 * Expression Popup Panel for user to paste expression data or upload them in a file.
 * @author maulik
 *
 */
public class ExpressionPopupPanel extends DialogBox {
	private int TEXT_AREA_WIDTH = 800;
	private FormPanel form = new FormPanel();
	private String action = null;
	private VerticalPanel dataEntryPanel = new VerticalPanel();
	private VerticalPanel innerDataEntryPanel = new VerticalPanel();
	private TextArea pastedDataTextArea;
	private Button analyseButton = new Button("Analyse");
	private Button exampleButton = new Button("Example");
	private Button clearButton = new Button("Clear");
	private Button closeButton = new Button("Close");
	private FileUpload upload = new FileUpload();
	
	public ExpressionPopupPanel() {
	      // Set the dialog box's caption.
      setHTML("<h1 class='expression-form-header'>Upload expression data</h1>");
      String descriptionText = "Takes gene expression data (and also numerical proteomics data) and shows how expression levels affect reactions and pathways in living organisms.  May be time-consuming, depending on the number of identifiers you are submitting; less than 5000: a few seconds, 5000 - 10000: a few minutes, 10000 or more: 10 minutes or longer. Your data should be formatted as a tab-delimited file, where the first column contains identifiers and subsequent columns contain numerical expression data.  You may paste your data into the supplied text area, or you can also upload it from a file.  Click on the \"Analyse\" button to perform this analysis.";
		
      HTML descriptionLabel = new HTML(descriptionText);
      descriptionLabel.setHorizontalAlignment(Label.ALIGN_LEFT);
      descriptionLabel.setStyleName("textbox"); 
      
      VerticalPanel mainExpressionPanel = new VerticalPanel();
      mainExpressionPanel.setWidth("800px");
      mainExpressionPanel.setStyleName("expression-panel");
      mainExpressionPanel.add(descriptionLabel);
      form.setEncoding(FormPanel.ENCODING_MULTIPART);
	  form.setMethod(FormPanel.METHOD_POST);
	  form.add(innerDataEntryPanel);
	  form.setStyleName("expression-form");
	  form.setAction("http://localhost:7080/ReactomeGWT/entrypoint/analysis");
	  dataEntryPanel.add(form);
      pastedDataTextArea = new TextArea();
      pastedDataTextArea.setSize(TEXT_AREA_WIDTH + "px", "200px");
      
      HorizontalPanel formTopRow = new HorizontalPanel();
      formTopRow.setWidth("100%");
      HTML pasteOrUploadLabel = new HTML("<b>Paste or upload your data:</b>");
      formTopRow.add(pasteOrUploadLabel);

      HorizontalPanel exampleButtonPanel = new HorizontalPanel();
      exampleButtonPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
      exampleButtonPanel.setWidth("100%");
      exampleButtonPanel.add(exampleButton);
      formTopRow.add(exampleButtonPanel);
      
      innerDataEntryPanel.add(formTopRow);

      pastedDataTextArea.setName("expression_analysis_set");
      innerDataEntryPanel.add(pastedDataTextArea);

      HorizontalPanel formMiddleRow = new HorizontalPanel();
	  upload.setName("FILE");
	  formMiddleRow.setWidth("100%");
	  formMiddleRow.add(upload);
	 
	  HorizontalPanel clearButtonPanel = new HorizontalPanel();
	  clearButtonPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
	  clearButtonPanel.setWidth("100%");
	  clearButtonPanel.add(clearButton);
      formMiddleRow.add(clearButtonPanel);
	  innerDataEntryPanel.add(formMiddleRow);
	  
	  HorizontalPanel formBottomRow = new HorizontalPanel();
	  formBottomRow.setWidth("100%");
	  formBottomRow.add(analyseButton);
	  
	  HorizontalPanel closeButtonPanel = new HorizontalPanel();
	  closeButtonPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
	  closeButtonPanel.setWidth("100%");
	  closeButtonPanel.add(closeButton);
      formBottomRow.add(closeButtonPanel);
	  innerDataEntryPanel.add(formBottomRow);

	  mainExpressionPanel.add(dataEntryPanel);
      setWidget(mainExpressionPanel);
	}
	
	public void disableAllButtons() {
		analyseButton.setEnabled(false);
		exampleButton.setEnabled(false);
		clearButton.setEnabled(false);
	}
	
	public void enableAllButtons() {
		analyseButton.setEnabled(true);
		exampleButton.setEnabled(true);
		clearButton.setEnabled(true);
	}
	
	public Button getExampleButton(){
		return exampleButton;
	}
	
	public Button getClearButton(){
		return clearButton;
	}
	
	public Button getCloseButton(){
		return closeButton;
	}
	
	public Button getAnalysisButton(){
		return analyseButton;
	}
	
	public TextArea getPastedDataTextArea(){
		return pastedDataTextArea;
	}
	
	public FormPanel getForm(){
		return form;
	}
}
