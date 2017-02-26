package state;

import java.awt.Color;

import command.MacroCommand;
import command.MoveCommand;
import hanoi.Disk;
import hanoi.HanoiModel;
import hanoi.RodPanel;

public class ManualState extends HanoiState
{
	private HanoiModel hanoiModel;
	private int from, to;
	private Disk fromDisk, toDisk;
	private MoveCommand command;
	private boolean rodHasDisk = false;
	private boolean first = true;
	private RodPanel previousRod;
	private static ManualState instance = null;
	
	private ManualState()
	{
		
	}
	
	public static ManualState getInstance(HanoiModel hanoiModel, MoveCommand moveCommand)
	{
		if(instance == null)
			instance = new ManualState();
		instance.setModel(hanoiModel);
		instance.setCommands(moveCommand);
		return instance;
	}
	
	public void setModel(HanoiModel hanoiModel)
	{
		this.hanoiModel = hanoiModel;
	}
	
	public void setCommands(MoveCommand moveCommand)
	{
		this.command = moveCommand;
	}

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
		command.execute();
	}

	@Override
	public void undo()
	{
		command.undo();
	}

	@Override
	public void redo()
	{
		command.redo();
	}
	
	@Override
	public String toString()
	{
		return "Manual";
	}
	
}
