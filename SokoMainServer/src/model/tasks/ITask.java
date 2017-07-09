package model.tasks;

import java.util.Observer;

import metadata.METADATA;
import metadata.TaskModel;
/**
 * 
 * @author Isan Rivkin and Daniel Hake
 * Task interface used by the task manager.
 * In general any task can run on the server with objet adapter pattern.
 */
public interface ITask extends Observer
{
	/**
	 * get a specific task id.
	 * @return unique task id
	 */
	public int getTaskID();
	/**
	 * Protocol id for the session with the client.
	 * @return unique protocol ID defined in Protocol.java
	 */
	public String getProtocolID();
	/**
	 * Every task is session related so it holds the session id 
	 * generated on session establishment.
	 * @return
	 */
	public String getSessionID();
	public boolean isTaskReady();
	public boolean isTaskStarted();
	/**
	 * execute the task.
	 */
	public void execute();
	public void killTask();
	/**
	 * Data from client request.
	 * @return
	 */
	public METADATA metaData();
	/**
	 * The task result as a json object.
	 * @return json string.
	 */
	public String getTaskJSONResult();
	/**
	 * params required for each task
	 * @param sessionID - session id with client
	 * @param metadata - data from the client request object
	 * @param jsonObject - the object followed after the client request
	 */
	public void initParams(String sessionID, METADATA metadata, String jsonObject);
	public void addManager(Observer observer);
	/**
	 * create a json initializable model.
	 * @return
	 */
	public TaskModel getModel();
	/**
	 * in someway like a copy constructer. 
	 * initiate the task with all details.
	 * @param model - a data json model.
	 */
	public void initializeWithModel(TaskModel model);
	
}
