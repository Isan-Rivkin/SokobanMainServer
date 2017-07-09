package model.restclient;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import metadata.SolutionModel;
import metadata.SolutionQuery;
import server.Protocol;
/**
 * A client requester class
 * Handles REST request for level solutions.
 * @author Isan Rivkin
 *
 */
public class RESTRequester
{
	/**
	 * Get a solution from the web service.
	 * @param domain - the service domain.
	 * @param levelMapKey - compressed level.
	 * @return solution encoded.
	 */
	public static String getLevelSolutionModel(String domain,String levelMapKey)
	{
		GsonBuilder b = new GsonBuilder();
		Gson g = b.create();
		Client client = ClientBuilder.newClient();
		SolutionQuery query = new SolutionQuery();
		query.setLevelMap(levelMapKey);
		String jsonSolutionQuery = g.toJson(query);
		String jsonNaked =jsonSolutionQuery.replace("{", "").replace("}", "");
		WebTarget webTarget = client.target(domain+Protocol.getSolutionPath+jsonNaked);
		Invocation.Builder invocationBuilder = webTarget.request();
		Response response= invocationBuilder.get();
		String solutionJson = response.readEntity(String.class);
		return solutionJson;
	}
	/**
	 * Post a new solution to the database.
	 * @param domain - webservice domain.
	 * @param solutionModel - the model to store.
	 * @return - confirmation.
	 */
	//http://localhost:8080/SokoREST/updateSolver/get/{jsonSolution}
	public static String postLevelSolutionModel(String domain,SolutionModel solutionModel)
	{
		GsonBuilder b = new GsonBuilder();
		Gson g = b.create();
		Client client = ClientBuilder.newClient();
		String jsonSolution = g.toJson(solutionModel);
		String jsonNaked =jsonSolution.replace("{", "").replace("}", "");
		WebTarget webTarget = client.target(domain+Protocol.postSolutionPath+jsonNaked);
		Invocation.Builder invocationBuilder = webTarget.request();
		Response response= invocationBuilder.get();
		String solutionJson = response.readEntity(String.class);
		return solutionJson;
	}
	public static SolutionModel getModelFromJson(String jsonSolutionModel)
	{
		GsonBuilder b = new GsonBuilder();
		Gson g = b.create();
		SolutionModel model = g.fromJson(jsonSolutionModel, SolutionModel.class);
		return model;
	}
	public static SolutionModel requestFullSolutionService(String domain,String levelMapKey)
	{
		GsonBuilder b = new GsonBuilder();
		Gson g = b.create();
		Client client = ClientBuilder.newClient();
		SolutionQuery query = new SolutionQuery();
		query.setLevelMap(levelMapKey);
		String jsonSolutionQuery = g.toJson(query);
		String jsonNaked =jsonSolutionQuery.replace("{", "").replace("}", "");
		WebTarget webTarget = client.target(domain+Protocol.getSolutionPath+jsonNaked);
		Invocation.Builder invocationBuilder = webTarget.request();
		Response response= invocationBuilder.get();
		String solutionJson = response.readEntity(String.class);
		return getModelFromJson(solutionJson);
	}
}
