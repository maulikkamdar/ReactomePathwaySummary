package org.reactome.summary.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.reactome.summary.shared.ConnectingEdge;
import org.reactome.summary.shared.GraphNode;
import org.reactome.summary.shared.PathwaySummary;
/**
 * Google Summer of Code 2012 Project
 * Reactome Pathway Summary Visualization
 * @author maulik
 *
 */
/**
 * Generates a PathwaySummary Instance from all the top-level pathways generated from TopLevelPathwayFactory.
 * Converts the pathways to GraphNodes and builds connecting edges between the GraphNodes.
 * The GraphNodes and the ConnectingEdges can be shared between Client/Server using RPC.
 *
 */
public class PathwaySummaryFactory{

	TopLevelPathwayFactory topLevelPathwayGroup;
	List<ConnectingEdge> connectingEdges;
	
	public PathwaySummaryFactory() throws IOException{
		topLevelPathwayGroup = new TopLevelPathwayFactory();
		connectingEdges = new ArrayList<ConnectingEdge>();
	}
	
	/**
	 * Build Connecting Edges between two top-level pathways.
	 */
	public void buildEdges(){
		List<TopLevelPathway> topLevelPathways = topLevelPathwayGroup.getTopLevelPathwayGroup();
		for(int i = 0; i < topLevelPathways.size(); i++){
			for (int j = 0; j < topLevelPathways.size(); j ++){
				if(topLevelPathways.get(j).getDbId() > topLevelPathways.get(i).getDbId()){
					ConnectingEdge edge = topLevelPathways.get(i).comparePathways(topLevelPathways.get(j));
					connectingEdges.add(edge);
				}
			}
		}
	}
	
	public TopLevelPathwayFactory getTopLevelPathwayGroup(){
		return topLevelPathwayGroup;
	}
	
	public List<ConnectingEdge> getConnectingEges(){
		return connectingEdges;
	}

	/**
	 * Generate Pathway Summary Instance
	 * @return pathway summary
	 */
	public PathwaySummary generatePathwaySummary() {
		// TODO Auto-generated method stub
		List<GraphNode> nodes = new ArrayList<GraphNode>();
		for(int i = 0; i < topLevelPathwayGroup.getTopLevelPathwayGroup().size(); i++){
			TopLevelPathway topLevelPathway = topLevelPathwayGroup.getTopLevelPathwayGroup().get(i);
			List<GraphNode> childNodes = new ArrayList<GraphNode>();
			for(int j = 0; j < topLevelPathway.getChildPathways().size(); j++){
				GraphNode childNode = new GraphNode(topLevelPathway.getChildPathways().get(j).getDbId(), topLevelPathway.getChildPathways().get(j).getPathwayName());
				childNodes.add(childNode);
			}
			GraphNode node = new GraphNode(topLevelPathway.getDbId(), topLevelPathway.getPathwayName());
			node.setTotalParticipatingMolecules(topLevelPathway.getTotalParticipatingMolecules());
			node.setChildNodes(childNodes);
			nodes.add(node);
		}
		PathwaySummary summary = new PathwaySummary(nodes,connectingEdges);
		return summary;
	}
}
