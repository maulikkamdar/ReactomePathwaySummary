package org.reactome.summary.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.reactome.summary.shared.ConnectingEdge;

/**
 * Google Summer of Code 2012 Project
 * Reactome Pathway Summary Visualization
 * @author maulik
 *
 */

/**
 * Extends Pathway Class to obtain participating molecules as well as compare with other top-level pathways
 * 
 */
public class TopLevelPathway extends Pathway{
	
	PathwayParticipantFactory participatingMolecules;
	int totalParticipatingMolecules;
	List<Pathway> childPathways;
	
	/**
	 * Constructor for top-level pathway
	 * @param dbId Pathway Id
	 * @param name Pathway Name
	 * @throws IOException
	 */
	public TopLevelPathway(int dbId, String name) throws IOException {
		// TODO Auto-generated constructor stub
		super(dbId,name);		
		this.participatingMolecules = new PathwayParticipantFactory(pathwayId);
		this.totalParticipatingMolecules = this.participatingMolecules.getTotalPathwayParticipants();
		childPathways = new ArrayList<Pathway>();
	}
	
	/**
	 * Get the total participating molecules in the top-level pathway
	 * @return the total number of participating molecules
	 */
	public int getTotalParticipatingMolecules () {
		return totalParticipatingMolecules;
	}
	
	/**
	 * Add a child Pathway to the top-level pathway based on 'hasEvent' element
	 * @param pathway Pathway instance to be added
	 */
	public void addChildPathway(Pathway pathway){
		childPathways.add(pathway);
	}
	
	/**
	 * Get all the child pathways for the particular top-level pathway
	 * @return an array of pathways
	 */
	public List<Pathway> getChildPathways(){
		return childPathways;
	}

	/**
	 * Get all the participating molecules in the particular top-level pathway
	 * @return Pathway Participant Molecules Group
	 */
	public PathwayParticipantFactory getPathwayParticipantGroup(){
		return participatingMolecules;
	}
	
	/**
	 * Compare the pathway with another top-level pathway
	 * @param pathway the second top-level pathway
	 * @return Connecting Edge between the two pathways
	 */
	public ConnectingEdge comparePathways(TopLevelPathway pathway){
		ConnectingEdge edge = new ConnectingEdge(this.getDbId(), pathway.getDbId());
		int identicalMolecules = compareTwoPathways(this.getPathwayParticipantGroup().getPathwayParticipants(), pathway.getPathwayParticipantGroup().getPathwayParticipants());
		edge.setIdenticalMeasure(identicalMolecules);
		return edge;
	}
	
	/**
	 * Compare two arrays of pathway participating molecules
	 * @param pathway1 First Array
	 * @param pathway2 Second Array
	 * @return return the number of identical molecules
	 */
	private int compareTwoPathways(ArrayList<Double> pathway1, ArrayList<Double> pathway2){
		int identicalParticipatingMolecules = 0 ;
		for(int i = 0; i < pathway1.size(); i++) {
			double testDbID = pathway1.get(i);
			for(int j = 0 ; j < pathway2.size() ; j++) {
				if(testDbID == pathway2.get(j))
					identicalParticipatingMolecules++;
				if(testDbID < pathway2.get(j))
					break;
			}
		}
		return identicalParticipatingMolecules;
	}
}
