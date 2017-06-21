package p20;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Mills extends Board<Move>{
	//size: [24][2][2]
	//field number, number of mill (0,1), other mill fields
	public int[][][] mills = {{{1,2},{6,7}}, {{0,2},{9,17}}, {{0,1},{3,4}}, {{2,4},{11,19}},
			{{5,6},{2,3}}, {{4,6},{13,21}}, {{4,5},{0,7}}, {{0,6},{15,23}}, {{9,10},{14,15}}, {{8,10},{1,17}}, {{8,9},{11,12}},
			{{10,12},{3,19}}, {{10,11},{13,14}}, {{12,14},{5,21}}, {{12,13},{8,15}}, {{8,14},{7,23}},
			{{17,18},{22,23}}, {{1,9},{16,18}}, {{16,17},{19,20}}, {{18,20},{3,11}}, {{18,19},{21,22}},
			{{20,22},{5,13}}, {{20,21},{16,23}}, {{16,22},{7,15}}};
	public static int[][] neighbours = {{1,7}, {0,2,9}, {1,3}, {2,4,11}, {3,5}, {4,6,13}, {5,7}, {0,6,15},
			{9,15}, {1,8,10,17}, {9,11}, {3,10,12,19}, {11,13}, {5,12,14,21}, {13,15}, {7,8,14,23}, {17,23}, {9,16,18},
			{17,19}, {11,18,20}, {19,21}, {13,20,22}, {21,23}, {15,16,22}};	
	
	@Override
	public List<Move> moves() {
		ArrayList<Move> moves = new ArrayList<>();
		if(getHistory().size() < 18){
			IntStream.range(0, board.length)
				.filter(i -> board[i] == 0)
				.forEach(i -> moves.add(new Move(i)));
		}else{
			int turn = isBeginnersTurn() ? 1 : -1;
			ArrayList<Move> freeNeighbours = new ArrayList<>();
			IntStream.range(0, board.length)
				.filter(i -> board[i] == turn)
				.forEach(stone -> Arrays.stream(neighbours[stone])
						.filter(nb -> board[nb] == 0)
						.mapToObj(nb -> new Move(nb, stone))
						.forEach(freeNeighbours::add));
			
			List<Integer> removableStones = removableStones();
			for(Move move : freeNeighbours){
				if(closesMill(move.to)){
					for(Integer stone : removableStones){
						moves.add(new Move(move.to, move.from, stone));
					}
				}
			}
		}
		return moves;
	}
	
	/**
	 * Removable stones from the opponent player are those who are not in a mill, unless there are no outside a mill,
	 * in that case every stone is removable.
	 * @return List of removable stones
	 */
	public List<Integer> removableStones(){
		List<Integer> removableStones = new ArrayList<>();
		List<Integer> stones = new ArrayList<>();
		int turn = isBeginnersTurn() ? 1 : -1;
		IntStream.range(0, board.length).filter(i -> board[i] == -turn).forEach(stones::add);
		if(stones.size() == 3) return stones;
		for(Integer stone : stones){
				if(closesMill(stone)) removableStones.add(stone);
			}
		//If there are no stones outside of a mill, every stone is removable
		return removableStones().size() > 0 ? removableStones : stones;
	}
	
	public boolean closesMill(int move){
		for(int i = 0; i < mills[move].length; i++){
			int sum = board[move];
			for(int j = 0; j < mills[move][i].length; j++){
				sum += board[j];
			}
			if(Math.abs(sum) == 3) return true;
		}
		return false;
	}
	
}
