package org.reactome.summary.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.reactome.summary.client.QueryService;
import org.reactome.summary.shared.ExpressionData;
import org.reactome.summary.shared.PathwaySummary;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
/**
 * Google Summer of Code 2012 Project
 * Reactome Pathway Summary Visualization
 * @author maulik
 *
 */
/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class QueryServiceImpl extends RemoteServiceServlet implements
		QueryService {

	static HashMap<Integer, Double> pathwayNodeRadiusGroup = new HashMap<Integer,Double>();
	
	/**
	 * Retrieves a static representation of pathway summary data
	 * @deprecated No more support on the client side for XML Parsing
	 */
	public String queryServer(){
	//	String returnResponse =  buildResponse();
		String returnResponse = null;
		returnResponse = buildStaticResponse();
		return (returnResponse);
	}
	
	public PathwaySummary getSummary(){
		try {
			PathwaySummaryFactory pathwaySummaryFactory = new PathwaySummaryFactory();
			pathwaySummaryFactory.buildEdges();
			PathwaySummary summary = pathwaySummaryFactory.generatePathwaySummary();
			return summary;
		} catch (IOException e){
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * Retries a static representation of pathway summary data in XML format
	 * To be used when REST API is not functioning
	 * @deprecated No more support of XML parsing on the client side
	 * @return xmlResponse
	 */
	private String buildStaticResponse(){
		// TODO Auto-generated method stub
		try {
			FileInputStream fstream = new FileInputStream("reactome.xml");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String fileContents = "";
			String strLine;
			while ((strLine = br.readLine()) != null)   {
				fileContents += strLine;
			}
			in.close();
			return fileContents; 
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Get sample expression data ready to be plot
	 * 
	 */
	@Override
	public ArrayList<ExpressionData> getExpressionData() {
		// TODO Auto-generated method stub
		System.out.println("Starting Expression");
		ArrayList<ExpressionData> data = new ArrayList<ExpressionData>();
		try {
			FileInputStream fstream = new FileInputStream("dataset.txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null)   {
				StringTokenizer st = new StringTokenizer(strLine);
				List<String> attributes = new ArrayList<String>();
				while(st.hasMoreTokens()) {
					attributes.add(st.nextToken());
				//	System.out.println(st.nextToken());
				}
				ExpressionData expDetails = new ExpressionData(attributes.get(0));
				expDetails.setExpressionLevels(attributes.get(1),attributes.get(2), attributes.get(3), attributes.get(4), attributes.get(5));
				//System.out.println(expDetails);
				data.add(expDetails);
			}
			in.close();
			 
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		return data;
	}

	/**
	 * Get Example dataset to be passed on to the servlet for expression analysis.
	 */
	@Override
	public String getExampleDataSet() {
		// TODO Auto-generated method stub
		String fileContents = "";
		try {
			FileInputStream fstream = new FileInputStream("exampledataset.txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			String strLine;
			while ((strLine = br.readLine()) != null)   {
				fileContents += strLine + "\n";
			}
			in.close();
			 
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		return fileContents;
	}

	
	/**
	 * A server side implementation of posting dataset for expression analysis to the servlet
	 */
	@Override
	public String sendDataSet(String expressionDataset) {
		String urlStr = "http://localhost:7080/ReactomeGWT/entrypoint/analysis";
		String [] paramName = {"expression_analysis_set"};
		String [] paramVal = {expressionDataset};
		String response = "failed";
		try {
			response = RESTRequest.httpPost(urlStr, paramName, paramVal);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * A server side implementation of getting the status of expression analysis
	 */
	@Override
	public String getAnalysisStatus(String analysisId) {
		// TODO Auto-generated method stub
		String urlStr = "http://localhost:7080/ReactomeGWT/entrypoint/analysis";
		String [] paramName = {"STATUS"};
		String [] paramVal = {analysisId};
		String response = "failed";
		try {
			response = RESTRequest.httpPost(urlStr, paramName, paramVal);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * A server side implementation of getting the results of expression analysis
	 */
	@Override
	public String getAnalysisResults(String analysisId) {
		// TODO Auto-generated method stub
		String urlStr = "http://localhost:7080/ReactomeGWT/entrypoint/analysis";
		String [] paramName = {"RESULTS"};
		String [] paramVal = {analysisId};
		String response = "failed";
		try {
			response = RESTRequest.httpPost(urlStr, paramName, paramVal);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	

}