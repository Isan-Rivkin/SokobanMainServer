package model.restclient;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.hibernate.dialect.identity.HSQLIdentityColumnSupport;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import metadata.SolutionModel;
import metadata.SolutionQuery;

public class RestColTest {

	public static void main(String args[])
	{

		LogDescriptorTxt.writeLogTxt("./LocalTestFiles/newOne.txt", "contenet");
		// ask for a solution 
	//RESTRequester requester = new RESTRequester();
//		SolutionModel model = requester.requestFullSolutionService("http://localhost:8080","111");
//		System.out.println("model:" + model.getLevelSolution());
		// post new solution.
//		String retuend = requester.postLevelSolutionModel("http://localhost:8080",new SolutionModel("666MApTest", "13454", 11));
//		System.out.println(retuend);
		// post a solution to db.
		
////		// GET
//		System.out.println("running client");
//		Client client = ClientBuilder.newClient();
//		//http://localhost:8080/SokoREST/Echo
//		GsonBuilder b = new GsonBuilder();
//		Gson g = b.create();
//		SolutionQuery query = new SolutionQuery();
//		query.setLevelMap("111");
//		String jsonSolutionQuery = g.toJson(query);
//		System.out.println(jsonSolutionQuery);
//		String jsonNaked =jsonSolutionQuery.replace("{", "").replace("}", "");
//		System.out.println("naked: " + jsonNaked);
//		WebTarget webTarget = client.target("http://localhost:8080/SokoREST/solver/get/"+jsonNaked);
//		Invocation.Builder invocationBuilder = webTarget.request();
//		Response response= invocationBuilder.get();
//		System.out.println("Status: "+ response.getStatus());
//		String message = response.readEntity(String.class);
//		System.out.println("Message: " + message);
	}
}

