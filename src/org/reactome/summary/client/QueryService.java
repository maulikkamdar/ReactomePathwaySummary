package org.reactome.summary.client;

import java.util.ArrayList;

import org.reactome.summary.shared.ExpressionData;
import org.reactome.summary.shared.PathwaySummary;




import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
/**
 * Google Summer of Code 2012 Project
 * Reactome Pathway Summary Visualization
 * @author maulik
 *
 */
/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface QueryService extends RemoteService {
	String queryServer();
	ArrayList<ExpressionData> getExpressionData();
	String getExampleDataSet();
	String sendDataSet(String expressionDataset);
	String getAnalysisStatus(String analysisId);
	String getAnalysisResults(String analysisId);
	PathwaySummary getSummary();
}
