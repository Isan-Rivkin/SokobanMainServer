package metadata;

import java.io.Serializable;

import model.restclient.RestClient;
/**
 * Serialized model to json and oposite of ITask.
 * @author Isan Rivkin and Daniel Hake.
 *
 */
public class TaskModel implements Serializable
{
	private String sessionID;
	private METADATA metadata;
	private String jsonObject;
	private String jsonResult;
	private boolean taskReady;
	private boolean taskStarted;
	private int taskID;
	
	public TaskModel() {}
	public TaskModel(String sessionID, METADATA metadata, String jsonObject, String jsonResult, boolean taskReady,
			boolean taskStarted, int taskID) 
	{
		super();
		this.sessionID = sessionID;
		this.metadata = metadata;
		this.jsonObject = jsonObject;
		this.jsonResult = jsonResult;
		this.taskReady = taskReady;
		this.taskStarted = taskStarted;
		this.taskID = taskID;
	}
	public String getSessionID() {
		return sessionID;
	}
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
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
	public boolean isTaskReady() {
		return taskReady;
	}
	public void setTaskReady(boolean taskReady) {
		this.taskReady = taskReady;
	}
	public boolean isTaskStarted() {
		return taskStarted;
	}
	public void setTaskStarted(boolean taskStarted) {
		this.taskStarted = taskStarted;
	}
	public int getTaskID() {
		return taskID;
	}
	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}
	@Override
	public String toString() {
		return "TaskModel [sessionID=" + sessionID + ", metadata=" + metadata.getId() + ", jsonObject=" + jsonObject
				+ ", jsonResult=" + jsonResult + ", taskReady=" + taskReady + ", taskStarted=" + taskStarted
				+ ", taskID=" + taskID + "]";
	}
}
