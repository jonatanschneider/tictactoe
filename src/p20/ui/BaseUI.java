package p20.ui;

import p20.ImmutableBoard;

import java.nio.file.Path;

/**
 * This class provides a skeletal implementation of an console based user interface
 * for games implementing the {@link ImmutableBoard} interface.
 */
public abstract class BaseUI<T> {
    /**
     * Instance of the ImmutableBoard interface implementing the game logic
     */
    protected ImmutableBoard<T> board;

    public BaseUI() {
        board = getNewBoard();
    }

    /**
     * Runs the game
     */
    public void run() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Flips the baord
     */
    private void flip() {
        board.flip();
    }

    /**
     * Undoes the last move
     */
    private void undo() {
        board = board.undoMove();
    }

    /**
     * Resets the current game to the default (empty) state
     */
    private void startNew() {
        board = getNewBoard();
    }

    /**
     * Returns a new instance of the board
     * @return new instance of the board
     */
    protected abstract ImmutableBoard<T> getNewBoard();

    /**
     * Prints the game's help text
     */
    protected abstract void help();

    /**
     * Saves the current game to the specified path
     * @param path
     */
    protected abstract void save(Path path);

    /**
     * Loads the game from the specified path
     * @param path
     */
    protected abstract void load(Path path);

    /**
     * Prints the instructions preceding the user's input
     */
    protected abstract void printInputInstructions();

    /**
     * Tries to make a move based on the user's input.
     *
     * @param input user's input that shall be evaluated to a move
     * @return {@code true} when the attempted move succeeded
     */
    protected abstract boolean move(String input);
}
