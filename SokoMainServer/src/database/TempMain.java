package database;

import java.util.List;

public class TempMain {

	
	public static void main(String[] args) {
		
	//	DBHighScoresManager manager = new DBHighScoresManager();
		 //manager.savePlayer(new PlayerP("Hake The Daniel"));
		//	manager.saveLevel(new LevelP( "FinalObjTestLevel"));
	 //  manager.saveHighscore(new HighScoreP(666, 16, 11, 165245));
		//manager.saveToDB(new Student(199, "obj name", "obh l name", 999));
	//	SokoDBMapper mapper= new SokoDBMapper();
	//	mapper.savePOJO(new PlayerP("Haimke shlomi"));
		//mapper.savePOJO(new PlayerP("Daniel Yohpaz"));
	//	mapper.savePOJO(new PlayerP("Daniel Cohen"));
//		List<PlayerP> p=mapper.getAllPlayers();
//		for(PlayerP pp : p)
//		{
//			System.out.println(p.toString());
//			mapper.deletePOJO(pp);
//		}		
//
//		mapper.savePOJO(new LevelP("testlevel1", "testPath"));
//		mapper.savePOJO(new HighScoreP("Haimke shlomi", "testlevel1", new Integer(51), new Long(343)));
//         mapper.savePOJO(new LevelP("testlevel2", "testpath//2"));
//         mapper.savePOJO(new PlayerP("testPlayer 2"));
//         mapper.savePOJO(new HighScoreP("testPlayer 2", "testlevel2",new Integer(666), new Long(6520)));
//         mapper.savePOJO(new HighScoreP("testPlayer 2", "testlevel2",new Integer(1), new Long(1000)));
//         mapper.savePOJO(new HighScoreP("testPlayer 2", "testlevel2",new Integer(2), new Long(2000)));
//         mapper.savePOJO(new HighScoreP("testPlayer 2", "testlevel2",new Integer(3), new Long(3000)));
////       
//		mapper.savePOJO(new LevelP("testlevel3", "path path"));
//        mapper.savePOJO(new PlayerP("testplayer3"));
//		mapper.savePOJO(new HighScoreP("testplayer3", "testlevel3",new Integer(3), new Long(9999)));
		//boolean urMAMA=mapper.deletePOJO(new HighScoreP("testPlayer 2", "testlevel2",new Integer(3), new Long(9999)));
//		boolean urMAMA=mapper.isEntityExist(new HighScoreP("testplayer3", "testlevel3",new Integer(3), new Long(9999)));
//         if(urMAMA)
//        	 System.out.println("UR MAMA");
//         else
//        	 System.err.println("!UR MAMA");
//		
		//***********
//		IQuery query= new HighScoreQuery();
//		//query.setPlayerName("testPlayer 2");
//		query.setLevelName("testlevel2");
//		query.initOrderBySteps();
//		query.setMaxResults(50);
//		HighScoreP[] list= mapper.searchHighScore(query);
//		System.out.println(list.length);
//		for(int i=0; i<list.length;++i)
//		{
//			System.out.println(list[i].getPlayerSteps());
//		}
//		
////		
//		SokoDBMapper mapper = new SokoDBMapper();
//		IDataManager data_manager = new HSDataManager(mapper);
//		data_manager.save(new LevelP("delete","delete.txt"));
//		
//	data_manager.signUpHighScore("shlomi", "delete", new Integer(1), new Long(1666666));
		//data_manager.signUpHighScore("shlomi", "testlevel1", new Integer(166), new Long(16));
		//data_manager.deleteQuery(new PlayerP(), "shlomi");
//		List<HighScoreP> list = data_manager.search("p", "shlomi", "time");
//        for(HighScoreP hs : list)
//		{
//			System.out.println("TIME : " + hs.getPlayerTime());
//		}
//		
		
		SokoDBMapper mapper = new SokoDBMapper();
		mapper.savePOJO(new HighScoreP("isan", "level1", new Integer(666), new Long(666)));
		//{"levelName":"testXML","maxResults":50,"orderBy":"levelName asc","desc":false}
		HighScoreQuery q = new HighScoreQuery();
		q.setLevelName("testXML");
		q.setMaxResults(50);
		List<HighScoreP> pojs = mapper.searchHighScore(q);
		for(HighScoreP p : pojs)
		{
			System.out.println("got one1");
		}
		
	}

}
