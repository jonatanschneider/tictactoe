import java.util.List;
import java.util.stream.IntStream;
import java.util.ArrayList;
import java.util.Arrays;

public class Board implements ImmutableBoard<Integer>{
	public int[] board = new int[9];
	public Board parent = null;
	public List<Integer> history = new ArrayList<>();
	public boolean isFlipped = false;
	
	@Override
	public Board makeMove(Integer pos){
		Board b = new Board();
		b.board = Arrays.copyOf(board, 9);
		b.board[pos] = (isBeginnersTurn() ? 1 : -1);
		b.parent = this;
		b.history = new ArrayList<>(history);
		b.history.add(pos);
		b.isFlipped = isFlipped;
		return b;
	}

	@Override
	public Board makeMove(Integer... positions){
		Board b = this;
		for(int pos : positions) b = b.makeMove(pos);
		return b;
	}

	@Override
	public Board undoMove(){
		history.remove(history.size()-1);
		return parent;
	}
	
	@Override
	public List<Integer> moves(){
		List<Integer> positions = new ArrayList<>();
		IntStream.range(0, board.length)
		.filter(i -> board[i] == 0)
		.forEach(positions::add);
		return positions;
	}

	@Override
	public boolean isWin(){
		if (history.size() < 5) return false;
		int[][] rows = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
		for(int[] i : rows){
			int sum = 0;
			for(int j : i){
				sum += board[j];
			}
			if (Math.abs(sum) == 3) return true;
		}
		return false;
	}

	@Override
	public boolean isDraw(){
		if(moves().size() == 0) return true;
		return false;
	}

	@Override
	public List<Integer> getHistory(){
		return history;
	}

	@Override
	public Board flip(){
		Board b = new Board();
		b.board = Arrays.copyOf(board, 9);
		b.parent = this;
		b.history = new ArrayList<>(history);
		b.isFlipped = !isFlipped;
		return b;
	}

	@Override
	public boolean isFlipped(){ return isFlipped; }

	@Override
	public String toString(){
		String s = "\n";
		for(int i = 0; i < 9; i++){
			if(board[i] == 0) s+= ". ";
			else if(board[i] == 1) s+= "X ";
			else if(board[i] == -1) s+= "O ";
			if(i == 2 || i == 5) s+= "\n";
		}
		s += "\n";
		return s;
	}
	@Override
	public int hashCode(){
		return Arrays.hashCode(board);
	}
}