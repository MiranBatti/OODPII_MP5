package command;

import java.util.Stack;
import java.util.Vector;

public class MacroCommand extends Command
{
	private Vector<Command> undoCommands = new Vector<Command>();
	private Vector<Command> redoCommands = new Vector<Command>();
	private Vector<Command>	moveCommands = new Vector<Command>();

	public void add(Command c)
	{
		moveCommands.add(c);
	}
	
	public void move()
	{
		for (Command command : moveCommands)
		{
			command.execute();
		}
	}
	
	public MacroCommand addUndo(Command c)
	{
		undoCommands.add(c);
		return this;
	}
	
	public MacroCommand addRedo(Command c)
	{
		redoCommands.add(c);
		return this;
	}
	
	@Override
	public boolean redo()
	{
		for (Command command : redoCommands)
		{
			command.redo();
		}
		return false;
	}

	@Override
	public boolean undo()
	{
		for (int i = undoCommands.size(); i > 0; i--)
		{
			undoCommands.get(i-1).undo();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public MacroCommand execute()
	{
		if(moveCommands != null)
			move();
		if(undoCommands != null)
			undo();
		if(redoCommands != null)	
			redo();
		clearCommands();
		return this;
	}
	
	private void clearCommands()
	{
		moveCommands.clear();
		undoCommands.clear();
		redoCommands.clear();
	}

}
