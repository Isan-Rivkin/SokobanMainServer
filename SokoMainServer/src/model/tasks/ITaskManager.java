package model.tasks;

import java.util.List;
import java.util.Observer;
/**
 * Task Manager for the admin to use.
 * @author Isan Rivkin and Daniel Hake.
 *
 */
public interface ITaskManager extends Observer
{
	public void addTask(ITask task);
	public void startTasker();
	public void stop();
	public List<ITask> getCurrentTasks();
	public ITask deleteCurrentRunningTask(int taskID);
}
