package server;

import java.util.List;

import model.database.HighScoreP;
import model.database.HighScoreQuery;
import model.database.IQuery;

public class Protocol
{
	/************************
	 * server config*****
	 *************************/
	public final static String serverIP = "127.0.0.1";
	public final static int serverPort = 9876;
	/************************
	 * REST config      *****
	 *************************/
	public final static String restServiceDomain = "http://localhost:8080";
	public final static String getSolutionPath = "/SokoREST/solver/get/";
	public static final String postSolutionPath ="/SokoREST/updatesolver/get/";
	public static final boolean caching = true;
	
	/************************
	 * networkd protocol*****
	 *************************/
	public final static String SOLVE_LEVEL ="1";
	public final static String HS_SEARCH ="2";
	public final static String HS_SIGNUP_HS ="3";
	public final static String HS_DELETE_POJO ="4";
	public final static String HS_SAVE_POJO ="5";
	public final static String HS_GET_CURRENT_HS="6";
	public final static String HS_SIGNUP_P = "8";
	public final static int HS_SEARCH_LEN=4;
	public final static String TEST_NETWORK ="7";
    public final static String POJO_HS ="phs";
    public final static String POJO_LEVEL ="pl";
	public final static String POJO_PLAYER ="pp";
	/************************
	 * commands protocol*****
	 *************************/
	// start server {cmd,port,max connections=threads}
	public final static String startServer ="startServer";
	// stop server [cmd]
	public final static String stopServer="stopServer";
	// params : {"addTask",sessionID,"METADATA","OBJECT"}
	public final static String addTask = "addTask";
	public final static String success_task = "success";
	public final static String failure_task = "failure";
	public final static String unkown_task = "unkown";
	// some session is close
	//{cmd,id,success/failure status}
	public final static String closedSession = "closedsession";
	// some new session {cmd,id} - from session manager on create
	public final static String newSession = "newsession";
	// session started {cmd,id} - from end of ctor of session
	public final static String sessionStarted="sessionstarted";
	// server alive
	public final static String serverOn = "serveron";
	// task is finished by rest client {cmd,taskID,sessionID, metadata,jsonObject, jsonResult,taskObjectJson}
	public final static String restClientFinished = "finished";
	// notify when solution is finished {cmd,level,actions{right,left,down...}}
	public final static String finishedSolution = "finishedSolution";
	// new task created + session update {cmd}
	public final static String newTaskAndSessionUpdate = "ntasu";
	// log update [cmd,log string]
	public final static String logUpdate = "log";
	//[cmd, sessionid]
	public final static String closeAsession="closeASession";
}
