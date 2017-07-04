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
                "1-9\t\tPosition des Feldes, das man besetzen möchte\n" +
                "0\t\tDer Computer macht für Sie einen Zug\n" +
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
            field = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return false;
        }
        // Cancel move attempt in case the number is too high
        if(field > 9) return false;

        // Run ai on input = 0
        if (field == 0)
            board = board.makeMove(runAI());
        // Make move in case it is valid...
        else if (board.moves().contains(field - 1))
            board = board.makeMove(field - 1);
        // ... Offer possible moves in case it is not
        else
            System.out.println("\nDas Spielfeld ist besetzt. Frei sind die Felder " +
                    board.moves().stream().map(i -> (++i).toString()).collect(Collectors.joining(", ")));
        // Return true as we handled the input
        return true;
    }

	@Override
	protected Integer runAI() {
		//Integer move = new AI<Integer>().monteCarlo(board, 100);
		Integer move = new AI<Integer>().getBestMove(board, 8, 100);
        System.out.println("\nIch denke nach ... und setze auf " + (move+1) + ".");
        return move;
	}

    @Override
    protected BoardPersistenceManager getPersistenceManager() {
        return new T3PersistenceManager();
    }
}
