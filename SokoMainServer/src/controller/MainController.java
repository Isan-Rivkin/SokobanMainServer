package controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import controller.commands.AddTaskCMD;
import controller.commands.CloseASessionCMD;
import controller.commands.Command;
import controller.commands.LogUpdateCMD;
import controller.commands.NewTaskAndSessionCreatedCMD;
import controller.commands.StartServerCMD;
import controller.commands.StopServerCMD;
import controller.commands.UpdateTaskFinishedCMD;
import controller.general_controller.GeneralController;
import controller.server.SokoServer;
import model.ISSModel;
import server.Protocol;
import sokoban_utils.SokoUtil;
import view.ViewModel;
/**
 * The main Controller of the server.
 * Based on Command pattern.
 * The Controller speaks with the ViewModel and The Model Facade.
 * @author Isan Rivkin and Daniel Hake.
 *
 */
public class MainController implements FController,Observer 
{
	private ISSModel model;
	private ViewModel viewModel;
	private GeneralController controller;
	private SokoServer server;
	private HashMap<String, Command> create_cmd;
	private SokoUtil util;
	private boolean keepRunning;
	
	public MainController(ISSModel model, ViewModel viewModel)
	{
		this.controller=new GeneralController();
		this.model=model;
		this.viewModel=viewModel;
		this.util=new SokoUtil();
		this.keepRunning=true;
		initCommandsMap();
	}
	/**
	 * All possible commands.
	 */
	private void initCommandsMap()
	{
		this.create_cmd=new HashMap<String,Command>();
		this.create_cmd.put(Protocol.addTask,new AddTaskCMD(model,model.getTaskManager()));
		this.create_cmd.put(Protocol.restClientFinished, new UpdateTaskFinishedCMD(model, viewModel,model.getSessionManager()));
		this.create_cmd.put(Protocol.startServer, new StartServerCMD(model, model.getServer()));
		this.create_cmd.put(Protocol.stopServer, new StopServerCMD(model, model.getServer(), model.getSessionManager()));
		this.create_cmd.put(Protocol.newTaskAndSessionUpdate, new NewTaskAndSessionCreatedCMD(viewModel, model, model.getSessionManager(),model.getTaskManager()));
		this.create_cmd.put(Protocol.logUpdate,new LogUpdateCMD(viewModel));
		this.create_cmd.put(Protocol.closeAsession, new CloseASessionCMD(model, model.getSessionManager()));
	}
	@Override
	public void update(Observable o, Object arg) 
	{
		LinkedList<String> params = (LinkedList<String>) arg;
		String commandKey = params.removeFirst();
		Command c = create_cmd.get(commandKey);
		if(c==null)
		{
			return;
		}
		c.init(params);
		controller.insertCommand(c);
	}
	@Override
	public void start() 
	{
		controller.start();
	}
	@Override
	public void stop() 
	{
		controller.stop();
	}

	
}