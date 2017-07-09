package database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Players")
@Table(name="Players", catalog="SokobanDB", schema="DBO")
public class PlayerP implements POJO
{
	@Id
	private String playerName;
	
	public PlayerP(){}
	@Override
	public String toString() 
	{
		return "PlayerP [playerName=" + playerName + "]";
	}
	public PlayerP(String playerName)
	{
		this.playerName=playerName;
	}
	public String getPlayerName()
	{
		return playerName;
	}
	public void setPlayerName(String playerName)
	{
		this.playerName = playerName;
	}
	@Override
	
	public String getName()
	{
	
		return this.getPlayerName();
	}
	
	
}


