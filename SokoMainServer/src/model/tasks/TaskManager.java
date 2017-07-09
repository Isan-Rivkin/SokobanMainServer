package model.tasks;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import metadata.TaskModel;
import server.Protocol;

public class TaskManager extends Observable implements ITaskManager
{
	private ArrayBlockingQueue<ITask> tasks_queue;
	private boolean keepRunning;
	private ArrayList<ITask> currentlyRunningTasks;
	private Lock lock;
	private GsonBuilder gsonBuilder;
	private Gson gson;
	
	public TaskManager() 
	{
		this.gsonBuilder = new GsonBuilder();
		this.gson = gsonBuilder.create();
		this.lock=new ReentrantLock();
		this.currentlyRunningTasks=new ArrayList<>();
		this.keepRunning=false;
		this.tasks_queue=new ArrayBlockingQueue<>(150);
	}
	@Override
	public void addTask(ITask task) 
	{
		lock.lock();
		task.addManager(this);
		this.tasks_queue.add(task);
		updateObservers(Protocol.newTaskAndSessionUpdate);
		updateObservers(Protocol.logUpdate,"[New Task] SessionId: " + task.getSessionID() + " TaskID: "+task.getTaskID() + " Protocol: "+task.getProtocolID());
		lock.unlock();
	}

	@Override
	public void startTasker() 
	{
		lock.lock();
		this.keepRunning=true;
		tasksExecutor();
		lock.unlock();
	}

	@Override
	public void stop()
	{
		lock.lock();
		keepRunning =false;
		lock.unlock();
	}

	@Override
	public List<ITask> getCurrentTasks() 
	{
		return currentlyRunningTasks;
	}

	// task updates when finishes
	//updateObservers(Protocol.restClientFinished,taskID,sessionID,gson.toJson(metadata),jsonObject,jsonResult);
	@Override
	public void update(Observable o, Object arg) 
	{
		lock.lock();
		LinkedList<String> params = (LinkedList<String>)arg;
		int finishedTaskID = Integer.parseInt(params.get(1));
		ITask finishedTask =deleteCurrentRunningTask(finishedTaskID);
		TaskModel model = finishedTask.getModel();
		String taskObjectJson= gson.toJson(model);	
		params.addLast(taskObjectJson);
		setChanged();
		notifyObservers(params);
		lock.unlock();
	}
	private void tasksExecutor()
	{
			new Thread(()->
			{
				while(keepRunning)
				{
					ITask task=null;
					try 
					{
						task = tasks_queue.take();
					}
					catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
					currentlyRunningTasks.add(task);
					task.execute();
				}
			}).start();
	}
	@Override
	public ITask deleteCurrentRunningTask(int taskID) 
	{
			for(int i=0;i<currentlyRunningTasks.size();++i)
			{
				if(currentlyRunningTasks.get(i).getTaskID() == taskID)
				{
					ITask t =currentlyRunningTasks.remove(i);
					return t;
				}
			}
			return null;
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

}
