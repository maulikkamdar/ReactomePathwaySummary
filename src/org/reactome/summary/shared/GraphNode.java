package org.reactome.summary.shared;

import java.io.Serializable;
import java.util.List;
/**
 * Google Summer of Code 2012 Project
 * Reactome Pathway Summary Visualization
 *
 */
/**
 * GraphNode Class to represent all pathways as nodes in PathwaySummary instance
 * @author maulik
 *
 */
@SuppressWarnings("serial")
public class GraphNode implements Serializable{
	int pathwayId;
	String pathwayName;
	int totalParticipatingMolecules;
	List<GraphNode> childNodes;
	
	/**
	 * Dummy Constructor for RPC
	 */
	public GraphNode(){
		
	}
	
	/**
	 * Main Constructor to generate a graph node from pathway information
	 * @param dbId pathway DbId 
	 * @param pathwayName Name of the Pathway
	 */
	public GraphNode(int dbId, String pathwayName) {
		// TODO Auto-generated constructor stub
		this.pathwayId = dbId;
		this.pathwayName = pathwayName;
	}

	/**
	 * Get the dbId of the pathway
	 * @return Pathway dbId
	 */
	public int getDbId(){
		return pathwayId;
	}
	
	/**
	 * Get the name of the pathway
	 * @return Pathway Name
	 */
	public String getPathwayName(){
		return pathwayName;
	}
	
	/**
	 * Set the total participating molecules whenever pathway is a top-level pathway
	 * @param totalMolecules Total Participating Molecules
	 */
	public void setTotalParticipatingMolecules(int totalMolecules){
		this.totalParticipatingMolecules= totalMolecules;
	}
	
	/**
	 * Set the child nodes of the pathway whenever pathway is a top-level pathway
	 * @param childNodes Child Pathways
	 */
	public void setChildNodes(List<GraphNode> childNodes){
		this.childNodes = childNodes;
	}
	
	/**
	 * Get the child nodes whenever pathway is a top-level pathway
	 * @return List of child Nodes
	 */
	public List<GraphNode> getChildNodes(){
		return childNodes;
	}
	
	/**
	 * Get the total participating molecules whenever pathway is a top-level pathway
	 * @return total participating molecules
	 */
	public int getTotalParticipatingMolecules(){
		return totalParticipatingMolecules;
	}
	
	/**
	 * Get the radius of the GraphNode
	 * @return Node Radius
	 */
	public double getPathwayNodeRadius(){
		return Math.sqrt(getTotalParticipatingMolecules());
	}
	
}
