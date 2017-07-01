package p20.ui;

import p20.AI;
import p20.ImmutableBoard;
import p20.T3Board;
import p20.persistence.T3PersistenceManager;

import java.nio.file.Path;
import java.util.stream.Collectors;

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
        String t3Help = "Spielen Sie Tic-Tac-Toe gegen den Computer. Die Positionen des Spielfelds sind von 1-9 nummeriert:\n" +
                "\n" +
                "1 2 3\n" +
                "4 5 6\n" +
                "7 8 9\n" +
                "\n" +
                "Es wird abwechselnd gezogen. Gewonnen hat, wer zuerst drei Spielsteine (entweder X oder O) in Reihe anordnet." +
                "\n\n" +
                "1-9\t\t\tPosition des Feldes, das man besetzten möchte\n" +
                "0\t\t\tDer Computer macht für Sie einen Zug\n" +
                getBaseHelp();
        System.out.println(t3Help);
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
        if (field == 0) {
            int move = runAI();
            board = board.makeMove(move);
        }
        // Map human friendly field number to count-by-zero
        if (board.moves().contains(field - 1)) {
            board = board.makeMove(field - 1);
        } else {
            System.out.println("\nDas Spielfeld ist besetzt. Frei sind die Felder " +
                    board.moves().stream().map(i -> (++i).toString()).collect(Collectors.joining(", ")));
        }
        return true;
    }

	@Override
	protected Integer runAI() {
		return new AI<Integer>().getBestMove(board, 8, 100);
	}
}
