package model;

import model.tasks.ITaskManager;
import server.SessionManager;
import server.SokoMultiServer;
import solver.MainSolver;

public interface ISSModel 
{
	public ITaskManager getTaskManager();
	public SessionManager getSessionManager();
	public SokoMultiServer getServer();
	public MainSolver getLevelSolver();
}
