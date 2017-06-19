package p20;

import java.util.List;

public abstract class Board implements ImmutableBoard {
    private int[] board;
    private Board parent;
    private boolean isFlipped;

    // TODO: add basic constructor in implementing board classes
    public Board(Board parent, boolean isFlipped, Move move) {
        // TODO: copy parent's board and make move
        this.parent = parent;
        this.isFlipped = isFlipped;
    }

    @Override
    public ImmutableBoard undoMove() {
        return parent;
    }

    @Override
    public List<Move> getHistory() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public ImmutableBoard flip() {
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
