package hanoi;

import java.awt.Color;

import command.MacroCommand;
import command.MoveCommand;
import state.HanoiState;
import state.ManualState;

public class Mediator {
	private HanoiModel hanoiModel;
	private MoveCommand command;
	private MacroCommand macro;
	private boolean macroMode = false;
	private HanoiState state;
	private boolean isClicked = false;
	
	public Mediator(HanoiModel hanoiModel, MoveCommand command, MacroCommand macro)
	{
		this.hanoiModel = hanoiModel;
		this.command = command;
		this.macro = macro;
		state = new ManualState(hanoiModel, this.command);
	}
	
	public void moveRequest(RodPanel rod)
	{
		isClicked = true;
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
	
	public boolean rodIsClicked()
	{
		return isClicked;
	}
	
}
