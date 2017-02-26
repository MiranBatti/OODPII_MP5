package state;

import hanoi.RodPanel;

public class AutomaticState extends HanoiState
{
	private static AutomaticState instance = null;
	
	private AutomaticState(){ }
	
	public static AutomaticState getInstance()
	{
		if(instance == null)
			instance = new AutomaticState();
		return instance;
	}
	
	@Override
	public void move(RodPanel rod){ }

	@Override
	public void undo(){ }

	@Override
	public void redo(){	}
	
	@Override
	public String toString()
	{
		return "Automatic";
	}

}
