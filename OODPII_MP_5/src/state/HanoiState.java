package state;

import hanoi.RodPanel;

public abstract class HanoiState
{
	public abstract void move(RodPanel rod);
	public abstract void undo();
	public abstract void redo();
}
