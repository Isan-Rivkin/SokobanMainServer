package controller.commands;

import java.util.LinkedList;

import model.ISSModel;
import server.SessionManager;
/**
 * Close an active session (session manager)
 * @author Isan Rivkin and Daniel Hake.
 *
 */
public class CloseASessionCMD implements Command
{

	private ISSModel model;
	private SessionManager sm;
	private String sessionID,successStatus;
	
	public CloseASessionCMD(ISSModel model, SessionManager sm) 
	{	
		this.sm=sm;
		this.model=model;
	}
	@Override
	public void execute() 
	{
		sm.closeSession(sessionID, successStatus);
	}

	//[id,success]
	@Override
	public void init(LinkedList<String> arg0)
	{
		this.sessionID = arg0.removeFirst();
		this.successStatus = arg0.removeFirst();
	}
	
}
