package p20.ui;

import p20.AI;
import p20.ImmutableBoard;
import p20.T3Board;
import p20.persistence.T3PersistenceManager;

import java.nio.file.Path;

/**
 * Created by David Donges on 29.06.2017.
 */
public class T3UI extends BaseUI<Integer> {
    @Override
    protected ImmutableBoard<Integer> getNewBoard() {
        return new T3Board();
    }

    @Override
    protected void help() {
        System.out.println("T3 Help...");
    }

    @Override
    protected void save(Path path) {
        T3PersistenceManager persistenceManager = new T3PersistenceManager();
        persistenceManager.save((T3Board) board, path);
    }

    @Override
    protected void load(Path path) {
        T3PersistenceManager persistenceManager = new T3PersistenceManager();
        board = persistenceManager.load(path);
    }

    @Override
    protected void printInputInstructions() {
        System.out.println(board.toString());
        System.out.println("Gib gültigen Spielzug ein (1-9 + <ENTER>)");
        System.out.println("[0: Computer zieht, ?: Hilfe]:");
    }

    @Override
    protected boolean move(String input) {
        int field;
        try {
            field = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return false;
        }
        // TODO: make AI move on input: 0
        // TODO: print error message / move suggestions here
        // Map human friendly field number to count-by-zero
        if(board.moves().contains(field - 1)) {
            board = board.makeMove(field - 1);
            return true;
        }
        return false;
    }

	@Override
	protected Integer runAI() {
		return new AI<Integer>().getBestMove(board, 8, 100);
	}
}
