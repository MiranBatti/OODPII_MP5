package command;

import hanoi.HanoiModel;
import hanoi.Stack;

public class MoveCommand extends Command {
	private Stack<Integer> oldCommands = new Stack<Integer>();
	private Stack<Integer> backupCommands = new Stack<Integer>();
	private Stack<Integer> moveCommands = new Stack<Integer>();
	private HanoiModel hanoiModel;
	
	public MoveCommand(HanoiModel hanoiModel)
	{
		this.hanoiModel = hanoiModel;
	}
	
	@SuppressWarnings("unchecked")
	public MoveCommand execute()
	{
		if(!moveCommands.empty())
		hanoiModel.move(moveCommands.pop(), moveCommands.pop());
		return this;
	}
	
	public void add(int from, int to)
	{
		moveCommands.push(to);
		moveCommands.push(from);
		oldCommands.push(from);
		oldCommands.push(to);
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
