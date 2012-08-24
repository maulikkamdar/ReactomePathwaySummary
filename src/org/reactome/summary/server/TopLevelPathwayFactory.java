package org.reactome.summary.server;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
/**
 * Google Summer of Code 2012 Project
 * Reactome Pathway Summary Visualization
 * @author maulik
 *
 */
/**
 * Makes a GET request to frontPagetItems REST API.
 * Parses the JSON Response and generates top-level pathways, 
 * as well as first level child pathways from 'hasEvent' element.
 *
 */
public class TopLevelPathwayFactory {
	List<TopLevelPathway> topLevelPathwayGroup = new ArrayList<TopLevelPathway>();
	String requestString = "http://reactomedev.oicr.on.ca:8080/ReactomeRESTfulAPI/RESTfulWS/frontPageItems";
	String jsonResponse;
	
	/**
	 * Constructor to generate top-level pathways
	 * @throws IOException
	 */
	public TopLevelPathwayFactory() throws IOException {
		jsonResponse = RESTRequest.httpGet(requestString);
	//	System.out.println(jsonResponse);
		JSONObject json = (JSONObject) JSONSerializer.toJSON( jsonResponse );
        JSONArray jarray = json.getJSONArray("pathway");
        for(int i=0 ; i < jarray.size(); i++) {
        	JSONObject pathway = jarray.getJSONObject(i);
        	int dbId = (int) Double.parseDouble(pathway.getString("dbId"));
        	String name = pathway.getString("displayName");
        	System.out.println(name);
        	TopLevelPathway topLevelPathway = new TopLevelPathway(dbId, name);
        	topLevelPathwayGroup.add(topLevelPathway);
        	System.out.println("--------------");
       	
        	if(pathway.has("hasEvent")){
        		try {
		        	JSONArray childArray = pathway.getJSONArray("hasEvent");
		        	for(int j=0; j < childArray.size(); j ++ ){
		        		JSONObject childPathway = childArray.getJSONObject(j);
		        		if(childPathway.has("@type")){
			        		if(childPathway.getString("@type").matches("pathway")){
			        			String childPathwayName = childPathway.getString("displayName");
			        			int childPathwaydbId = (int) Double.parseDouble(childPathway.getString("dbId"));
			        			System.out.println(childPathwayName);
			        			Pathway subPathway = new Pathway(childPathwaydbId,childPathwayName);
			        			topLevelPathway.addChildPathway(subPathway);
			        		}
		        		}
		        	} } catch (Exception e){
		        		JSONObject childPathway = pathway.getJSONObject("hasEvent");
	        			if(childPathway.has("@type")){
			        		if(childPathway.getString("@type").matches("pathway")){
			        			String childPathwayName = childPathway.getString("displayName");
			        			int childPathwaydbId = (int) Double.parseDouble(childPathway.getString("dbId"));
			        			System.out.println(childPathwayName);
			        			Pathway subPathway = new Pathway(childPathwaydbId,childPathwayName);
			        			topLevelPathway.addChildPathway(subPathway);
			        		}
		        		}
		        	}
        	} 
        	System.out.println("--------------");
        	}
	}
	
	/**
	 * Get the list of all top-level pathways
	 * @return List of all Top-Level Pathways
	 */
	public List<TopLevelPathway> getTopLevelPathwayGroup(){
		return topLevelPathwayGroup;
	}
	
}
