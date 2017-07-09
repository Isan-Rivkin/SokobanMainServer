package server;

import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import metadata.METADATA;
import metadata.SessionModel;
import model.tasks.ITask;

public class SokoSessionManager extends Observable implements SessionManager, Observer 
{
	private SessionBuilder sb;
	private List<Session> sessions;
	private Lock lock;
	public SokoSessionManager(SessionBuilder sb) 
	{
		this.lock=new ReentrantLock();
		this.sessions = new ArrayList<>();
		this.sb = sb;
	}
	
	@Override
	public List<Session> getAllSessions() 
	{
		return sessions;
	}

	@Override
	public void setAllSessions(List<Session> sessions) 
	{
		this.sessions = sessions;
	}

	@Override
	public void closeSession(String id,String success_stats) 
	{
		for(int i=0;i<sessions.size();++i)
		{
			Session s  = sessions.get(i);
			String tempId = s.getSessionID()+"";
			if(id.equals(tempId))
			{
				s.stop();
				sessions.remove(i);
				updateObservers(Protocol.closedSession,""+id,success_stats);
				updateObservers(Protocol.logUpdate,"[Session MAnager:] Session ID = " + s.getSessionID() + " closed.");
				updateObservers(Protocol.newTaskAndSessionUpdate);
				break;
			}
		}
	}

	@Override
	public void addSessionToList(Session session) 
	{
		
	}
	private void updateObservers(String ...strings)
	{
		List<String> params = new LinkedList<String>();
		int k=0;
		for(String st : strings)
		{
			params.add(st);
			k++;
		}
		setChanged();
		notifyObservers(params);
	}

	@Override
	public Session getNewSession(Socket socket) 
	{
		Session newSession = sb.getNewSession(socket);
		newSession.addManager(this);
		sessions.add(newSession);
		updateObservers(Protocol.newSession,""+newSession.getSessionID());
		return newSession;
	}

	@Override
	public void startSession(int sessionID) 
	{
		for(int i=0;i<sessions.size();++i)
		{
			Session s = sessions.get(i);
			if(s.getSessionID() == sessionID)
			{
				s.startSession();
			}
		}
	}

	// handle all notify from the sessions
	// some will just go up to the controller for example - do a task 
	@Override
	public void update(Observable o, Object arg)
	{
		LinkedList<String> params = (LinkedList<String>)(arg);
		String command = params.get(0);
		switch(command)
		{
		case Protocol.closedSession:
		{
			closeSession(params.get(1),params.getLast());
		}
		case Protocol.addTask:
		{
			setChanged();
			notifyObservers(arg);
			break;
		}
		}
	}

	@Override
	public void notifyTaskFinished(ITask task) 
	{
		lock.lock();
		for(int i=0;i<sessions.size();++i)
		{
			Session s = sessions.get(i);
			String id = s.getSessionID()+"";
			if(task.getSessionID().equals(id))
			{
				s.notifyTaskReady(task);
			}
		}
		lock.unlock();
		
	}

	@Override
	public void closeAllSessions() 
	{
		for(Session s : sessions)
		{
			closeSession(s.getSessionID()+"", Protocol.unkown_task);
		}
		System.out.println("[SessionManager:] All sessions are closed.");
		updateObservers(Protocol.newTaskAndSessionUpdate);
		updateObservers(Protocol.logUpdate,"[Session Manager] All sessions are closed.");
	}

	@Override
	public SocketAddress getSessionAdress(String id) 
	{
		for(Session s : sessions)
		{
			if(s.getSessionID() == Integer.parseInt(id))
			{
				return s.getSocketAdress();
			}
		}
		return null;
	}

	@Override
	public METADATA getSessionMetaData(String id) 
	{	
		for(Session s : sessions)
		{
			if(s.getSessionID() == Integer.parseInt(id))
			{
				return s.getMetaData();
			}
		}
		return null;
	}

	@Override
	public SessionModel getSessionModel(String id)
	{
		for(Session s : sessions)
		{
			if(id.equals(s.getSessionID()+""))
			{
				return s.getModel();
			}
		}
		return null;
	}

	@Override
	public List<SessionModel> getAllSessionModels()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
