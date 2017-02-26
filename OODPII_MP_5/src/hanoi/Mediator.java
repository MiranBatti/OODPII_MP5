package hanoi;

import command.MacroCommand;
import command.MoveCommand;
import state.AutomaticState;
import state.HanoiState;

public class Mediator {
	private HanoiModel hanoiModel;
	private MoveCommand command;
	private MacroCommand macro;
	private boolean macroMode = false;
	private HanoiState state;
	
	public Mediator(HanoiModel hanoiModel, MoveCommand command, MacroCommand macro)
	{
		this.hanoiModel = hanoiModel;
		this.command = command;
		this.macro = macro;
		state = AutomaticState.getInstance();
	}
	
	public void moveRequest(RodPanel rod)
	{
		state.move(rod);
	}
	
	public void undoRequest()
	{
		state.undo();
	}
	
	public void redoRequest()
	{
		state.redo();
	}
	
	public void macro()
	{
		if(macroMode)
			macro.execute();
		macroMode = !macroMode;
	}
	
	public void setState(HanoiState state)
	{
		this.state = state;
	}
	
	public String getStateName()
	{
		return state.toString();
	}
	
	public void resetRequest()
	{
		command.reset();
		macro.reset();
	}
	
}
