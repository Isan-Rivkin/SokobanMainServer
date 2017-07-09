package model.restclient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import common_data.level.Level;
import database.HighScoreP;
import database.HighScoreQuery;
import database.IDBMapper;
import database.LevelP;
import database.PlayerP;
import database.SokoDBMapper;
import metadata.DBLevelModel;
import metadata.FMGenerator;
import metadata.HSQueryModel;
import metadata.HighScoreModel;
import metadata.LevelModel;
import metadata.METADATA;
import metadata.PlayerModel;
import metadata.SolutionModel;
import metadata.SolutionQuery;
import model.HuffmanCompression.Coder;
import planning.plannable.SokoHeuristics;
import searchable.Action;
import searchable.Solution;
import server.Protocol;
import solver.MainSolver;
/**
 * IRestClient implementation.
 * used by the ITask class to execute client requests.
 * @author Isan Rivkin and Daniel Hake
 *
 */
public class RestClient extends Observable implements IRestClient,Serializable 
{

	private Observer observer;
	private String sessionID;
	private String jsonObject,jsonResult;
	private METADATA metadata;
	private Gson gson;
	private GsonBuilder gsonBuilder;
	
	public RestClient() {}
	public RestClient(Observer observer, String sessionID, METADATA metadata, String jsonObject) 
	{
		this.gsonBuilder = new GsonBuilder();
		this.gson=gsonBuilder.create();
		this.observer=observer;
		this.sessionID=sessionID;
		this.metadata=metadata;
		this.jsonObject=jsonObject;
		this.jsonResult="";
		this.addObserver(observer);
	}
	/** 
	 * 	public final static String SOLVE_LEVEL ="1";
		public final static String HS_SEARCH ="2";
		public final static String HS_SIGNUP_HS ="3";
		public final static String HS_DELETE_POJO ="4";
		public final static String HS_SAVE_POJO ="5";
		public final static String HS_GET_CURRENT_HS="6";
		public final static int HS_SEARCH_LEN=4;
		public final static String TEST_NETWORK ="7";
	 */
	@Override
	public void executeTask() 
	{
		IDBMapper mapper = new SokoDBMapper();
		
		// generate a request.

		String requestID = metadata.getId();
		switch(requestID)
		{
			case Protocol.SOLVE_LEVEL:
			{
				Coder coder = new Coder();
				LevelModel levelModel = gson.fromJson(jsonObject, LevelModel.class);
				SolutionQuery solQuery = new SolutionQuery();
				solQuery.setLevelMap(coder.levelEncoder(levelModel.getMap()));
				solQuery.setMaxResults(1);
				// create REST request for solution
				/**
				 * REST request- ask for SolutionModel , send solQuery
				 */
				RESTRequester restRequester = new RESTRequester();
				SolutionModel sm = restRequester.requestFullSolutionService(Protocol.restServiceDomain,coder.levelEncoder(levelModel.getMap()));
				 //no solution
				if(sm.getLevelSolution().length()==0 || Protocol.caching)
				{
					LogDescriptorTxt.writeLogTxt("./LocalTestFiles/cacheVerify.txt", "did not find soltion - solving ");
					// solve the level
					Level realLevel =FMGenerator.generateLevelFromModel(levelModel);
					Solution sol =solveLevel(realLevel);
					jsonResult= gson.toJson(sol);
					//update the rest service
					String zippedLevelMap = coder.levelEncoder(realLevel.getCharGameBoard());
					String zippedLevelSulution = coder.solutionEncoder(sol.getTheSolution());
					SolutionModel solutionModel = new SolutionModel(zippedLevelMap, zippedLevelSulution, sol.getTheSolution().size());
					//restRequester.postLevelSolutionModel(Protocol.restServiceDomain, solutionModel);
					
				}
				else
				{
					LogDescriptorTxt.writeLogTxt("./LocalTestFiles/cacheVerify.txt", "found solution! ");					// there is a result - update the client.
					List<Action> actions = coder.solutionDecoder(sm.getLevelSolution());
					Solution solution = new Solution((LinkedList<Action>)actions);
					jsonResult= gson.toJson(solution);
				}
				break;
			}
			case Protocol.HS_SEARCH:
			{
				HSQueryModel model = gson.fromJson(jsonObject, HSQueryModel.class);
				System.out.println(jsonObject);
				HighScoreQuery query = new HighScoreQuery(model.getLevelName(), model.getPlayerName(), model.getMaxResults(), model.getOrderBy(),model.isDESC());
				List<HighScoreP> pojos_list =mapper.searchHighScore(query);
				List<HighScoreModel> models_list = new ArrayList<>();
				for(HighScoreP p : pojos_list)
				{
					models_list.add(new HighScoreModel(p.getPlayerName(), p.getLevelName(),p.getPlayerSteps(), p.getPlayerTime()));
				}
				jsonResult = gson.toJson(models_list);
				System.out.println(jsonResult);
				break;
			}
			case Protocol.HS_SIGNUP_P:
			{
				PlayerModel model = gson.fromJson(jsonObject, PlayerModel.class);
				PlayerP pojo = new PlayerP(model.getPlayerName());
				signUpPlayerToDB(mapper, pojo);
				jsonResult = jsonObject;
				break;
			}
			case Protocol.HS_SIGNUP_HS:
			{
				HighScoreModel model = gson.fromJson(jsonObject, HighScoreModel.class);
				HighScoreP pojo = new HighScoreP(model.getPlayerName(), model.getLevelName(), model.getPlayerSteps(), model.getPlayerTime());
				if(mapper.isEntityExist(new LevelP(model.getLevelName(),""))==null)
				{
					mapper.savePOJO(new LevelP(model.getLevelName(), ""));
				}
				PlayerP p = new PlayerP(model.getPlayerName());
				signUpPlayerToDB(mapper, p);
				mapper.savePOJO(pojo);
				jsonResult = jsonObject;
				break;
			}
			case Protocol.HS_DELETE_POJO:
			{
				String pojoType = metadata.getAdditionalInfo();
				switch(pojoType)
				{
				case Protocol.POJO_HS:
					{
						HighScoreModel model = gson.fromJson(jsonObject, HighScoreModel.class);
						HighScoreP pojo = new HighScoreP(model.getPlayerName(), model.getLevelName(), model.getPlayerSteps(), model.getPlayerTime());
						mapper.deletePOJO(pojo);
						break;
					}
				case Protocol.POJO_LEVEL:
					{
						DBLevelModel model =gson.fromJson(jsonObject, DBLevelModel.class);
						LevelP pojo = new LevelP(model.getLevelName(), model.getLevelPath());
						mapper.deletePOJO(pojo);
						break;
					}
				case Protocol.POJO_PLAYER:
				{
					PlayerModel model = gson.fromJson(jsonObject, PlayerModel.class);
					PlayerP pojo = new PlayerP(model.getPlayerName());
					mapper.deletePOJO(pojo);
					break;
				}
				}
				jsonResult=jsonObject;
				break;
			}
			case Protocol.HS_SAVE_POJO:
			{
				String pojoType = metadata.getAdditionalInfo();
				switch(pojoType)
				{
				case Protocol.POJO_HS:
					{
						HighScoreModel model = gson.fromJson(jsonObject, HighScoreModel.class);
						HighScoreP pojo = new HighScoreP(model.getPlayerName(), model.getLevelName(), model.getPlayerSteps(), model.getPlayerTime());
						mapper.savePOJO(pojo);
						break;
					}
				case Protocol.POJO_LEVEL:
					{
						DBLevelModel model =gson.fromJson(jsonObject, DBLevelModel.class);
						LevelP pojo = new LevelP(model.getLevelName(), model.getLevelPath());
						mapper.savePOJO(pojo);
						break;
					}
				case Protocol.POJO_PLAYER:
				{
					PlayerModel model = gson.fromJson(jsonObject, PlayerModel.class);
					PlayerP pojo = new PlayerP(model.getPlayerName());
					signUpPlayerToDB(mapper,pojo);
					break;
				}
				}
				jsonResult=jsonObject;
			}
			case Protocol.TEST_NETWORK:
			{
				LevelModel l = gson.fromJson(jsonObject, LevelModel.class);
				jsonResult = gson.toJson(l);
				break;
			}
		}
		updateObservers(sessionID,gson.toJson(metadata),jsonObject,jsonResult);
	}
	private void signUpPlayerToDB(IDBMapper mapper, PlayerP playerPojo)
	{
		
		if(mapper.isEntityExist(playerPojo) == null)
		{
			mapper.savePOJO(playerPojo);
		}
	}
	private void updateObservers(String ...strings)
	{
		List<String> params = new LinkedList<String>();
		int k=0;
		for(String st : strings)
		{
			params.add(st);
			k++;
		}
		setChanged();
		notifyObservers(params);
	}
	public Observer getObserver() 
	{
		return observer;
	}
	public void setObserver(Observer observer) 
	{
		this.observer = observer;
	}
	public String getSessionID() 
	{
		return sessionID;
	}
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	public String getJsonObject() {
		return jsonObject;
	}
	public void setJsonObject(String jsonObject) {
		this.jsonObject = jsonObject;
	}
	public String getJsonResult() {
		return jsonResult;
	}
	public void setJsonResult(String jsonResult) {
		this.jsonResult = jsonResult;
	}
	public METADATA getMetadata() {
		return metadata;
	}
	public void setMetadata(METADATA metadata) {
		this.metadata = metadata;
	}
	public Gson getGson() {
		return gson;
	}
	public void setGson(Gson gson) 
	{
		this.gson = gson;
	}
	public GsonBuilder getGsonBuilder() 
	{
		return gsonBuilder;
	}
	public void setGsonBuilder(GsonBuilder gsonBuilder)
	{
		this.gsonBuilder = gsonBuilder;
	}
	
	public Solution solveLevel(Level level)
	{
		MainSolver solver = new MainSolver(new SokoHeuristics());
		solver.setLevel(level);
		solver.asyncSolve();
		
		Solution sol = solver.saveSolution();
		
		if(sol.getTheSolution().isEmpty())
			System.out.println("[RestSolver:] Solution not found");
		return sol;
	}

}
