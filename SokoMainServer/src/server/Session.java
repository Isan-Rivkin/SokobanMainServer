package server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketAddress;
import java.util.Observer;

import javafx.beans.Observable;
import metadata.METADATA;
import metadata.SessionModel;
import model.tasks.ITask;

public interface Session
{
	public void stop();
	public InputStream getClientInputStream();
	public OutputStream getClientOutputStream();
	public void setServerStreams(InputStream in, OutputStream out);
	public void startSession();
	public int getSessionID();
	public boolean isSessionActive();
	public void notifyTaskReady(ITask t);
	public void addManager(Observer observer);
	public METADATA getMetaData();
	public SocketAddress getSocketAdress();
	public SessionModel getModel();
}
