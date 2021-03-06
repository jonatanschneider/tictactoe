package p20;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;

public class AI<Move> {
	private int beginningDepth;
	private int monteCarloDepth;
	private Hashtable<Integer, Integer> cacheScore = new Hashtable<>();
	private Hashtable<Integer, Move> cacheMove = new Hashtable<>();
	private HashMap<Integer, List<Move>> killerMoves = new HashMap<>();
	private HashMap<Integer, Move> bestMoves = new HashMap<>();
	private HashMap<Integer, Integer> bestMovesScore = new HashMap<>();
	public List<Move> getListOfBestMoves(){return new ArrayList<Move>(bestMoves.values());}
	
	public Move getBestMove(ImmutableBoard<Move> b, int depths, int monteCarloDepth) {
		this.beginningDepth = b.getHistory().size();
		this.monteCarloDepth = monteCarloDepth;
		//iterative depth deepening first search
		int maxDepth = beginningDepth + depths;
		for(int i = beginningDepth; i < maxDepth; i++){
			alphaBeta(b, Integer.MIN_VALUE, Integer.MAX_VALUE, i);
		}
		return bestMoves.get(beginningDepth);
	}

	public int alphaBeta(ImmutableBoard<Move> b, int alpha, int beta, int depth) {
		int boardDepth = b.getHistory().size();
		if (b.isWin()) return -(1000 - boardDepth);
		if (b.isDraw()) return 0;
		//Transposition Table
		if (cacheScore.containsKey(b.hashCode())){
			bestMoves.put(boardDepth, cacheMove.get(b.hashCode()));
			bestMovesScore.put(boardDepth, cacheScore.get(b.hashCode()));
			return cacheScore.get(b.hashCode());
		}
		//Monte-Carlo
		if (depth == beginningDepth) {
			Move temp = monteCarlo(b);
			bestMoves.put(boardDepth, temp);
			bestMovesScore.put(boardDepth, -1);
			return -Integer.MIN_VALUE;
		}
		int bestValue = alpha;
		List<Move> boardMoves = b.moves();
		List<Move> moves = new ArrayList<>();
		//Sort killer-moves
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
					//Save killer-moves
					if(!killerMoves.containsKey(boardDepth)){
						killerMoves.put(boardDepth, new ArrayList<Move>());
					}
					//make sure killer moves only get added once to a list
					if(!killerMoves.get(boardDepth).contains(move)) killerMoves.get(boardDepth).add(move);
					break;
				}
				if(bestMovesScore.containsKey(boardDepth)){
					if(bestValue > bestMovesScore.get(boardDepth)){
						cacheScore.put(b.hashCode(), bestValue);
						cacheMove.put(b.hashCode(), move);
						bestMoves.put(boardDepth, move);
						bestMovesScore.put(boardDepth, bestValue);
					}
				}else{
					cacheScore.put(b.hashCode(), bestValue);
					cacheMove.put(b.hashCode(), move);
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
			List<Move> moves = b.moves();
			Random r = new Random();
			Move randomMove = moves.get(r.nextInt(moves.size()));
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

	public Move monteCarlo(ImmutableBoard<Move> b) {
		int[] values = new int[b.moves().size()];
		int maxValue = Integer.MIN_VALUE;
		int indexOfMaxValue = -1;
		int[][] evaluated = evaluateMoves(b, monteCarloDepth);
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
	
	public Move monteCarlo(ImmutableBoard<Move> b, int depth){
		this.monteCarloDepth = depth;
		return monteCarlo(b);
	}
}