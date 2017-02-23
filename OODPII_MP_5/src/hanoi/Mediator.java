package hanoi;

public class Mediator {
	private HanoiModel hanoiModel;
	private boolean requestsFull = false;
	private int from, to, counter = 0;
	Command command;
	
	public Mediator(HanoiModel hanoiModel)
	{
		this.hanoiModel = hanoiModel;
		command = new CommandImpl(hanoiModel);
	}
	
	public void moveRequest(RodPanel rod)
	{
		if(counter == 0)
		{
			from = rod.getIndex();
			System.out.println("from: " + from);
			counter++;
			return;
		}
		if(counter > 0)
		{
			to = rod.getIndex();
			counter = 0;
			System.out.println("to: " + to);
		}
		command.add(from, to);
		if((Integer)from != null && (Integer)to != null)
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
}
