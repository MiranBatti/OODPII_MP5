package hanoi;

public class CommandImpl extends Command implements Cloneable {
	private Stack<Integer> oldCommands = new Stack<Integer>();
	private Stack<Integer> backupCommands = new Stack<Integer>();
	private HanoiModel hanoiModel;
	
	public CommandImpl(HanoiModel hanoiModel)
	{
		this.hanoiModel = hanoiModel;
	}
	
	public boolean redo()
	{
		if(!backupCommands.empty())
		{
			int from = backupCommands.pop();
			int to = backupCommands.pop();
			hanoiModel.move(from, to);
			oldCommands.push(from);
			oldCommands.push(to);
			return true;
		}
		return false;
	}
	public boolean undo()
	{
		if(!oldCommands.empty())
		{
			int from = oldCommands.pop();
			int to = oldCommands.pop();
			hanoiModel.move(from, to);
			backupCommands.push(from);
			backupCommands.push(to);
			return true;
		}
		return false;
	}
	
	public void add(int from, int to)
	{
		oldCommands.push(from);
		oldCommands.push(to);
	}
	
	public CommandImpl clone()
	{
		return null;
	}
}
