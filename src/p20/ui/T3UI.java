package p20.ui;

import p20.AI;
import p20.ImmutableBoard;
import p20.T3Board;
import p20.persistence.BoardPersistenceManager;
import p20.persistence.T3PersistenceManager;

import java.nio.file.Paths;
import java.util.stream.Collectors;

/**
 * Created by David Donges on 29.06.2017.
 */
public class T3UI extends BaseUI<Integer> {

    public T3UI() {
        super();
        // Overwrite the savegame path
        savegamePath = Paths.get(System.getProperty("user.dir") + "/savegame.t3");
    }

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
    protected void printInputInstructions() {
        System.out.println(board.toString());
        System.out.println("Gib gültigen Spielzug ein (1-9 + <ENTER>)");
        System.out.println("[0: Computer zieht, ?: Hilfe]:");
    }

    @Override
    protected boolean move(String input) {
        int field;
        try {
        	// make sure only the first character of the input gets checked
            field = Integer.parseInt(input.trim().substring(0, 1));
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

    @Override
    protected BoardPersistenceManager getPersistenceManager() {
        return new T3PersistenceManager();
    }
}
