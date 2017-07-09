package database;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name = "HighScores")
@Table(name="HighScores", catalog="SokobanDB", schema="DBO")
public class HighScoreP implements POJO
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer highScoreID;

	@JoinColumn(name="playerName")
	private String playerName;
	
	@JoinColumn(name="levelName")
	private String levelName;
	
	@Column(name="playerSteps")
	private Integer playerSteps;
	
	@Column(name="playerTime")
	private Long playerTime;
	
	public HighScoreP(){}
	public HighScoreP(String playerName, String levelName ,Integer playerSteps,Long playerTime) 
	{
		this.levelName=levelName;
		this.playerName=playerName;
		this.playerSteps=playerSteps;
		this.playerTime=playerTime;
	}
	@Override
	public String getName() 
	{
		return ""+this.getHighScoreID();
	}
	public Integer getHighScoreID() 
	{
		return highScoreID;
	}
	public void setHighScoreID(Integer highScoreID) 
	{
		this.highScoreID = highScoreID;
	}
	public String getPlayerName() 
	{
		return playerName;
	}
	public void setPlayerName(String playerName) 
	{
		this.playerName = playerName;
	}
	public String getLevelName() 
	{
		return levelName;
	}
	public void setLevelName(String levelName) 
	{
		this.levelName = levelName;
	}
	public Integer getPlayerSteps() 
	{
		return playerSteps;
	}
	public void setPlayerSteps(Integer playerSteps) 
	{
		this.playerSteps = playerSteps;
	}
	public Long getPlayerTime() 
	{
		return playerTime;
	}
	public void setPlayerTime(Long playerTime) 
	{
		this.playerTime = playerTime;
	}


	
	
}
