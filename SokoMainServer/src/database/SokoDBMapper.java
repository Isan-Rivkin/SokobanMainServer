package database;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
/**
 * IDM Mapper implementation.
 * @author Isan Rivkin and Daniel Hake.3
 *
 */
public class SokoDBMapper implements IDBMapper 
{

	final private static String PLAYERS_TABLE="Players";
	final private static String LEVELS_TABLE="Levels";
	final private static String HIGHSCORE_TABLE="HighScores";
	private SessionFactory factory;

	public SokoDBMapper() 
	{
		Configuration config = new Configuration();
		config.configure();
		factory = config.buildSessionFactory();
	}
	@Override
	public List<HighScoreP> searchHighScore(IQuery query)
	{
		Session session=factory.openSession(); 
		HighScoreP[] res = null;
		Query q=null;
		if(query.getPlayerName() == null && query.getLevelName() != null)
		{
			System.out.println("I enter here level name not null = " + query.getLevelName());
			q = session.createQuery("From " +HIGHSCORE_TABLE + " hs WHERE hs.levelName LIKE :levelName "
					+"ORDER BY hs." + query.getOrderBy());
			q.setParameter("levelName", query.getLevelName());
		}
		else if(query.getLevelName() == null && query.getPlayerName() != null)
		{
			 q = session.createQuery("From "+HIGHSCORE_TABLE+" hs WHERE "
			+ "hs.playerName LIKE :playerName ORDER BY hs."
			+(query.getOrderBy()));
			q.setParameter("playerName", "%"+query.getPlayerName()+"%");
		}
		else
		{
			 q = session.createQuery("From "+HIGHSCORE_TABLE+" hs WHERE hs.levelName LIKE :levelName AND "
			+ "hs.playerName LIKE :playerName ORDER BY hs."
			+(query.getOrderBy()));
			 q.setParameter("playerName", "%"+query.getPlayerName()+"%");
			 q.setParameter("levelName", "%"+query.getLevelName()+"%");
		}
		
		q.setMaxResults(query.getMaxResults());
		List<HighScoreP> list  = (List<HighScoreP>)q.list();
		return list;
	}

	@Override
	public List<LevelP> getAllLevels()
	{
		return (List<LevelP>) this.getAllrows(LEVELS_TABLE);
	}

	@Override
	public List<PlayerP> getAllPlayers()
	{
		return (List<PlayerP>) this.getAllrows(PLAYERS_TABLE);
	}

	@Override
	public List<HighScoreP> getAllHighScores()
	{
		return (List<HighScoreP>) this.getAllrows(HIGHSCORE_TABLE);
	}

	@Override
	public boolean savePOJO(POJO pojo)
	{
		Session session=null;
		Transaction tx=null;
		boolean worked = true;
		try
		{
			
			session=factory.openSession();
			tx=session.beginTransaction();
			session.save(pojo);
			tx.commit();
		}
		catch(HibernateException e)
		{
			if(tx != null)
			{
				tx.rollback();
				worked = false;
			}
		}
		finally
		{
			if(session != null)
			{
				session.close();
			}
		}
		return worked;
	}

	
	@Override
	public boolean deletePOJO(POJO pojo) 
	{
		Transaction tx = null;
		boolean worked = true;
		Session session = null;
		worked =false ;
		if(isEntityExist(pojo) != null)
		{
			worked = true;
		}
		try {
			session = factory.openSession();
			tx = session.beginTransaction();
			if (worked || pojo instanceof HighScoreP)
			{
			session.delete(pojo);
			tx.commit();
			worked=true;
			}
			else 
			{
				worked = false;
			}
		}
		catch(HibernateException he)
		{
			if(tx!=null)
			{
			tx.rollback();
			}
			worked = false;
		}
		finally
		{
			if (session!=null)
			{
			      session.close();
			}
		}
		
		return worked;
	}

	@Override
	public POJO isEntityExist(POJO pojo) 
	{
	
		boolean flag=false;
		Session session=null;
		Transaction tx=null;
		POJO pp=null;
		try
		{
			session=factory.openSession();
			tx=session.beginTransaction();
			if(pojo instanceof HighScoreP)
			{
				System.out.println("[SokoMapper]: Cannot search HighScoreP - id is not String");
				return null;
			}
				pp= session.get(pojo.getClass(),pojo.getName());

			if(pp != null)
			{
				flag=true;
			}
		}
		catch(HibernateException e)
		{
			if(tx!= null)
			{
				tx.rollback();
			}
			e.printStackTrace();
		}
		finally
		{
			if(session != null)
			{
				session.close();
			}
		}
		return pp;
	}

	@Override
	public List<? extends POJO> getAllrows(String tableName) {
		Transaction tx=null;
		Session session = null;
		List<POJO> res = null;
		try
		{
		session=factory.openSession(); 
	     tx=session.beginTransaction();
		Query q = session.createQuery("from "+tableName+" p");
		res  = q.list();
		tx.commit();
	}
	catch(HibernateException e)
	{
		if(tx != null)
		{
			tx.rollback();
		}
	}
	finally
	{
		if(session != null)
		{
			session.close();
		}
	}
		return res;
	}

}
