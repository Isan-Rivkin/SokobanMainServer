package database;

import java.util.List;
/**
 * Database DAL - data access layer.
 * @author Isan Rivkin
 *
 */
public interface IDBMapper 
{
	public List<HighScoreP> searchHighScore(IQuery query);
	public List<LevelP> getAllLevels();
	public List<PlayerP> getAllPlayers();
	public List<HighScoreP> getAllHighScores();
	/**
	 * save any kind of table persistent object.
	 * @param pojo
	 * @return
	 */
	public boolean savePOJO(POJO pojo);
	public boolean deletePOJO(POJO pojo);
	List<? extends POJO> getAllrows(String tableName);
	public POJO isEntityExist(POJO pojo);	

}
