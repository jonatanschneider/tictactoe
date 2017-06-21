package p20;

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
                history.add((Move) board.move);
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
        int hashCode = 0;
        // Generate number based on the ternary numeral system to identify the play position
        // without respecting java object references
        for(int i = 0; i < board.length; i++) {
            // Board's fields can hold three states -1 (player 1) / 0 (not set) / 1 (player 2)
            // Add 1 to the field's state so the result is within 0 - 2 allowing us to
            // build a ternary number based on the field's index
            hashCode += Math.pow(board[i] + 1, i);
        }
        return hashCode;
    }
}
