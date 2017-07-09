package controller.commands;

import java.util.LinkedList;

import model.ISSModel;
import server.SokoMultiServer;
/**
 * Run the server.
 * @author Isan Rivkin and Daniel Hake.
 *
 */
public class StartServerCMD implements Command
{

	private String stringPort;
	private String stringMaxConnections;
	private int port;
	private int maxConnection;
	private ISSModel model;
	private SokoMultiServer server;
	
	public StartServerCMD(ISSModel model ,SokoMultiServer server) 
	{
		this.model=model;
		this.server=server;
	}
	@Override
	public void execute() 
	{
		port = Integer.valueOf(stringPort);
		maxConnection = Integer.valueOf(stringMaxConnections);
		server.start(port, maxConnection);
	}
//start server {cmd,port,max connections=threads}
	@Override
	public void init(LinkedList<String> arg0)
	{
		stringPort = arg0.removeFirst();
		stringMaxConnections = arg0.removeFirst();
	}

}
