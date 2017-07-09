package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import model.tasks.ITaskManager;
import planning.plannable.SokoHeuristics;
import searchable.Action;
import searchable.Solution;
import server.Protocol;
import server.SessionManager;
import server.SokoMultiServer;
import solver.MainSolver;

public class SSModel extends Observable implements ISSModel
{
	private SessionManager sm;
	private MainSolver solver;
	private SokoMultiServer server;
	private boolean stop=false;
	private int port;
	private ITaskManager tm;
	public SSModel(SessionManager sm2, ITaskManager tm) 
	{
		server = new SokoMultiServer(sm2);
		this.sm=sm2;
		this.tm=tm;
	}
	public void startServer()
	{
			server.start();
			updateObserver(Protocol.serverOn);		
	}
	public void initializeModelComponents()
	{
		//startServer();
		tm.startTasker();
	}
	public void stop()
	{
		stop=true;
		server.stop();
		tm.stop();
	}
	public int getUsersConnectedNum()
	{
		return sm.getAllSessions().size();
	}
	public void solveLevel(String path)
	{
		String levelPath=path;
		String solutionPath="./LocalTestFiles/solution.txt";
		SokoHeuristics heuristics = new SokoHeuristics();
		MainSolver solver = new MainSolver(heuristics);
		solver.defineLevelPath(levelPath,solutionPath );
		solver.loadLevel();
		solver.asyncSolve();
		Solution solution = solver.saveSolution();
		if(solution == null)
		{
			System.out.println("Model: solution is null.");
			return;
		}
		int size = solution.getTheSolution().size();
		String [] parsed_solution = new String[size+2];
		int i=2;
		parsed_solution[0]=Protocol.finishedSolution;
		parsed_solution[1]=levelPath;
		
		for(Action a: solution.getTheSolution())
		{
			parsed_solution[i]=a.getAction();
			i++;
		}
		//{cmd,level,actions{right,left,down...}}
		updateObserver(parsed_solution);
	}
	public void updateObserver(String ...strings)
	{
		List<String> params = new LinkedList<String>();
		for(String s : strings)
		{
			params.add(s);
		}
		setChanged();
		notifyObservers(params);
	}
	@Override
	public ITaskManager getTaskManager() 
	{
		return tm;
	}
	@Override
	public SessionManager getSessionManager() 
	{
		return sm;
	}
	@Override
	public SokoMultiServer getServer() 
	{
		return this.server;
	}
	@Override
	public MainSolver getLevelSolver() 
	{
		return solver;
	}	
}
