package p20;

import java.nio.file.Path;
import java.util.List;

@SuppressWarnings("unchecked")
public interface ImmutableBoard<Move> {
	ImmutableBoard<Move> makeMove(Move move);
	default ImmutableBoard<Move> makeMove(Move... moves) {
		ImmutableBoard<Move> b = this;
		for(Move move : moves) b = b.makeMove(move);
		return b;
	}
	Board undoMove();
	List<ImmutableBoard<Move>> moves();
	List<Move> getHistory();

	boolean isWin();
	boolean isDraw();
	default boolean isBeginnersTurn() {
		return getHistory().size() % 2 == 0;
	}

	void flip();
	boolean isFlipped();
	String toString();

	void load(String name);
	void load(Path path);
	void save(String name);
	void save(Path path);
}

