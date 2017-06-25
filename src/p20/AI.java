package p20;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

public class AI<Move> {
	private Hashtable<Integer, Integer> transTable = new Hashtable<>();
	private List<Move> bestMoves = new ArrayList<>(); //Zugfolge
	private List<Integer> bestMovesScore = new ArrayList<>();
		
	public Move getBestMove(ImmutableBoard<Move> b) {
		for(int i = 1; i < 10; i++){
			alphaBeta(b, Integer.MIN_VALUE, Integer.MAX_VALUE, i);
		}
		return bestMoves.get(0);
	}
	
	private int alphaBeta(ImmutableBoard<Move> b, int alpha, int beta, int depth) {
		if (b.isWin()) return -(1000 - b.getHistory().size());
		if (b.isDraw()) return 0;
		if (transTable.containsKey(b.hashCode())) return transTable.get(b.hashCode());
		if (depth == 0) {
			Move m = monteCarlo(b, 1000);
			bestMoves.add(b.getHistory().size() - 1, m);
			//dummy score cause monteCarlo gives no guarantees about the result
			bestMovesScore.add(b.getHistory().size() - 1, -10000);
			return -10000;
		}
		int bestValue = alpha;
		if(bestMoves.size() >= b.getHistory().size()){
		}
		for (Move move : b.moves()) {
			int value = -alphaBeta(b.makeMove(move), -beta, -bestValue, depth - 1);
			if (value > bestValue) {
				bestValue = value;
				if (bestValue >= beta) break;
				transTable.put(b.hashCode(), bestValue);
				//Prüfe ob der neue Wert für diese Tiefe besser ist als der alte
				//Wenn ja setze den besseren Wert und speichere den move für diese Tiefe
				if(bestMovesScore.size() >= b.getHistory().size()){
					if(bestMovesScore.get(b.getHistory().size()) < bestValue){
						bestMovesScore.add(b.getHistory().size(), bestValue);
						bestMoves.add(b.getHistory().size(), move);
					}
				}else{
					bestMovesScore.add(bestValue);
					bestMoves.add(move);
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