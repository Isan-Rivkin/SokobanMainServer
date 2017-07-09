package view;

import java.util.LinkedList;
import java.util.Observer;

import metadata.SessionModel;
import metadata.TaskModel;

public interface ViewModel extends Observer
{
	public void updateLog(String log);
	public void updateSessions(LinkedList<SessionModel> sessions, LinkedList<TaskModel> taskModels);
}
