package p20;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Mills extends Board<Move>{
	//size: [24][2][2]
	//field number, number of mill (0,1), other mill fields
	private static int[][][] mills = {{{1,2},{6,7}}, {{0,2},{9,17}}, {{0,1},{3,4}}, {{2,4},{11,19}},
			{{5,6},{2,3}}, {{4,6},{13,21}}, {{4,5},{0,7}}, {{0,6},{15,23}}, {{9,10},{14,15}}, {{8,10},{1,17}}, {{8,9},{11,12}},
			{{10,12},{3,19}}, {{10,11},{13,14}}, {{12,14},{5,21}}, {{12,13},{8,15}}, {{8,14},{7,23}},
			{{17,18},{22,23}}, {{1,9},{16,18}}, {{16,17},{19,20}}, {{18,20},{3,11}}, {{18,19},{21,22}},
			{{20,22},{5,13}}, {{20,21},{16,23}}, {{16,22},{7,15}}};
	private static int[][] neighbours = {{1,7}, {0,2,9}, {1,3}, {2,4,11}, {3,5}, {4,6,13}, {5,7}, {0,6,15},
			{9,15}, {1,8,10,17}, {9,11}, {3,10,12,19}, {11,13}, {5,12,14,21}, {13,15}, {7,8,14,23}, {17,23}, {9,16,18},
			{17,19}, {11,18,20}, {19,21}, {13,20,22}, {21,23}, {15,16,22}};	
	

    public Mills() {
        board = new int[24];
    }

    @Override
	public ImmutableBoard makeMove(Move move) {
		Board<Move> b = new Mills();
		b.board = Arrays.copyOf(board, board.length);
		// Set new stone or move stone to new position
		b.board[move.getTo()] = isBeginnersTurn() ? 1 : -1;
		// Check if stone was moved...
		if(move.getFrom() > -1) {
			// ... Remove it from its preceeding position
			b.board[move.getFrom()] = 0;
		}
		// Check if opposal stone hs to be removed...
		if(move.getRemove() > -1) {
			// ... Remove it
			b.board[move.getRemove()] = 0;
		}

		b.parent = this;
		b.move = move;
		b.isFlipped = isFlipped;
		return b;
	}

	@Override
	public List<Move> moves() {
		ArrayList<Move> moves = new ArrayList<>();
		if(getHistory().size() < 18){ //first phase
			return IntStream.range(0, board.length)
					.filter(i -> board[i] == 0)
					.mapToObj(i -> new Move(i))
					.collect(Collectors.toList());
		}
		
		int turn = isBeginnersTurn() ? 1 : -1;
		List<Integer> playerStones = IntStream.range(0, board.length)
				.filter(i -> board[i] == turn)
				.boxed().collect(Collectors.toList());
		
		ArrayList<Move> movableStones = new ArrayList<>();
		if(playerStones.size() == 3){ //third phase
			//moving to any position is allowed
			playerStones.forEach(stone -> IntStream.range(0, board.length)
					.filter(i -> board[i] == 0)
					.mapToObj(i -> new Move(i, stone))
					.forEach(movableStones::add));
		}else{ //second phase
			//moving to direct neighbours only
			playerStones.forEach(stone -> Arrays.stream(neighbours[stone])
					.filter(nb -> board[nb] == 0)
					.mapToObj(nb -> new Move(nb, stone))
					.forEach(movableStones::add));
		}
		//second and third phase
		List<Integer> removableStones = removableStones();
		for(Move move : movableStones){
			if(closesMill(move.to)){
				for(Integer stone : removableStones){
					moves.add(new Move(move.to, move.from, stone));
				}
			}else moves.add(move);
		}
		return moves;
	}

	@Override
	public boolean isWin() {
    	// When game is not in first state (set stones, first 18 turns)
		// AND no possible moves left OR only two stones left
		// -> Game over
		return getHistory().size() > 18 &&
				(moves().size() == 0 ||
				IntStream.of(board).filter(i -> i == (isBeginnersTurn() ? 1 : -1)).count() < 3);
	}

	/**
	 * Removable stones from the opponent player are those who are not in a mill, unless there are no outside a mill,
	 * in that case every stone is removable.
	 * @return List of removable stones
	 */
	private List<Integer> removableStones(){
		int turn = isBeginnersTurn() ? 1 : -1;
		List<Integer> opponentStones = IntStream.range(0, board.length)
				.filter(i -> board[i] == -turn)
				.boxed().collect(Collectors.toList());
		if(opponentStones.size() == 3) return opponentStones;
		List<Integer> removableStones = opponentStones.stream()
				.filter(stone -> closesMill(stone) == false)
				.collect(Collectors.toList());
		return (removableStones.size() > 0 ? removableStones : opponentStones);
	}
	
	/**
	 * Checks whether a stone closes a mill or not
	 * @param stone to be set
	 * @return true if the stone closes a mill
	 */
	private boolean closesMill(int stone){
		for(int i = 0; i < mills[stone].length; i++){
			int sum = board[stone];
			for(int j = 0; j < mills[stone][i].length; j++){
				sum += board[j];
			}
			if(Math.abs(sum) == 3) return true;
		}
		return false;
	}

    @Override
    public String toString() {
        String[] repr = {"O", ".", "X"};

        String s =
                "%s-----%s-----%s\n" +
                "| %s---%s---%s |\n" +
                "| | %s-%s-%s | |\n" +
                "%s-%s-%s   %s-%s-%s\n" +
                "| | %s-%s-%s | |\n" +
                "| %s---%s---%s |\n" +
                "%s-----%s-----%s";
        // Map board's values to string array containing the field's character representation...
        String[] ever = IntStream.of(board).mapToObj(i ->  repr[i + 1]).toArray(String[]::new);
        // ... so we can easily format the board string
        return String.format(s, ever);
    }
}