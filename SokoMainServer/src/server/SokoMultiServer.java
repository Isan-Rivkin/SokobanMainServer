package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * Sokoban Multi Server
 * @author Isan Rivkin and Daniel Hake
 *  Uses -> needs a thread numbers limit for the thread pool
 *  Dont know anything about session managment.
 *  Listens for a connections and pass it to the session manager.
 */
public class SokoMultiServer 
{//
	private int port;
	private int threadsLimit;
	private ExecutorService threadPool;
	private volatile boolean stop;
	private SessionManager session_manager;

	public SokoMultiServer(SessionManager session_manager) 
	{
		this.session_manager=session_manager;
		this.threadsLimit = threadsLimit;
		threadPool = Executors.newFixedThreadPool(10);
		this.port=port;
		stop = false;
	}
	private void runServer()
	{
		System.out.println("Alive on port " + port);
		ServerSocket acceptingSocket=null;
		try 
		{
		    acceptingSocket = new ServerSocket(port);
			acceptingSocket.setSoTimeout(1000);
			while(!stop)
			{
				try{
				Socket aClient = acceptingSocket.accept();
				System.out.println("[server:] joined " + aClient.getRemoteSocketAddress());
					threadPool.execute(()->
					{
						session_manager.getNewSession(aClient);
					});
				}
				catch(SocketTimeoutException o){}
			}
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if(acceptingSocket != null)
			{
				try 
				{
					acceptingSocket.close();
				}
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
			System.out.println("[Server:] closed.");
		}
	}
	public void start()
	{
		stop = false;
		new Thread(()->
		{
			runServer();
		}).start();
	}
	public void start(int port , int maxConnections)
	{
		stop = false;
		this.port=port;
		this.threadsLimit=maxConnections;
		new Thread(()->
		{
			runServer();
		}).start();
	}
	public void stop()
	{
		stop=true;
	}
}
