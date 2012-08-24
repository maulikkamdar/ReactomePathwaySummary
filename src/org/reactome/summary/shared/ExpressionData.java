package org.reactome.summary.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Google Summer of Code 2012 Project
 * Reactome Pathway Summary Visualization
 * @author maulik
 *
 */
/**
 * Expression Data for each pathway at various time intervals
 */
@SuppressWarnings("serial")

public class ExpressionData implements Serializable{

	int dbId;
	double expLevel1;
	double expLevel2;
	double expLevel3;
	double expLevel4;
	double expLevel5;
	
	/**
	 * Dummy Constructor for RPC
	 */
	public ExpressionData() {
		
	}
	
	/**
	 * Main Constructor
	 * @param string pathway dbId
	 */
	public ExpressionData(String string) {
		this.dbId = Integer.parseInt(string);
	}
	/**
	 * Main Constructor
	 * @param id pathway dbId
	 */
	public ExpressionData(int id){
		this.dbId = id;
	}
	
	/**
	 * Set the expression levels for particular pathway
	 * @param string expression level at time 1
	 * @param string2 expression level at time 2
	 * @param string3 expression level at time 3
	 * @param string4 expression level at time 4
	 * @param string5 expression level at time 5
	 */
	public void setExpressionLevels(String string, String string2,
			String string3, String string4, String string5) {
		// TODO Auto-generated method stub
		this.expLevel1 = Double.parseDouble(string);
		this.expLevel2 = Double.parseDouble(string2);
		this.expLevel3 = Double.parseDouble(string3);
		this.expLevel4 = Double.parseDouble(string4);
		this.expLevel5 = Double.parseDouble(string5);
	}
	
	/**
	 * Get the Pathways Id for which the expression data is set
	 * @return pathway Id
	 */
	public int getDbId() {
		return dbId;
	}

	/**
	 * Get the expression Levels
	 * @return List of expression Levels at different times
	 */
	public List<Double> getExpressionLevels() {
		List<Double> expressionLevels = new ArrayList<Double>();
		expressionLevels.add(expLevel1);
		expressionLevels.add(expLevel2);
		expressionLevels.add(expLevel3);
		expressionLevels.add(expLevel4);
		expressionLevels.add(expLevel5);
		return expressionLevels;
	}
}
