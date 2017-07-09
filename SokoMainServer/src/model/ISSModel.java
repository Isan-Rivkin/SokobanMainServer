package model;

import model.tasks.ITaskManager;
import server.SessionManager;
import server.SokoMultiServer;
import solver.MainSolver;
/**
 * Main Model working as bundle mostly for other components.
 * Interracts with the controller.
 * @author Isan Rivkin and Daniel Hake
 *
 */
public interface ISSModel 
{
	/**
	 * The task manager componenet.
	 * @return
	 */
	public ITaskManager getTaskManager();
	/**
	 * Session manager component
	 * @return
	 */
	public SessionManager getSessionManager();
	/**
	 * server component
	 * @return
	 */
	public SokoMultiServer getServer();
	/**
	 * solver components
	 * @return
	 */
	public MainSolver getLevelSolver();
}
