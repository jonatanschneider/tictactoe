package p20;

import java.util.List;

@SuppressWarnings("unchecked")
public interface ImmutableBoard<Move> {
	ImmutableBoard makeMove(Move move);
	default ImmutableBoard makeMove(Move... moves) {
		ImmutableBoard b = this;
		for(Move move : moves) b = b.makeMove(move);
		return b;
	}
	ImmutableBoard undoMove();
	List<Move> moves();
	List<Move> getHistory();

	boolean isWin();
	boolean isDraw();
	default boolean isBeginnersTurn() {
		return getHistory().size() % 2 == 0;
	}

	ImmutableBoard flip();
	boolean isFlipped();
	String toString();
}
