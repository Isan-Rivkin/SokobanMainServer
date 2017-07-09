package controller.commands;


import java.util.LinkedList;
import java.util.List;

import metadata.SessionModel;
import metadata.TaskModel;
import model.ISSModel;
import model.tasks.ITask;
import model.tasks.ITaskManager;
import model.tasks.TaskManager;
import server.Session;
import server.SessionManager;
import view.ViewModel;

public class NewTaskAndSessionCreatedCMD implements Command
{

	private ViewModel vm;
	private ISSModel model;
	private SessionManager sm;
	private ITaskManager tm;
	
	public NewTaskAndSessionCreatedCMD(ViewModel vm , ISSModel model , SessionManager sm, ITaskManager tm) 
	{
		this.vm=vm;
		this.model=model;
		this.sm=sm;
		this.tm=tm;
	}
	@Override
	public void execute() 
	{
		List<ITask> tasksList = tm.getCurrentTasks();
		LinkedList<TaskModel> taskModels= new LinkedList<>();
		for(ITask t : tasksList)
		{
			taskModels.add(t.getModel());
		}
		List<Session> sessionsList = sm.getAllSessions();
		LinkedList<SessionModel> sessionModels = new LinkedList<>();
		for(Session s : sessionsList)
		{
			sessionModels.add(s.getModel());
		}
		vm.updateSessions(sessionModels,taskModels);
	}

	@Override
	public void init(LinkedList<String> arg0) 
	{
		
	}

}
