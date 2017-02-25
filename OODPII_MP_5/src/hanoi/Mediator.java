package hanoi;

import java.awt.Color;

import command.MacroCommand;
import command.MoveCommand;

public class Mediator {
	private HanoiModel hanoiModel;
	private int from, to;
	private Disk fromDisk, toDisk;
	private MoveCommand command;
	private MacroCommand macro;
	private boolean macroMode = false;
	private boolean rodHasDisk = false;
	private boolean first = true;
	private RodPanel previousRod;
	
	public Mediator(HanoiModel hanoiModel)
	{
		this.hanoiModel = hanoiModel;
		command = new MoveCommand(hanoiModel);
		macro = new MacroCommand();
	}
	
	public void moveRequest(RodPanel rod)
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
	
	public void undoRequest()
	{
		if(!macroMode)
			command.undo();
		else
			macro.addUndo(command);		
	}
	
	public void redoRequest()
	{
		if(!macroMode)
			command.redo();
		else
			macro.addRedo(command);		
	}
	
	public void macro()
	{
		if(macroMode)
			macro.execute();
		macroMode = !macroMode;
	}
	
}
