package p20;

public class Move {
	public int to;
	public int from;
	public int remove;
	
	public Move(int to, int from, int remove){
		this.to = to;
		this.from = from;
		this.remove = remove;
	}
	
	public Move(int to, int from){
		this(to, from, -1);
	}
	
	public Move(int to){
		this(to, -1, -1);
	}

	public int getTo() {
		return to;
	}

	public int getFrom() {
		return from;
	}

	public int getRemove() {
		return remove;
	}
}