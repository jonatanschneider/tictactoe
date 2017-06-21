package p20;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

@SuppressWarnings("unchecked")
public class AI {
	public Hashtable<Integer, Integer> transTable = new Hashtable<>();
	public int depthOfBestMove;
	public Move tempBestMove;
	public List<Move> bestMoves = new ArrayList<>();
		
	public Move getBestMove(ImmutableBoard<Move> b, int maxDepth) {
		depthOfBestMove = b.getHistory().size();
		int counter = 0;
		while (counter < maxDepth) {
			if(bestMoves.size() > 0){
				for(Move move : bestMoves){
					b = b.makeMove(move);
				}
			}
			alphaBeta(b, Integer.MIN_VALUE, Integer.MAX_VALUE, maxDepth);
			bestMoves.add(tempBestMove);
			counter++;
		}
		return bestMoves.get(0);
	}
	
	public int alphaBeta(ImmutableBoard<Move> b, int alpha, int beta, int maxDepth) {
		if (b.isWin()) return -(1000 - b.getHistory().size());
		if (b.isDraw()) return 0;
		if (transTable.containsKey(b.hashCode())) return transTable.get(b.hashCode());
		if (b.getHistory().size() == maxDepth) {
			tempBestMove = monteCarlo(b, 1000);
			return -10000;
		}
		int bestValue = alpha;
		for (Move move : b.moves()) {
			int value = -alphaBeta(b.makeMove(move), -beta, -bestValue, maxDepth);
			if (value > bestValue) {
				bestValue = value;
				if (bestValue >= beta) continue;
				transTable.put(b.hashCode(), bestValue);
				if (b.getHistory().size() == depthOfBestMove) {
					tempBestMove = move;
				}
			}
		}
		return bestValue;
	}

	public int playRandomly(ImmutableBoard<Move> b) {
		while (b.isWin() == false) {
			if (b.isDraw()) return 0;
			Random r = new Random();
			Move randomMove = b.moves().get(r.nextInt(b.moves().size()));
			b = b.makeMove(randomMove);
		}
		return (b.isBeginnersTurn() ? -1 : 1); // -turn
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