package view;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import metadata.SessionModel;
import metadata.TaskModel;
import server.Protocol;

public class SokoViewModel extends Observable implements ViewModel
{

	public StringProperty log,adminName,sessionsNumber,tasksNumber;
	public LinkedList<SessionModel> sessions_list;
	public LinkedList<TaskModel> tasks_list;
	private Lock lock;
	private AdminController view;
	public SokoViewModel() 
	{
		view =null;
		lock = new ReentrantLock();
		sessions_list = new LinkedList<>();
		tasks_list = new LinkedList<>();
		log = new SimpleStringProperty();
		adminName = new SimpleStringProperty();
	}
	
	// get updates to change data.
	@Override
	public void update(Observable o, Object arg) 
	{
		LinkedList<String> params = (LinkedList<String>)arg;
		String cmd = params.get(0);
			setChanged();
			notifyObservers(params);
			if(cmd.equals(Protocol.closeAsession))
			{
				log.set(log.get() + "\n" + "*********" + "Killed a Session."+"*********");
			}
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
	public void updateLog(String log) 
	{
		lock.lock();
		this.log.set(this.log.get()+"\n"+log);
		System.out.println("***** log ******* ");
		System.out.println(this.log.get());
		System.out.println("****************** ");
		lock.unlock();
	}
	@Override
	public void updateSessions(LinkedList<SessionModel> sessions, LinkedList<TaskModel> taskModels) 
	{
		sessions_list = sessions;
		this.tasks_list = taskModels;
		if(sessions.size()>0)
		{
			updateObservers(Protocol.closedSession, sessions.get(0).getSessionID(),"success");
		}
		ArrayList<String> binded = new ArrayList<>();
		if(view != null)
		{
			for(SessionModel s : sessions)
			{
				String line = "Session ID:[ " + s.getSessionID()+" ]" + " Details: " + s.getSessionAdress() + " RequestID:"+s.getMetaData().getId();
				binded.add(line);
			}
			for(TaskModel t : taskModels)
			{
				String line = "TaskID: " + t.getTaskID() + " SessionID: " + t.getSessionID() + "RequestID "+ t.getMetadata().getId();
				String dataObject = "TaskID: " + t.getTaskID() + " Executing data model: "+t.getJsonObject();
				binded.add(line);
				binded.add(dataObject);
			}
			view.updateListView(binded);	
		}

	}

	public void addView(AdminController gui) 
	{
		this.view=gui;
		
	}

}
