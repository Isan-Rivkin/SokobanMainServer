package model.restclient;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import metadata.SolutionModel;

/**
 * Rest client implementations.
 * @author Isan Rivkin
 *
 */
public class ClientSol 
{
    public String levelEncoded;
    public String sol;
    
    public ClientSol()
    {
        this.levelEncoded="";
        this.sol="";
    }
    
    public String levelSol(String str)
    {
        this.levelEncoded=str;
        Client client = ClientBuilder.newClient();
        // invoke the webTarget
       // WebTarget webTarget = client.target("http://localhost:8080/SokoREST/solver/get/"+this.levelEncoded);
        WebTarget webTarget = client.target("http://localhost:8080/SokoREST/Echo/"+this.levelEncoded);
        Invocation.Builder invocationBuilder = webTarget.request().accept("text/plain");
        
        // get the response from the invoker
        Response response = invocationBuilder.get();
        
        System.out.println("Got from server: ");
        String msg = response.readEntity(String.class);

        return msg;
    }
    public void setlevel(String name)
    {
        this.levelEncoded = name;
    }
    public String getlevelEncoded()
    {
        return levelEncoded;
    }
    public void setSol(String j)
    {
        this.sol = j;
    }
    public String getSol()
    {
        return this.sol; 
    }

    public void sendString(String str)
    {
        this.levelEncoded=str;
        Client client = ClientBuilder.newClient();
        // invoke the webTarget
        WebTarget webTarget = client.target("http://localhost:8080/SokoREST/updateSolver/get/"+this.levelEncoded);
        Invocation.Builder invocationBuilder = webTarget.request().accept("text/plain");
        
        // get the response from the invoker
        Response response = invocationBuilder.get();
        
        System.out.println("Got from server: ");
        String msg = response.readEntity(String.class);
    }   
 

}
