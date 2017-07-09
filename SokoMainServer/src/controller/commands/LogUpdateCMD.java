package controller.commands;

import java.util.LinkedList;

import view.ViewModel;
/**
 * Add an action to the log presented in the view model.
 * aggrigating.
 * @author Isan Rivkin and Daniel Hake.
 *
 */
public class LogUpdateCMD implements Command
{

	private ViewModel vm; 
	private String log;
	public LogUpdateCMD(ViewModel vm) 
	{
		this.vm =vm;
	}
	@Override
	public void execute() 
	{
		vm.updateLog(log);
	}

	@Override
	public void init(LinkedList<String> arg0)
	{
		log = arg0.removeFirst();
	}
	
}
