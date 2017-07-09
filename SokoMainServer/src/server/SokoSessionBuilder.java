package server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

public class SokoSessionBuilder implements SessionBuilder
{
	private InputStream inToServer;
	private OutputStream outToServer;
	private static int Id;
	
	
	public SokoSessionBuilder(InputStream inToServer , OutputStream outToServer) 
	{
		Id=0;
		this.inToServer=inToServer;
		this.outToServer=outToServer;
	}

	@Override
	public Session getNewSession(Socket socket) 
	{
		Session newSession = new SokoSession(socket, inToServer,outToServer,Id);
		Id++;
		return newSession;
	}
}
