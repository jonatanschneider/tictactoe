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
	
	@Override
	public String toString() {
		return "Move{" +
				"to=" + to +
				", from=" + from +
				", remove=" + remove +
				'}';
	}

	public String toSaveString() {
		if(from > -1 && to > -1 && remove > -1) {
			return from + "-" + to + "-" + remove;
		}
		if(from > -1 && to > -1) {
			return from + "-" + to;
		}
		if(to > -1 && remove > -1) {
			return to + "-" + remove;
		}
		return "" + to;
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Move){
			Move other = (Move) o;
			if(other.to == this.to && other.from == this.from && other.remove == this.remove) return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
}