package p20;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

public class AI {
	private Hashtable<Integer, Integer> transTable = new Hashtable<>();
	private int depthOfBestMove;
	private Move tempBestMove;
	private int tempBestValue;
	private List<Move> bestMoves = new ArrayList<>();
		
	public Move getBestMove(ImmutableBoard<Move> b, int maxDepth) {
		depthOfBestMove = b.getHistory().size();
		//iterative deeping depth-first search
		for(int i = 0; i < maxDepth; i++){
			//"killer-heuristic": start next 
			if(bestMoves.size() > 0){
				for(Move move : bestMoves) b = b.makeMove(move);
			}
			int value = alphaBeta(b, Integer.MIN_VALUE, Integer.MAX_VALUE, i + 1);
			if(value > tempBestValue){
				bestMoves.set(i, tempBestMove);
				tempBestValue = value;
			}
		}
		return bestMoves.get(0);
	}
	
	private int alphaBeta(ImmutableBoard<Move> b, int alpha, int beta, int depth) {
		if (b.isWin()) return -(1000 - b.getHistory().size());
		if (b.isDraw()) return 0;
		if (transTable.containsKey(b.hashCode())) return transTable.get(b.hashCode());
		if (depth == 0) {
			tempBestMove = monteCarlo(b, 1000);
			return -10000;
		}
		int bestValue = alpha;
		for (Move move : b.moves()) {
			int value = -alphaBeta(b.makeMove(move), -beta, -bestValue, depth - 1);
			if (value > bestValue) {
				bestValue = value;
				if (bestValue >= beta) break;
				transTable.put(b.hashCode(), bestValue);
				if (b.getHistory().size() == depthOfBestMove) {
					tempBestMove = move;
					tempBestValue = bestValue;
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
		return (b.isBeginnersTurn() ? -1 : 1); // -turn
	}

	private int[] simulatePlays(ImmutableBoard<Move> b, int times) {
		int[] count = new int[3];
		while (times > 0) {
			//0 = -turn won, 1 = draw, 2 = +turn won
			count[playRandomly(b) + 1] += 1;
			times -= 1;
		}
		return count;
	}

	private int[][] evaluateMoves(ImmutableBoard<Move> b, int times) {
		int[][] values = new int[b.moves().size()][3];
		for(int i = 0; i < b.moves().size(); i++){
			values[i] = simulatePlays(b.makeMove(b.moves().get(i)), times);
		}
		return values;
	}
	
	public Move monteCarlo(ImmutableBoard<Move> b, int times) {
		int[] values = new int[b.moves().size()];
		int maxValue = Integer.MIN_VALUE;
		int indexOfMaxValue = -1;
		int[][] evaluated = evaluateMoves(b, 100);
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