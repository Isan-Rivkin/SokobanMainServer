package server;

import java.net.Socket;
import java.net.SocketAddress;
import java.util.List;

import metadata.METADATA;
import metadata.SessionModel;
import model.tasks.ITask;
/**
 * 
 * @author Isan Rivkin and Daniel Hake
 * Stores all the sessions and manages them.
 * Every communication with a session from outside is only
 * via this session manager class used with unique sessionID.
 *
 */
public interface SessionManager 
{
	/**
	 * current active sessions
	 * @return
	 */
	public List<Session> getAllSessions();
	public void setAllSessions(List<Session> sessions);
	/**
	 * close an active session.
	 * @param id - session id
	 * @param success - status of task flags inside Protocol.java 
	 */
	public void closeSession(String id,String success);
	public void addSessionToList(Session session);
	/**
	 * via the session builder.
	 * @param socket
	 * @return
	 */
	public Session getNewSession(Socket socket);
	public void startSession(int sessionID);
	/**
	 * notify a specific Session that a task is finished.
	 * task is holding session id inside.
	 * @param task
	 */
	public void notifyTaskFinished(ITask task);
	public void closeAllSessions();
	public SocketAddress getSessionAdress(String id);
	public METADATA getSessionMetaData(String id);
	/**
	 * Session model are class for holding data required for a session.
	 * the models are JSON initializables.
	 * @param id
	 * @return
	 */
	public SessionModel getSessionModel(String id);
	public List<SessionModel> getAllSessionModels();
}
