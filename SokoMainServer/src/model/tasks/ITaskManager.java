package model.tasks;

import java.util.List;
import java.util.Observer;

public interface ITaskManager extends Observer
{
	public void addTask(ITask task);
	public void startTasker();
	public void stop();
	public List<ITask> getCurrentTasks();
	public ITask deleteCurrentRunningTask(int taskID);
}
