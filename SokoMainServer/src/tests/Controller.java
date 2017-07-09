package tests;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import server.SessionManager;

public class Controller implements Observer
{
	private SessionManager sm;
	
	public Controller(SessionManager sm) 
	{
		this.sm=sm;
	}

	@Override
	public void update(Observable o, Object arg)
	{
		LinkedList<String> params =(LinkedList<String>)arg;
		System.out.print("type= " + o.getClass().getName() + " params: ");
		for(String s : params)
		{
			System.out.print(" " + s);
		}
		System.out.println();
		String command = params.removeFirst();
		if(command.equals("newsession"))
		{
			sm.startSession(Integer.parseInt(params.removeFirst()));
		}
	}

}
