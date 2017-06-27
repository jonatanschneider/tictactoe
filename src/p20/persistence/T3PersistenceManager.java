package p20.persistence;

import p20.T3Board;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by David Donges on 26.06.2017.
 */
public class T3PersistenceManager extends BoardPersistenceManager<T3Board> {
    @Override
    protected String boardToString(T3Board board) {
        // Map board's history (Integers) to strings and join them
        String savegame = board.getHistory().stream().sequential().map(i -> i.toString()).collect(Collectors.joining(","));
        // Add flipped flag
        if (board.isFlipped()) {
            savegame += ",f";
        }
        // Add terminating line break according to specification
        return savegame + "\n";
    }

    @Override
    protected T3Board stringToBoard(String savegame) {
        T3Board board = new T3Board();
        // If the board is flipped (According to specification)...
        if(savegame.toLowerCase().indexOf("f") > -1) {
            // ... Flip the board
            board.flip();
            // ... Cut off the flipped flag so the move generation stream works properly
            savegame = savegame.substring(0, savegame.length() - 3);
        }
        // Split the string and generate an Integer array containing the moves
        Integer[] moves = Arrays.stream(savegame.split(","))
                .mapToInt(Integer::parseInt).boxed().toArray(Integer[]::new);
        // Make the moves and return the T3Board
        return (T3Board) board.makeMove(moves);
    }
}
