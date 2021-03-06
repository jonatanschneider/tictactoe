package p20;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
		ArrayList<Move> movesWithoutRemove = new ArrayList<>();
		//first phase
		if(getHistory().size() < 18){
			IntStream.range(0, board.length)
					.filter(i -> board[i] == 0)
					.mapToObj(i -> new Move(i))
					.forEach(movesWithoutRemove::add);
			//return if mill closing is not possible yet
			if(getHistory().size() < 4) return movesWithoutRemove;
		}
		else{
			int turn = isBeginnersTurn() ? 1 : -1;
			List<Integer> playerStones = IntStream.range(0, board.length)
					.filter(i -> board[i] == turn)
					.boxed().collect(Collectors.toList());
			if(playerStones.size() < 3) return new ArrayList<>();
			//second phase
			if(playerStones.size() > 3){
				//moving to direct neighbours only
				playerStones.forEach(stone -> Arrays.stream(neighbours[stone])
						.filter(nb -> board[nb] == 0)
						.mapToObj(nb -> new Move(nb, stone))
						.forEach(movesWithoutRemove::add));
			}
			//third phase
			else{
				//moving to any position is allowed
				playerStones.forEach(stone -> IntStream.range(0, board.length)
						.filter(i -> board[i] == 0)
						.mapToObj(i -> new Move(i, stone))
						.forEach(movesWithoutRemove::add));
			}
		}
		//all phases: check for mill closing
		List<Integer> removableStones = removableStones();
		for(Move move : movesWithoutRemove){
			if(closesMill(move)){
				for(Integer stone : removableStones){
					moves.add(new Move(move.getTo(), move.getFrom(), stone));
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
		return getHistory().size() > 17 &&
				(moves().size() == 0 ||
				IntStream.of(board).filter(i -> i == (isBeginnersTurn() ? 1 : -1)).count() < 3);
	}

	@Override
	public boolean isDraw() {
    	Board next = this;
    	// Store play positions based on the board's hashCode counting the occurences of the play position
		HashMap<Integer, Integer> playPositions = new HashMap<>();

		// Move along board history..
		while(next != null && next.move != null) {
			int hashCode = next.hashCode();
			// When stone got removed...
			if(((Move) next.move).getRemove() > -1) {
				// ... Return false as the play position can't be the same as before.
				return false;
			}
			// In case the play position occures for the third time the board matches the conditions for a draw
			if(playPositions.containsKey(hashCode) && playPositions.get(hashCode) == 2) {
				return true;
			}
			// Put the current play position in the playPosition storage
			playPositions.put(hashCode, (playPositions.containsKey(hashCode) ? 2 : 1));
			next = next.parent;
		}
		return false;
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
				.filter(stone -> inMill(stone) == false)
				.collect(Collectors.toList());
		return (removableStones.size() > 0 ? removableStones : opponentStones);
	}
	
	private boolean inMill(int stone){
		for(int i = 0; i < mills[stone].length; i++){
			int sum = board[stone];
			sum += board[mills[stone][i][0]];
			sum += board[mills[stone][i][1]];
			if(Math.abs(sum) == 3) return true;
		}
		return false;
	}

	/**
	 * Checks whether a stone closes a mill or not
	 * @param stone to be set
	 * @return true if the stone closes a mill
	 */
	private boolean closesMill(Move move){
		for(int i = 0; i < mills[move.getTo()].length; i++){
			int sum = (isBeginnersTurn() ? 1 : -1);
			for(int j = 0; j < mills[move.getTo()][i].length; j++){
				//make sure a stone which gets moved does not raise the sum
				if(move.getFrom() == -1 || move.getFrom() != mills[move.getTo()][i][j]){ 
					sum += board[mills[move.getTo()][i][j]];
				}
				
			}
			if(Math.abs(sum) == 3) return true;
		}
		return false;
	}

    @Override
    public String toString() {
		String[] repr = {"O", ".", "X"};

        String s =
                "%1$1s-----%2$1s-----%3$1s\n" +
                "| %9$1s---%10$1s---%11$1s |\n" +
                "| | %17$1s-%18$1s-%19$1s | |\n" +
                "%8$1s-%16$1s-%24$1s   %20$1s-%12$1s-%4$1s\n" +
                "| | %23$1s-%22$1s-%21$1s | |\n" +
                "| %15$1s---%14$1s---%13$1s |\n" +
                "%7$1s-----%6$1s-----%5$1s";
        // Map board's values to string array containing the field's character representation...
        String[] ever = IntStream.of(board).mapToObj(i ->  repr[(i * (isFlipped ? -1 : 1)) + 1]).toArray(String[]::new);
        // ... so we can easily format the board string
        return String.format(s, ever);
    }
}