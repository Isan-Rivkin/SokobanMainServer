package controller.commands;

import java.util.LinkedList;

import model.ISSModel;
import server.SessionManager;
import server.SokoMultiServer;

public class StopServerCMD implements Command
{

	private ISSModel model;
	private SokoMultiServer server;
	private SessionManager sm;
	public StopServerCMD(ISSModel model , SokoMultiServer server, SessionManager sm) 
	{
		
		this.model=model;
		this.server=server;
		this.sm=sm;
	}
	@Override
	public void execute()
	{
		sm.closeAllSessions();
		server.stop();
	}

	@Override
	public void init(LinkedList<String> arg0){}

}
