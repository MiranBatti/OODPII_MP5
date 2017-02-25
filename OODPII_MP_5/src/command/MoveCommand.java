package command;

import hanoi.HanoiModel;
import hanoi.Stack;

public class MoveCommand extends Command {
	private Stack<Integer> oldCommands = new Stack<Integer>();
	private Stack<Integer> backupCommands = new Stack<Integer>();
	private HanoiModel hanoiModel;
	private int from, to;
	
	public MoveCommand(HanoiModel hanoiModel)
	{
		this.hanoiModel = hanoiModel;
	}
	
	public MoveCommand execute()
	{
		hanoiModel.move(from, to);
		return this;
	}
	
	public void add(int from, int to)
	{
		this.from = from;
		this.to = to;
		oldCommands.push(from);
		oldCommands.push(to);
		backupCommands.clear();
	}
	
	public boolean redo()
	{
		if(!backupCommands.empty())
		{
			int from = backupCommands.pop();
			int to = backupCommands.pop();
			hanoiModel.move(from, to);
			oldCommands.push(from);
			oldCommands.push(to);
			return true;
		}
		return false;
	}
	public boolean undo()
	{
		if(!oldCommands.empty())
		{
			int from = oldCommands.pop();
			int to = oldCommands.pop();
			hanoiModel.move(from, to);
			backupCommands.push(from);
			backupCommands.push(to);
			return true;
		}
		return false;
	}
	
}
