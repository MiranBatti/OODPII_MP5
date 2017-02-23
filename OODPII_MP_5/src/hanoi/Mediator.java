package hanoi;

public class Mediator {
	private HanoiModel hanoiModel;
	private boolean requestsFull = false;
	private int from, to, counter = 0;
	private Disk fromDisk, toDisk;
	Command command;
	int tmp1, tmp2;
	
	public Mediator(HanoiModel hanoiModel)
	{
		this.hanoiModel = hanoiModel;
		command = new CommandImpl(hanoiModel);
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
		move(from, to);
	}
	
	private void move(int from, int to)
	{
		hanoiModel.move(from, to);
	}
	
	public void undo()
	{
		command.undo();
	}
	
	public void redo()
	{
		command.redo();
	}
}
