package p20;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.ArrayList;
import java.util.HashMap;

public class AI<Move> {
	private Hashtable<Integer, Integer> transTable = new Hashtable<>();
	private Hashtable<Integer, Move> transTableMove = new Hashtable<>();
	private int beginningDepth;
	private HashMap<Integer, List<Move>> killerMoves = new HashMap<>();
	private HashMap<Integer, Move> bestMoves = new HashMap<>();
	private HashMap<Integer, Integer> bestMovesScore = new HashMap<>();
	public HashMap<Integer, Move> getListOfBestMoves(){ return bestMoves; }
	
	public Move getBestMove(ImmutableBoard<Move> b, int maxDepth) {
		beginningDepth = b.getHistory().size();
		for(int i = 1; i < maxDepth - b.getHistory().size() + 1; i++){
			alphaBeta(b, Integer.MIN_VALUE, Integer.MAX_VALUE, i);
			System.out.println(bestMoves);
		}
		return bestMoves.get(beginningDepth);
	}

	public int alphaBeta(ImmutableBoard<Move> b, int alpha, int beta, int depth) {
		int boardDepth = b.getHistory().size();
		if(depth == 5) System.out.println("depth = 8");
		if (b.isWin()) return -(1000 - boardDepth);
		if (b.isDraw()) return 0;
		if (transTable.containsKey(b.hashCode())){
			bestMoves.put(boardDepth, transTableMove.get(b.hashCode()));
			bestMovesScore.put(boardDepth, transTable.get(b.hashCode()));
			return transTable.get(b.hashCode());
		}
		if (depth == 0) {
			Move temp = monteCarlo(b, 100);
			bestMoves.put(boardDepth -  1, temp);
			bestMovesScore.put(boardDepth - 1, -1);
			return Integer.MAX_VALUE;
		}
		int bestValue = alpha;
		List<Move> boardMoves = b.moves();
		List<Move> moves = new ArrayList<>();
		//make sure killer moves get checked first
		if(killerMoves.get(boardDepth + 1) != null){
			for(Move m : killerMoves.get(boardDepth + 1)){
				if(boardMoves.contains(m)) moves.add(0, m);
				else moves.add(m);
			}
		}else moves = boardMoves;
		
		for (Move move : moves) {
			int value = -alphaBeta(b.makeMove(move), -beta, -bestValue, depth - 1);
			if (value > bestValue) {
				bestValue = value;
				if (bestValue >= beta){
					if(!killerMoves.containsKey(boardDepth)){
						killerMoves.put(boardDepth, new ArrayList<Move>());
					}
					//make sure killer moves only get added once to a list
					if(!killerMoves.get(boardDepth).contains(move)) killerMoves.get(boardDepth).add(move);
					break;
				}
				if(bestMovesScore.containsKey(boardDepth)){
					if(bestValue > bestMovesScore.get(boardDepth)){
						transTable.put(b.hashCode(), bestValue);
						transTableMove.put(b.hashCode(), move);
						bestMoves.put(boardDepth, move);
						bestMovesScore.put(boardDepth, bestValue);
					}
				}else{
					transTable.put(b.hashCode(), bestValue);
					transTableMove.put(b.hashCode(), move);
					bestMoves.put(boardDepth, move);
					bestMovesScore.put(boardDepth, bestValue);
				}
			}
		}
		return bestValue;
	}

	private int playRandomly(ImmutableBoard<Move> b) {
		while (b.isWin() == false) {
			if (b.isDraw()) return 0;
			Random r = new Random();
			Move randomMove = b.moves().get(r.nextInt(b.moves().size()));
			b = b.makeMove(randomMove);
		}
		return (b.isBeginnersTurn() ? -1 : 1);
	}

	private int[] simulatePlays(ImmutableBoard<Move> b, int number) {
		int[] count = new int[3];
		while (number > 0) {
			count[playRandomly(b) + 1] += 1;
			number -= 1;
		}
		return count;
	}

	private int[][] evaluateMoves(ImmutableBoard<Move> b, int number) {
		int[][] values = new int[b.moves().size()][3];
		for(int i = 0; i < b.moves().size(); i++){
			values[i] = simulatePlays(b.makeMove(b.moves().get(i)), number);
		}
		return values;
	}

	public Move monteCarlo(ImmutableBoard<Move> b, int number) {
		int[] values = new int[b.moves().size()];
		int maxValue = Integer.MIN_VALUE;
		int indexOfMaxValue = -1;
		int[][] evaluated = evaluateMoves(b, 1000);
		for (int i = 0; i < values.length; i++) {
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