package metadata;

import java.net.SocketAddress;
/**
 * A JSON seriallized session model.
 * @author Isan Rivkin and Daniel Hake
 *
 */
public class SessionModel 
{
	@Override
	public String toString() {
		return "SessionModel [sessionAdress=" + sessionAdress + ", metaData=" + metaData.getId() + ", sessionID=" + sessionID
				+ "]";
	}
	private SocketAddress sessionAdress;
	private METADATA metaData;
	private String sessionID;
	
	public SocketAddress getSessionAdress() {
		return sessionAdress;
	}
	public void setSessionAdress(SocketAddress sessionAdress) {
		this.sessionAdress = sessionAdress;
	}
	public METADATA getMetaData() {
		return metaData;
	}
	public SessionModel(SocketAddress sessionAdress, METADATA metaData, String sessionID) {
		super();
		this.sessionAdress = sessionAdress;
		this.metaData = metaData;
		this.sessionID = sessionID;
	}
	public void setMetaData(METADATA metaData) {
		this.metaData = metaData;
	}
	public String getSessionID() {
		return sessionID;
	}
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
}
