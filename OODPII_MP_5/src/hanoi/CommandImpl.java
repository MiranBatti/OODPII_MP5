package hanoi;

public class CommandImpl extends Command implements Cloneable {
	private Stack<Integer> stack = new Stack<Integer>();
	private HanoiModel hanoiModel;
	
	public CommandImpl(HanoiModel hanoiModel)
	{
		this.hanoiModel = hanoiModel;
	}
	
	public boolean redo()
	{
		return false;
	}
	public boolean undo()
	{
		hanoiModel.move(stack.pop(), stack.pop());
		return false;
	}
	
	public void add(int from, int to)
	{
		stack.push(from);
		stack.push(to);
	}
	
	public CommandImpl clone()
	{
		return null;
	}
}
