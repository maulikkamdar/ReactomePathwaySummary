package org.reactome.summary.server;

import java.io.IOException;
/**
 * Google Summer of Code 2012 Project
 * Reactome Pathway Summary Visualization
 * @author maulik
 *
 */
/**
 * Pathway Class to construct each pathway generated from TopLevelPathwayFactory
 */
public class Pathway {
	int pathwayId;
	String pathwayName;
	int parentPathwayId;
	
	/**
	 * Constructor
	 * @param dbId pathway dbId
	 * @param name pathway name
	 * @throws IOException
	 */
	public Pathway(int dbId, String name) throws IOException {
		// TODO Auto-generated constructor stub
		this.pathwayId = dbId;
		this.pathwayName = name;
	}

	/**
	 * Set the parent pathway for the child pathways
	 * @param dbId Pathway dbId of the parent pathway
	 */
	public void setParentPathway (int dbId) {
		this.parentPathwayId = dbId;
	}
	
	/**
	 * Get the dbId of the pathway
	 * @return dbId of the pathway
	 */
	public int getDbId(){
		return pathwayId;
	}
	
	/**
	 * Get the name of the Pathway
	 * @return Pathway Name
	 */
	public String getPathwayName(){
		return pathwayName;
	}
}
