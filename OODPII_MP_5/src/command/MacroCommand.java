package command;

import java.util.Vector;

public class MacroCommand extends Command
{
	private Vector<Command> undoCommands = new Vector<Command>();
	private Vector<Command> redoCommands = new Vector<Command>();

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
		undo();
		redo();
		clearCommands();
		return this;
	}
	
	private void clearCommands()
	{
		undoCommands.clear();
		redoCommands.clear();
	}

}
