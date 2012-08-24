package org.reactome.summary.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.reactome.summary.shared.ExpressionData;
/**
 * Google Summer of Code 2012 Project
 * Reactome Pathway Summary Visualization
 *
 */
/**
 * Expression Renderer Class to map Expression Data against the Blue-Yellow Color Scale
 * @author maulik
 *
 */

public class ExpressionRenderer {
	double highMin = 0.00, highMax = 0.00;
	ArrayList<ExpressionData> data;
	
	/**
	 * Constructor for ExpressionRenderer
	 * @param expressionData The expression data to be mapped. 
	 */
	public ExpressionRenderer(ArrayList<ExpressionData> expressionData) {
		// TODO Auto-generated constructor stub
		this.data = expressionData;
	}
	
	/**
	 * Parse the expression Levels for the minimum and the maximum expression Level
	 */
	public void setLimits() {	
		boolean highSet = false;
		for(int i = 0; i < data.size() ; i ++) {
			List<Double> expressionLevels = data.get(i).getExpressionLevels();
			for(int j = 0; j < expressionLevels.size() ; j ++) {
				if(highSet == false) {
					highMin = expressionLevels.get(j);
					highMax = expressionLevels.get(j);
				}
				if(expressionLevels.get(j) > highMax)
					highMax = expressionLevels.get(j);
				if(expressionLevels.get(j) < highMin)
					highMin = expressionLevels.get(j);
				highSet = true;
			}
		}		
		
	}
	
	/**
	 * Maps the expression levels at particular time against the color scale
	 * @param time Time of expression
	 * @return Hash Table with pathway dbId as key and mapped color string as value
	 */
	public HashMap<Integer,String> getColorDetails(int time) {
		HashMap<Integer, String> colorDetails = new HashMap<Integer,String>();
		for(int i = 0; i < data.size(); i ++) {
			String bgColor = mapValuetoColor(data.get(i).getExpressionLevels().get(time-1));
			colorDetails.put((int)data.get(i).getDbId(), bgColor);
		}
		return colorDetails;
	}
	
	/**
	 * Maps a single expression value against the color scale
	 * @param expressionValue the expression value to be mapped
	 * @return the color represented as a string in RGB format
	 */
	private String mapValuetoColor(double expressionValue) {
		double level = expressionValue;
        double min = highMin; 
        double max = highMax; 
        ExpressionColorPicker expressionColorPicker = new ExpressionColorPicker(min, max);
        Color foregroundColor = expressionColorPicker.pickForegroundColor(level);
        String foregroundColorString = foregroundColor.getHexValue();
        return foregroundColorString;
	}
	
	/**
	 * Get the minimum and maximum levels of the expression analysis.
	 * @return List containing 2 elements - the minimum and the maximum levels in expression
	 */
	public List<Double> getLimits() {
		List<Double> limits = new ArrayList<Double>();
		limits.add(highMin);
		limits.add(highMax);		
		return limits;
	}
}
