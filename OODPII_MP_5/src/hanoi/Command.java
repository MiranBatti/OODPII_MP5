package hanoi;

public abstract class Command
{
	abstract public <T> T execute();
	abstract public boolean redo();
	abstract public boolean undo();
}
