package hanoi;

public class Mediator {
	private HanoiModel hanoiModel;
	private boolean requestsFull = false;
	private int from, to, counter = 0;
	
	public Mediator(HanoiModel hanoiModel)
	{
		this.hanoiModel = hanoiModel;
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
		if((Integer)from != null && (Integer)to != null)
		move(from, to);
	}
	
	private void move(int from, int to)
	{
		hanoiModel.move(from, to);
	}
}
