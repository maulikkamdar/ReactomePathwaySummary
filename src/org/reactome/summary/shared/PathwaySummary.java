package org.reactome.summary.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Google Summer of Code 2012 Project
 * Reactome Pathway Summary Visualization
 *
 */
/**
 * PathwaySummary generated from Server-side PathwaySummaryFactory Class.
 * Is shared between client/server via RPC.
 * @author maulik
 *
 */
@SuppressWarnings("serial")
public class PathwaySummary implements Serializable{

	public List<ConnectingEdge> edges;
	public List<GraphNode> nodes;
	/**
	 * Dummy Constructor for RPC
	 */
	public PathwaySummary(){
		
	}
	/**
	 * Main Constructor
	 * @param nodes List of GraphNode
	 * @param connectingEdges List of ConnectingEdge
	 */
	public PathwaySummary(List<GraphNode> nodes, List<ConnectingEdge> connectingEdges) {
		// TODO Auto-generated constructor stub
		this.nodes = nodes;
		this.edges = connectingEdges;
	}

	/**
	 * Get all the Graph Nodes
	 * @return List of GraphNode
	 */
	public List<GraphNode> getNodes() {
		return nodes;
	}
	
	/**
	 * Get all the Connecting Edges
	 * @return List of ConnectingEdge
	 */
	public List<ConnectingEdge> getEdges() {
		return edges;
	}
	
	/**
	 * Generate a threshold value
	 * @return value such that at least 20 pathways have similarity measure greater than that value
	 */
	public double generateThreshold() {
		List<ConnectingEdge> allEdges = getEdges();
		List<Double> similarities = new ArrayList<Double>();
		for(int i = 0; i < allEdges.size() ; i ++) {
			double similarity = allEdges.get(i).getEdgeSimilarity();
			similarities.add(similarity);
		}
		Collections.sort(similarities);
		double maxThreshold = similarities.get(similarities.size()-20);
		return maxThreshold;
	}
	
	/**
	 * Get all the dbIds of top-level pathways and their child pathways.
	 * @return List of dbIds.
	 */
	public List<Integer> getAllDbIds(){
		List<Integer> allDbIds = new ArrayList<Integer>();
		for(int i = 0; i < nodes.size(); i ++){
			allDbIds.add(nodes.get(i).getDbId());
			for(int j =0 ; j < nodes.get(i).getChildNodes().size(); j ++){
				allDbIds.add(nodes.get(i).getChildNodes().get(j).getDbId());
			}
		}
		Collections.sort(allDbIds);
		return allDbIds;
	}
}
