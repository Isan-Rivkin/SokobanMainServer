package server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketAddress;
import java.util.Observer;

import javafx.beans.Observable;
import metadata.METADATA;
import metadata.SessionModel;
import model.tasks.ITask;
/**
 * 
 * @author Isan Rivkin and Daniel Hake
 * An interface describing a session with the sokoban client.
 * All communications will be inside the session start session method.
 */
public interface Session
{
	/**
	 * Kill the session
	 */
	public void stop();
	public InputStream getClientInputStream();
	public OutputStream getClientOutputStream();
	public void setServerStreams(InputStream in, OutputStream out);
	/**
	 * opens a new thread for the session communication
	 */
	public void startSession();
	/**
	 * unique session id
	 * @return int
	 */
	public int getSessionID();
	/**
	 * is session still happening or client dissapeard.
	 * @return
	 */
	public boolean isSessionActive();
	/**
	 * Method to notify the blocking call from startSession() that the requested
	 * Task by client is ready with its results.
	 * @param t
	 */
	public void notifyTaskReady(ITask t);
	public void addManager(Observer observer);
	/**
	 * Request Metada Protocol
	 * @return
	 */
	public METADATA getMetaData();
	public SocketAddress getSocketAdress();
	/**
	 * create a model that can be serialized to json holding all nessceary data.
	 * @return
	 */
	public SessionModel getModel();
}
