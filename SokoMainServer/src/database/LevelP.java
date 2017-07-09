package database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity (name = "Levels")
@Table(name="Levels", catalog="SokobanDB", schema="DBO")
public class LevelP implements POJO 
{	
	@Id
	private String levelName;
	
	@Column(name="serializedLevel")
	private String serializedLevel;
	
	@Column(name="levelMinSteps")
	private Integer levelMinSteps;
	
	@Column(name="levelPath")
	private String levelPath;
	
	public LevelP(){}
	public LevelP(String levelName, String levelPath)
	{
		this.levelName=levelName;
		serializedLevel="";
		levelMinSteps=null;
		this.levelPath=levelPath;
	}
	
	@Override
	public String getName() 
	{
		return this.getLevelName();
	}
	public String getLevelPath()
	{
		return levelPath;
	}
	public void setLevelPath(String levelPath) 
	{
		this.levelPath = levelPath;
	}
	@Override
	public String toString() 
	{
		return "LevelP [levelName=" + levelName + ", serializedLevel=" + serializedLevel + ", levelMinSteps="
				+ levelMinSteps + ", levelPath=" + levelPath + "]";
	}
	public String getLevelName() 
	{
		return levelName;
	}
	public void setLevelName(String levelName)
	{
		this.levelName = levelName;
	}
	public String getSerializedLevel() 
	{
		return serializedLevel;
	}
	public void setSerializedLevel(String serializedLevel)
	{
		this.serializedLevel = serializedLevel;
	}
	public Integer getLevelMinSteps() 
	{
		return levelMinSteps;
	}
	public void setLevelMinSteps(Integer levelMinSteps)
	{
		this.levelMinSteps = levelMinSteps;
	}
}
