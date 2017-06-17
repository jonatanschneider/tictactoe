package p20;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class AI<Move> {
	private Hashtable<Integer, Integer> transTable = new Hashtable<>();
	private int depthOfBestMove;
	private Move tempBestMove;
	private List<Move> bestMoves = new ArrayList<>();
	
	public List<Move> getListOfBestMoves(){ return bestMoves; }
	
	public int getBestMove(ImmutableBoard b, int maxDepth) {
		depthOfBestMove = b.getDepth();
		int counter = 0;
		while (counter < maxDepth) {
			ImmutableBoard tempBoard = b.clone();
			for(Move move : bestMoves){
				b2 = b2.makeMove(move);
			}
			alphaBeta(b2, Integer.MIN_VALUE, Integer.MAX_VALUE, maxDepth);
			bestMoves.add(tempBestMove);
			counter++;
		}
		return bestMoves.get(0);
	}
}