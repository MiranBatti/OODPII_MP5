package command;

import hanoi.HanoiModel;
import hanoi.Stack;

public class MoveCommand extends Command {
	private Stack<Integer> undoCommands = new Stack<Integer>();
	private Stack<Integer> redoCommands = new Stack<Integer>();
	private Stack<Integer> moveCommands = new Stack<Integer>();
	private HanoiModel hanoiModel;
	private int from, to;
	
	public MoveCommand(HanoiModel hanoiModel)
	{
		this.hanoiModel = hanoiModel;
	}
	
	@SuppressWarnings("unchecked")
	public MoveCommand execute()
	{
		if(!moveCommands.empty())
			hanoiModel.move(from, to);
		return this;
	}
	
	public void add(int from, int to)
	{
		this.from = from;
		this.to = to;		
		moveCommands.push(to);
		moveCommands.push(from);
		undoCommands.push(from);
		undoCommands.push(to);
	}
	
	public boolean redo()
	{
		if(!redoCommands.empty())
		{
			int from = redoCommands.pop();
			int to = redoCommands.pop();
			hanoiModel.move(from, to);
			undoCommands.push(from);
			undoCommands.push(to);
			return true;
		}
		return false;
	}
	
	public boolean undo()
	{
		if(!undoCommands.empty())
		{
			int from = undoCommands.pop();
			int to = undoCommands.pop();
			hanoiModel.move(from, to);
			redoCommands.push(from);
			redoCommands.push(to);
			return true;
		}
		return false;
	}
	
	public void reset()
	{
		moveCommands.clear();
		undoCommands.clear();
		redoCommands.clear();
	}
	
}
