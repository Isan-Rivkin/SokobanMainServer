package server;

import java.net.Socket;
import java.net.SocketAddress;
import java.util.List;

import metadata.METADATA;
import metadata.SessionModel;
import model.tasks.ITask;

public interface SessionManager 
{
	public List<Session> getAllSessions();
	public void setAllSessions(List<Session> sessions);
	public void closeSession(String id,String success);
	public void addSessionToList(Session session);
	public Session getNewSession(Socket socket);
	public void startSession(int sessionID);
	public void notifyTaskFinished(ITask task);
	public void closeAllSessions();
	public SocketAddress getSessionAdress(String id);
	public METADATA getSessionMetaData(String id);
	public SessionModel getSessionModel(String id);
	public List<SessionModel> getAllSessionModels();
}
