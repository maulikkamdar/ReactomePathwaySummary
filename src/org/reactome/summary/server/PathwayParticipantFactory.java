package org.reactome.summary.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
/**
 * Google Summer of Code 2012 Project
 * Reactome Pathway Summary Visualization
 * @author maulik
 *
 */
/**
 * Makes GET Request to pathwayParticipants REST API for each top-level pathway.
 * Parses the json response using a simple text parser, and builds a sorted array of participating molecule IDs.
 */
public class PathwayParticipantFactory {

	int totalPathwayParticipants;
	int dbId;
	String requestString;
	ArrayList<Double> pathwayParticipants = new ArrayList<Double>();
	
	/**
	 * Makes GET Request to pathwayParticipants REST API for specified dbId
	 * Parses the json response using a simple text parser, and builds a sorted array of participating molecule IDs
	 * @param dbId pathway Id
	 * @throws IOException
	 */
	public PathwayParticipantFactory (int dbId) throws IOException{
		this.dbId = dbId;
		this.requestString = "http://reactomedev.oicr.on.ca:8080/ReactomeRESTfulAPI/RESTfulWS/pathwayParticipants/" + dbId;
		String jsonResponse = RESTRequest.httpGet(requestString);
		String[] entities = jsonResponse.split("\\}\\{");
		totalPathwayParticipants = entities.length;
		for(int i = 0; i < totalPathwayParticipants ; i ++){
			String[] attributes = entities[i].split(",");
			double entityId = Double.parseDouble((attributes[0].split(":"))[1]);
			pathwayParticipants.add(entityId);
		}
		Collections.sort(pathwayParticipants);
	}
	
	/**
	 * Returns the total number of pathway participants
	 * @return total number of pathway participants
	 */
	public int getTotalPathwayParticipants() {
		return totalPathwayParticipants;		
	}
	
	/**
	 * Returns a sorted array of the IDs of participating molecules in the top-level pathway
	 * @return Sorted Array of IDs
	 */
	public ArrayList<Double> getPathwayParticipants() {
		return pathwayParticipants;
	}
}
