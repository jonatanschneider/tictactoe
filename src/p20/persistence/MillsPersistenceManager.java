package p20.persistence;

import p20.Mills;
import p20.Move;
import p20.T3Board;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by David Donges on 26.06.2017.
 */
public class MillsPersistenceManager extends BoardPersistenceManager<Mills> {
    private int moveCounter = 0;
    @Override
    protected String boardToString(Mills board) {
        // Map board's history (Move objects) to strings and join them
        return board.getHistory().stream().map(i -> i.toSaveString()).collect(Collectors.joining(","));
    }

    @Override
    protected Mills stringToBoard(String savegame, boolean isFlipped) {
        Mills board = new Mills();
        if(isFlipped) {
            board.flip();
        }
        moveCounter = 0;
        // Split the string and generate an Integer array containing the moves
        Move[] moves = Arrays.stream(savegame.split(","))
                .map(this::fromString).toArray(Move[]::new);
        // Make the moves and return the MillsBoard
        return (Mills) board.makeMove(moves);
    }

    private Move fromString(String move) {
        moveCounter++;
        Integer[] moveFragments = Arrays.stream(move.split("-")).map(Integer::parseInt).toArray(Integer[]::new);
        if(moveFragments.length == 3) {
            return new Move(moveFragments[1], moveFragments[0], moveFragments[2]);
        }
        if(moveFragments.length == 2) {
            if(moveCounter > 18) {
                return new Move(moveFragments[1], moveFragments[0]);
            }
            return new Move(moveFragments[0], -1, moveFragments[1]);
        }
        return new Move(moveFragments[0]);
    }
}