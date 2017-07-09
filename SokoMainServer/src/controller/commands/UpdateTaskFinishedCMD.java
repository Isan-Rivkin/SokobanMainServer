package controller.commands;

import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import metadata.TaskModel;
import model.ISSModel;
import model.tasks.ITask;
import model.tasks.SokoTask;
import server.SessionManager;
import view.ViewModel;
/**
 * Update that a task has been finished to whom it may be interesting.
 * @author Isan Rivkin
 *
 */
public class UpdateTaskFinishedCMD implements Command
{
	private ISSModel model;
	private ViewModel viewModel;
	private String sessionID;
	private String metadataJson, jsonObject,jsonResult,taskID,taskJsonObject;
	private Gson gson;
	private GsonBuilder gsonBuilder;
	private SessionManager sm;
	public UpdateTaskFinishedCMD(ISSModel model, ViewModel viewModel,SessionManager sm) 
	{
		this.sm=sm;
		this.gsonBuilder = new GsonBuilder();
		this.gson = gsonBuilder.create();
		this.model=model;
		this.viewModel=viewModel;
	}

	@Override
	public void execute() 
	{
		TaskModel taskModel = gson.fromJson(taskJsonObject,TaskModel.class);
		ITask task = new SokoTask();
		task.initializeWithModel(taskModel);
		sm.notifyTaskFinished(task);
	}

	// task is finished by rest client {cmd,taskID,sessionID, metadata,jsonObject, jsonResult,taskJsonObject}
	@Override
	public void init(LinkedList<String> arg0) 
	{
		this.taskID=arg0.removeFirst();
		this.metadataJson=arg0.removeFirst();
		this.jsonObject=arg0.removeFirst();
		this.jsonResult=arg0.removeFirst();
		this.taskJsonObject = arg0.removeLast();
	}

}
