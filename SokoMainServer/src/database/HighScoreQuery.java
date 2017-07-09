package database;

public class HighScoreQuery implements IQuery
{

	private String levelName;
	private String playerName;
	private int maxResults;
	private String orderBy;
	public HighScoreQuery(String levelName, String playerName, int maxResults, String orderBy, boolean desc) {
		super();
		this.levelName = levelName;
		this.playerName = playerName;
		this.maxResults = maxResults;
		this.orderBy = orderBy;
		this.desc = desc;
	}
	private boolean desc;
	
	public HighScoreQuery()
	{
		levelName=null;
		playerName=null;
		orderBy="playerTime asc";
	}
	@Override
	public String getLevelName() 
	{
		return levelName;
	}

	@Override
	public String getPlayerName()
	{
		return playerName;
	}

	@Override
	public String getOrderBy() 
	{
		return orderBy;
	}

	@Override
	public void setLevelName(String levelName)
	{
		this.levelName=levelName;
	}

	@Override
	public void setPlayerName(String playerName) 
	{
		this.playerName=playerName;
	}

	@Override
	public void setMaxResults(int max) 
	{
		this.maxResults=max;
	}

	@Override
	public void initOrderBySteps() 
	{
		orderBy="playerSteps asc";
	}

	@Override
	public void initOrderByTime() 
	{
		orderBy="playerTime asc";
	}

	@Override
	public int getMaxResults() 
	{
		return maxResults;
	}

	@Override
	public boolean isDESC()
	{
		return desc;
	}

	@Override
	public void setDESC(boolean desc)
	{
		this.desc=desc;
	}
	@Override
	public void initLexiPlayerName() 
	{
		orderBy="playerName asc";
	}
	@Override
	public void initLexiLevelName() 
	{
		orderBy="levelName asc";
	}

}
