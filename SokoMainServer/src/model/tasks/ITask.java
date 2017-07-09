package model.tasks;

import java.util.Observer;

import metadata.METADATA;
import metadata.TaskModel;

public interface ITask extends Observer
{
	public int getTaskID();
	public String getProtocolID();
	public String getSessionID();
	public boolean isTaskReady();
	public boolean isTaskStarted();
	public void execute();
	public void killTask();
	public METADATA metaData();
	public String getTaskJSONResult();
	public void initParams(String sessionID, METADATA metadata, String jsonObject);
	public void addManager(Observer observer);
	public TaskModel getModel();
	public void initializeWithModel(TaskModel model);
	
}
