package database;

public interface IQuery {
	public String getLevelName();
	public String getPlayerName();
	public String getOrderBy();
	public void setLevelName(String levelName);
	public void setPlayerName(String playerName);
	public void setMaxResults(int max);
	public void initOrderBySteps();
	public void initOrderByTime();
	public void initLexiPlayerName();
	public void initLexiLevelName();
	public int getMaxResults();
	public boolean isDESC();
	public void setDESC(boolean desc);
}

