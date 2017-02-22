package hanoi;

public abstract class Command implements Cloneable{
	abstract public boolean redo();
	abstract 	public boolean undo();
	public Command clone()
	{
		try {
			return (Command)super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}
