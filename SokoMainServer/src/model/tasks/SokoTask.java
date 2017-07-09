package model.tasks;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import metadata.METADATA;
import metadata.TaskModel;
import model.restclient.RestClient;
import server.Protocol;

public class SokoTask extends Observable implements ITask,Serializable
{
	private String sessionID;
	private METADATA metadata;
	private String jsonObject;
	private String jsonResult;
	private boolean taskReady;
	private boolean taskStarted;
	private transient RestClient restClient;
	private static int taskID=0;
	
	public SokoTask() 
	{	
	}
	public SokoTask(String sessionID, METADATA metadata, String jsonObject) 
	{
		taskID++;
		this.taskStarted=false;
		this.taskReady = false;
		this.sessionID= sessionID;
		this.metadata=metadata;
		this.jsonObject=jsonObject;
	}
	@Override
	public int getTaskID() 
	{
		return taskID;
	}
	
	@Override
	public String getSessionID() 
	{
		return sessionID;
	}

	@Override
	public boolean isTaskReady() 
	{
		return taskReady;
	}

	@Override
	public boolean isTaskStarted() 
	{
		return taskStarted;
	}

	@Override
	public void execute()
	{
		restClient = new RestClient(this, sessionID, metadata, jsonObject);
		taskStarted = true;
		new Thread(()->
		{
			restClient.executeTask();
		}).start();;
	}

	@Override
	public void killTask()
	{
		
	}

	@Override
	public METADATA metaData()
	{
		return metadata;
	}

	@Override
	public String getTaskJSONResult() 
	{
		return jsonResult;
	}

	@Override
	public void initParams(String sessionID, METADATA metadata, String jsonObject) 
	{
		this.sessionID=sessionID;
		this.metadata=metadata;
		this.jsonObject=jsonObject;
	}
	// rest client returns an answer when ready.
	@Override
	public void update(Observable arg0, Object arg1) 
	{
		taskReady=true;
		LinkedList<String> params = (LinkedList<String>)arg1;
		this.jsonResult = params.getLast();
		params.addFirst(""+taskID);
		params.addFirst(Protocol.restClientFinished);
		setChanged();
		notifyObservers(params);
	}
	@Override
	public String getProtocolID() 
	{
		return metadata.getId();
	}
	@Override
	public void addManager(Observer observer)
	{
		this.addObserver(observer);
	}
	public METADATA getMetadata() {
		return metadata;
	}
	public void setMetadata(METADATA metadata) {
		this.metadata = metadata;
	}
	public String getJsonObject() {
		return jsonObject;
	}
	public void setJsonObject(String jsonObject) {
		this.jsonObject = jsonObject;
	}
	public String getJsonResult() {
		return jsonResult;
	}
	public void setJsonResult(String jsonResult) {
		this.jsonResult = jsonResult;
	}
	public RestClient getRestClient() {
		return restClient;
	}
	public void setRestClient(RestClient restClient) {
		this.restClient = restClient;
	}
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	public void setTaskReady(boolean taskReady) {
		this.taskReady = taskReady;
	}
	public void setTaskStarted(boolean taskStarted) {
		this.taskStarted = taskStarted;
	}
	public static void setTaskID(int taskID) {
		SokoTask.taskID = taskID;
	}
	@Override
	public TaskModel getModel() 
	{
		TaskModel tm= new TaskModel(sessionID, metadata, jsonObject, jsonResult, taskReady, taskStarted, taskID);
		return tm;
	}
	@Override
	public void initializeWithModel(TaskModel model) 
	{
		sessionID=model.getSessionID();
		metadata=model.getMetadata();
		jsonObject=model.getJsonObject();
		jsonResult = model.getJsonResult();
		taskReady = model.isTaskReady(); 
		taskStarted=model.isTaskStarted();
		taskID = model.getTaskID();
	}

}
