package controller.commands;

import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import metadata.METADATA;
import model.ISSModel;
import model.tasks.ITask;
import model.tasks.ITaskManager;
import model.tasks.SokoTask;
/**
 * Add a new task from a session.
 * @author Isan Rivkin and Daniel Hake
 *
 */
public class AddTaskCMD implements Command
{

	private ISSModel model;
	private String SessionID;
	private METADATA metadata;
	private String jsonObject,jsonMetadata;
	private Gson gson;
	private GsonBuilder gsonBuilder;
	private ITaskManager taskManager;
	
	public AddTaskCMD(ISSModel model,ITaskManager taskManager) 
	{
		this.taskManager = taskManager;
		model=model;
		gsonBuilder = new GsonBuilder();
		gson=gsonBuilder.create();
	}
	@Override
	public void execute() 
	{
		metadata = gson.fromJson(jsonMetadata, METADATA.class);
		ITask task = new SokoTask(SessionID, metadata, jsonObject);
		taskManager.addTask(task);
	}
	//params : {"addTask",sessionID,"METADATA","OBJECT"}
	@Override
	public void init(LinkedList<String> arg0) 
	{
		this.SessionID=arg0.removeFirst();
		this.jsonMetadata=arg0.removeFirst();
		this.jsonObject = arg0.removeFirst();
	}

}
