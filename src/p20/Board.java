package p20;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public abstract class Board<Move> implements ImmutableBoard<Move> {
    protected int[] board;
    protected Board<Move> parent;
    protected Move move;
    protected boolean isFlipped;

    @Override
    public ImmutableBoard<Move> undoMove() {
        return parent;
    }

    @Override
    public List<Move> getHistory() {
        Board<Move> board = this;
        Queue<Move> history = new LinkedList<>();

        while(board != null) {
            if(board.move != null) {
                history.add(board.move);
            }
            board = board.parent;
        }
        return (List) history;
    }

    @Override
    public ImmutableBoard<Move> flip() {
        isFlipped = !isFlipped;
        return this;
    }

    @Override
    public boolean isFlipped() {
        return isFlipped;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(board);
    }
}
