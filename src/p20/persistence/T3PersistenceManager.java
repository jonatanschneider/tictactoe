package p20.persistence;

import p20.ImmutableBoard;
import p20.T3Board;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by David Donges on 26.06.2017.
 */
public class T3PersistenceManager extends BoardPersistenceManager<Integer> {
    @Override
    protected String boardToString(ImmutableBoard<Integer> board) {
        // Map board's history (Integers) to strings and join them
        return board.getHistory().stream().map(i -> i.toString()).collect(Collectors.joining(","));
    }

    @Override
    protected ImmutableBoard<Integer> stringToBoard(String savegame, boolean isFlipped) {
        T3Board board = new T3Board();
        if(isFlipped) {
            board.flip();
        }

        try {
            // Split the string and generate an Integer array containing the moves
            Integer[] moves = Arrays.stream(savegame.split(","))
                    .mapToInt(Integer::parseInt).boxed().toArray(Integer[]::new);
            // Make the moves and return the T3Board
            return board.makeMove(moves);
        } catch(NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }
}
