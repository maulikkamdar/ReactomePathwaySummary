package org.reactome.summary.client;

import java.util.ArrayList;

import org.reactome.summary.shared.ExpressionData;
import org.reactome.summary.shared.PathwaySummary;




import com.google.gwt.user.client.rpc.AsyncCallback;
/**
 * Google Summer of Code 2012 Project
 * Reactome Pathway Summary Visualization
 * @author maulik
 *
 */
/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface QueryServiceAsync {
	void queryServer(AsyncCallback<String> callback);
	void getExpressionData(AsyncCallback<ArrayList<ExpressionData>> callback);
	void getExampleDataSet(AsyncCallback<String> callback);
	void sendDataSet(String expressionDataset,AsyncCallback<String> asyncCallback);
	void getAnalysisStatus(String analysisId, AsyncCallback<String> asyncCallback);
	void getAnalysisResults(String analysisId, AsyncCallback<String> asyncCallback);
	void getSummary(AsyncCallback<PathwaySummary> callback);
}
