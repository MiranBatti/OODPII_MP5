package hanoi;

public class Mediator {
	private HanoiModel hanoiModel;
	private int from, to, counter = 0;
	private Disk fromDisk, toDisk;
	private MoveCommand command;
	private MacroCommand macro;
	private boolean macroMode = false;
	
	public Mediator(HanoiModel hanoiModel)
	{
		this.hanoiModel = hanoiModel;
		command = new MoveCommand(hanoiModel);
		macro = new MacroCommand();
	}
	
	public void moveRequest(RodPanel rod)
	{
		if(hanoiModel.getNumberOfDisksOnRod(rod.getIndex()) == 0 && counter == 0)
			return;

		if(counter == 0)
		{
			from = rod.getIndex();
			fromDisk = hanoiModel.getTopDisk(rod.getIndex());
			counter++;
			return;
		}
		if(counter > 0)
		{
			to = rod.getIndex();
			toDisk = hanoiModel.getTopDisk(rod.getIndex());
			counter = 0;
		}
		
		if(fromDisk != null && toDisk != null)
		{
			if(fromDisk.getNumber() < toDisk.getNumber())
			{
				return;
			}
		}
		
		command.add(from, to);
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
