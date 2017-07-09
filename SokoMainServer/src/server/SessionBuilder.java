package server;

import java.net.Socket;
import java.util.List;
/**
 * Build a session class using a socket.
 * @author Isan Rivkin
 *
 */
public interface SessionBuilder
{
	public Session getNewSession(Socket socket);
}
