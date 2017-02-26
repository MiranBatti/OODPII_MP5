package command;

import java.util.ArrayDeque;
import java.util.Queue;

import hanoi.HanoiModel;
import hanoi.Stack;

public class MoveCommand extends Command {
	private Stack<Integer> undoCommands = new Stack<Integer>();
	private Stack<Integer> redoCommands = new Stack<Integer>();
	private Stack<Integer> moveCommands = new Stack<Integer>();
	private HanoiModel hanoiModel;
	private Queue<Integer> from 		= new ArrayDeque<Integer>();
	private Queue<Integer> to 			= new ArrayDeque<Integer>();
	
	public MoveCommand(HanoiModel hanoiModel)
	{
		this.hanoiModel = hanoiModel;
	}
	
	@SuppressWarnings("unchecked")
	public MoveCommand execute()
	{
		if(!from.isEmpty() && !to.isEmpty())
			hanoiModel.move(from.poll(), to.poll());
		return this;
	}
	
	public void add(int from, int to)
	{
		this.from.offer(from);
		this.to.offer(to);
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
