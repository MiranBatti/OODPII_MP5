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
			System.out.println(from = rod.getIndex());

		hanoiModel.move(0, 1);
	}
	
}
