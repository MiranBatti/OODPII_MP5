package state;

import java.awt.Color;

import command.MacroCommand;
import command.MoveCommand;
import hanoi.Disk;
import hanoi.HanoiModel;
import hanoi.RodPanel;

public class MacroState extends HanoiState
{
	private HanoiModel hanoiModel;
	private int from, to;
	private Disk fromDisk, toDisk;
	private MoveCommand command;
	private MacroCommand macro;
	private boolean rodHasDisk = false;
	private boolean first = true;
	private RodPanel previousRod;
	private static MacroState instance = null;
	
	private MacroState()
	{
		
	}
	
	public static MacroState getInstance(HanoiModel hanoiModel, MoveCommand moveCommand, MacroCommand macro)
	{
		if(instance == null)
			instance = new MacroState();
		instance.setModel(hanoiModel);
		instance.setCommands(moveCommand, macro);
		return instance;
	}
	
	public void setModel(HanoiModel hanoiModel)
	{
		this.hanoiModel = hanoiModel;
	}
	
	public void setCommands(MoveCommand moveCommand, MacroCommand macro)
	{
		this.command = moveCommand;
		this.macro = macro;
	}
	
	@Override
	public void move(RodPanel rod)
	{
		rodHasDisk = rod.getDisks().iterator().hasNext();
		
		if(first) 
		{
			if(rodHasDisk)
			{
				rod.setColor(Color.orange, rod);
				from = rod.getIndex();
				previousRod = rod;
				fromDisk = hanoiModel.getTopDisk(rod.getIndex());
			}
			else
			{
				if(first)
				{
					return;
				}
			}
			first = false;
			return;
		} 
		else
		{
			if(rodHasDisk)
			{
				rod.setColor(Color.yellow, previousRod);
				to = rod.getIndex();
				toDisk = hanoiModel.getTopDisk(rod.getIndex());
				if(fromDisk.getNumber() > toDisk.getNumber())
					command.add(from, to);				
				else
				{
					first = true;
					return;
				}
			}
			else
			{
				to = rod.getIndex();
				rod.setColor(Color.yellow, previousRod);
				command.add(from, to);				
			}
			first = true;			
		}
		macro.add(command);
	}

	@Override
	public void undo()
	{
		macro.addUndo(command);
	}

	@Override
	public void redo()
	{
		macro.addRedo(command);
	}
	
	@Override
	public String toString()
	{
		return "Macro";
	}

}
