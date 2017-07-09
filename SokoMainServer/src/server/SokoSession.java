package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ArrayBlockingQueue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import metadata.METADATA;
import metadata.SessionModel;
import model.tasks.ITask;

public class SokoSession extends Observable implements Session
{

	private Socket socket;
	private BufferedReader readFromClient;
	private PrintWriter writeToClient;
	private boolean activeSession;
	private int id;
	private ArrayBlockingQueue<ITask> sessionTasks;
	private GsonBuilder gsonBuilder;
	private Gson gson;
	private SocketAddress socketAdress;
	private METADATA metaDataObj;
	
	
	public SokoSession(Socket socket , InputStream serverInput, OutputStream serverOutPut, int id) 
	{

			this.id=id;
			socketAdress = socket.getRemoteSocketAddress();
			this.socket=socket;
			sessionTasks=new ArrayBlockingQueue<>(50);
			this.activeSession = true;
			metaDataObj = null;
			try 
			{
				writeToClient = new PrintWriter(socket.getOutputStream());
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			gsonBuilder = new GsonBuilder();	
			gson = gsonBuilder.create();
			startSession();		
			setChanged();
			updateObservers(Protocol.sessionStarted,""+id);
	}

	@Override
	public void stop() 
	{
		this.activeSession=false;
		try
		{
			writeToClient.close();
			readFromClient.close();
			socket.close();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		
	}

	@Override
	public InputStream getClientInputStream() 
	{
		try
		{
			return socket.getInputStream();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public OutputStream getClientOutputStream()
	{
		try 
		{
			return socket.getOutputStream();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void setServerStreams(InputStream in, OutputStream out) 
	{
		// TODO Auto-generated method stub
		
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
	public void startSession() 
	{
		
		new Thread(()->
		{
			InputStream is = getClientInputStream();
			readFromClient = new BufferedReader(new InputStreamReader(is));
			while(activeSession && !socket.isClosed() && socket.isConnected())
			{
				try 
				{
					/**
					 *  command protocol: {"addTask",sessionID,"METADATA","OBJECT"}
					 */	
					// get the metadata from the client-
					String metaDataString = readFromClient.readLine();

					metaDataObj = gson.fromJson(metaDataString, METADATA.class);
					// find request id
					String objectJson = readFromClient.readLine();
					updateObservers(Protocol.addTask,""+id,metaDataString,objectJson);
					// wait for task to finish.
					ITask t=null;
					try 
					{
						System.out.println("[Server:] blocked.");
						t = sessionTasks.take();
						System.out.println("[Server:] unblocked.");
					}
					catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
					if(t==null)
					{
						System.out.println("[Session:] Task returned null");
					}
					// the result is a json oject.
					writeToClient.println(metaDataString);
					writeToClient.flush();
					writeToClient.println(t.getTaskJSONResult());
					writeToClient.flush();
					String failed_status=Protocol.failure_task;
					if(!t.getTaskJSONResult().equals(""))
						failed_status=Protocol.success_task;
					updateObservers(Protocol.closedSession,id+"",failed_status);
					updateObservers(Protocol.newTaskAndSessionUpdate);
					
				}
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		}).start();;	
		}

	@Override
	public int getSessionID() 
	{
		return id;
	}

	@Override
	public boolean isSessionActive() 
	{
		return activeSession;
	}

	@Override
	public void notifyTaskReady(ITask t)
	{
		sessionTasks.add(t);
	}

	@Override
	public void addManager(Observer observer) 
	{
		this.addObserver(observer);
		
	}

	@Override
	public METADATA getMetaData()
	{
		return metaDataObj;
	}

	@Override
	public SocketAddress getSocketAdress() 
	{
		return socketAdress;
	}

	@Override
	public SessionModel getModel() 
	{
		return new SessionModel(socketAdress, metaDataObj, id+"");
	}

}
