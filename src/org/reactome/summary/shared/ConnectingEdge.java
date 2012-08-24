package org.reactome.summary.shared;

import java.io.Serializable;
/**
 * Google Summer of Code 2012 Project
 * Reactome Pathway Summary Visualization
 * @author maulik
 *
 */
/**
 * Connecting edge between two top-level pathways
 */
@SuppressWarnings("serial")
public class ConnectingEdge implements Serializable{

	int pathwayId1, pathwayId2, identicalMeasure;
	/**
	 * Dummy Constructor for RPC
	 */
	public ConnectingEdge(){
		
	}
	/**
	 * Main Constructor
	 * @param dbId dbId of Pathway 1
	 * @param dbId2 dbId of Pathway 2
	 */
	public ConnectingEdge(int dbId, int dbId2) {
		// TODO Auto-generated constructor stub
		this.pathwayId1=dbId;
		this.pathwayId2=dbId2;
	}
	
	/**
	 * Set the similarity measure of the connecting edge (used to calculate edge thickness)
	 * @param identicalMeasure similarity measure
	 */
	public void setIdenticalMeasure(int identicalMeasure){
		this.identicalMeasure = identicalMeasure;
	}

	/**
	 * Get dbId of first pathway of the connecting edge
	 * @return Pathway 1 dbId
	 */
	public int getNode1() {
		// TODO Auto-generated method stub
		return pathwayId1;
	}

	/**
	 * Get dbId of second pathway of the connecting edge
	 * @return Pathway 2 dbId
	 */
	public int getNode2() {
		// TODO Auto-generated method stub
		return pathwayId2;
	}

	/**
	 * Get the similarity measure of the connecting edge
	 * @return similarity measure
	 */
	public double getEdgeSimilarity() {
		// TODO Auto-generated method stub
		return identicalMeasure;
	}
	
	
}
