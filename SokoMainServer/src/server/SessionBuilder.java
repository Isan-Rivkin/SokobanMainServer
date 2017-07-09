package server;

import java.net.Socket;
import java.util.List;

public interface SessionBuilder
{
	public Session getNewSession(Socket socket);
}
