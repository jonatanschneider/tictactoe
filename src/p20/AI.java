package p20;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.ArrayList;
import java.util.HashMap;

public class AI<Move> {
	private Hashtable<Integer, Integer> transTable = new Hashtable<>();
	private int beginningDepth;
	private Move tempBestMove;
	private HashMap<Integer, List<Move>> killerMoves = new HashMap<>();
	private List<Move> bestMoves = new ArrayList<>();
	
	public List<Move> getListOfBestMoves(){ return bestMoves; }
	
	public Move getBestMove(ImmutableBoard<Move> b, int maxDepth) {
		beginningDepth = b.getHistory().size();
		for(int i = 1; i < maxDepth; i++){
			alphaBeta(b, Integer.MIN_VALUE, Integer.MAX_VALUE, i);
			bestMoves.add(tempBestMove);
		}
		return bestMoves.get(0);
	}

	public int alphaBeta(ImmutableBoard<Move> b, int alpha, int beta, int depth) {
		int boardDepth = b.getHistory().size();
		if (b.isWin()) return -(1000 - boardDepth);
		if (b.isDraw()) return 0;
		if (transTable.containsKey(b.hashCode()))return transTable.get(b.hashCode());
		if (depth == 0) {
			System.out.println("MC");
			tempBestMove = chooseBestMove(b, 100);
			return -1;
		}
		int bestValue = alpha;
		List<Move> boardMoves = b.moves();
		List<Move> moves = new ArrayList<>();
		//make sure killer moves get executed first
		if(killerMoves.get(boardDepth) != null){
			for(Move m : killerMoves.get(boardDepth)){
				if(boardMoves.contains(m)) moves.add(0, m);
				else moves.add(m);
			}
		}else moves = boardMoves;
		
		for (Move move : moves) {
			int value = -alphaBeta(b.makeMove(move), -beta, -bestValue, depth - 1);
			if (value > bestValue) {
				bestValue = value;
				if (bestValue >= beta){
					if(! killerMoves.containsKey(boardDepth)){
						killerMoves.put(boardDepth, new ArrayList<Move>());
					}
					killerMoves.get(boardDepth).add(move);
					break;
				}
				transTable.put(b.hashCode(), bestValue);
				if (boardDepth == beginningDepth) {
					tempBestMove = move;
				}
			}
		}

		return bestValue;
	}

	public int playRandomly(ImmutableBoard<Move> b) {
		while (b.isWin() == false) {
			if (b.isDraw())
				return 0;
			List<Move> moves = b.moves();
			Random r = new Random();
			Move randomMove = moves.get(r.nextInt(moves.size()));
			b = b.makeMove(randomMove);
		}
		return (b.isBeginnersTurn() ? -1 : 1);
	}

	public int[] simulatePlays(ImmutableBoard<Move> b, int number) {
		int[] count = new int[3];
		while (number > 0) {
			count[playRandomly(b) + 1] += 1;
			number -= 1;
		}
		return count;
	}

	public int[][] evaluateMoves(ImmutableBoard<Move> b, int number) {
		int[][] values = new int[9][];
		for(int i = 0; i < b.moves().size(); i++){
			values[i] = simulatePlays(b.makeMove(b.moves().get(i)), number);
		}
		return values;
	}

	public Move chooseBestMove(ImmutableBoard<Move> b, int number) {
		int[] values = new int[9];
		int maxValue = Integer.MIN_VALUE;
		int indexOfMaxValue = -1;
		int[][] evaluated = evaluateMoves(b, 1000);
		//TODO stream?
		for (int i = 0; i < 9; i++) {
			if (evaluated[i] != null) {
				values[i] = evaluated[i][2] * (b.isBeginnersTurn() ? 1 : -1);
				if (values[i] > maxValue) {
					maxValue = values[i];
					indexOfMaxValue = i;
				}
			}
		}
		return b.moves().get(indexOfMaxValue);
	}
}