package p20.ui;

import p20.AI;
import p20.ImmutableBoard;
import p20.persistence.BoardPersistenceManager;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * This class provides a skeletal implementation of an console based user interface
 * for games implementing the {@link ImmutableBoard} interface.
 *
 * @param <T> the move type of this game
 */
public abstract class BaseUI<T> {
    /**
     * Instance of the ImmutableBoard interface implementing the game logic
     */
    protected ImmutableBoard<T> board;

    /**
     * Instance of the {@Link AI} class offering artificial intelligence functionality
     */
    private AI ai;

    /**
     * Path to savegame
     * Can be overwritten by implementing classes.
     */
    protected Path savegamePath = Paths.get(System.getProperty("user.dir") + "/savegame.board");

    /**
     * Flag indicating if the game is running
     */
    private boolean isRunning;

    /**
     * Instance of the {@Link Scanner} class used for console interaction
     */
    private Scanner scanner;

    public BaseUI() {
        board = getNewBoard();
        ai = new AI();
        scanner = new Scanner(System.in);
    }

    /**
     * Runs the game
     */
    public void run() {
        this.isRunning = true;
        while(isRunning) {
            if(board.isWin()) {
                // TODO: add win action
                System.out.println("Win");
                break;
            }
            if(board.isDraw()) {
                // TODO: add draw action
                System.out.println("Draw");
                break;
            }
            if(board.isBeginnersTurn()) {
                printInputInstructions();
                processInput(scanner.next());
            } else {
            	T move = runAI();        
            	board = board.makeMove(move);
            }
        }
    }

    /**
     * Processes the user input and invokes a method matching the command.
     * Prints an error message in case the input doesn't match any command.
     * @param input user input
     */
    private void processInput(String input) {
        input = input.toLowerCase();

        if(isMatch(input, "flip")) {
            flip();
        } else if(isMatch(input, "undo")) {
            undo();
        } else if(isMatch(input, "new")) {
            startNew();
        } else if(isMatch(input, "exit")) {
            exit();
        } else if(isMatch(input, "save")) {
            save();
        } else if(isMatch(input, "load")) {
            load();
        } else if(isMatch(input, "help", "?")) {
            help();
        } else if(move(input)) {
            // Empty, Action is in if's head so it effects the execution of the else
            // branch indicating that there was no action matching the user's input
        } else {
            // Output default message indicating that the input could not be process
            System.out.println("Das habe ich leider nicht verstanden.");
        }
    }

    /**
     * Checks if the input matches any the passed commands.
     * The input must match the beginning (or the whole word) to conform a match.
     * @param input user input to check for
     * @param commands variable amount of commands (varargs) that could get matched
     * @return Returns {@code true} in case the input matches any of the passed commands
     */
    private boolean isMatch(String input, String... commands) {
        for(String command : commands) {
            // Check if the input string matches the beginning of the command
            if(command.indexOf(input) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Flips the board
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

    private void exit() {
        isRunning = false;
    }

    /**
     * Saves the current game
     */
    protected void save() {;
        if(getPersistenceManager().save(board, savegamePath)) {
            System.out.println("Das Spiel wurde gespeichert.");
        } else {
            System.out.println("Das Spiel konnte nicht gespeichert werden.");
        }
    }

    /**
     * Loads the game previously saved
     */
    protected void load() {
        ImmutableBoard<T> loadedBoard = getPersistenceManager().load(savegamePath);
        if(loadedBoard != null) {
            board = loadedBoard;
            System.out.println("Das Spiel wurde geladen.");
        } else {
            System.out.println("Das Spiel konnte nicht geladen werden.");
        }
    }

    /**
     * Get a string describing the commands working independently from the specific game.
     * Can be used in the game-specific help.
     * @return help text describing generic ui commands
     */
    protected String getBaseHelp() {
        return "undo\t\tMacht den letzten Spielzug rückgängig\n" +
                "flip\t\tTauscht Spielsteine: X gegen O und umgekehrt\n" +
                "new\t\t\tBeginnt ein neues Spiel\n" +
                "exit\t\tBeendet das Programm\n" +
                "save\t\tSpeichert aktuelle Spielsituation\n" +
                "load\t\tLädt letzte gespeicherte Spielsituation\n" +
                "help\t\tZeigt diese Übersicht an\n" +
                "?\t\t\tWie “help”\n" +
                "\n" +
                "Schließen Sie die Eingabe mit der <Eingabe>-Taste ab!\n" +
                "Bei Befehlen genügt auch die Eingabe des ersten Buchstabens.";
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
     * Prints the instructions preceding the user's input
     */
    protected abstract void printInputInstructions();

    /**
     * Tries to make a move based on the user's input.
     *
     * @param input user's input that shall be evaluated to a move
     * @return {@code true} when the input has been handled, {@code false} on illegal inputs
     */
    protected abstract boolean move(String input);
    
    /**
     * Runs the AI with suitable arguments for the chosen game
     * @return move that the ai calculated
     */
    protected abstract T runAI();

    /**
     * Gets an instance of the PersistenceManager being able to persist and load the played game.
     * @return {@link BoardPersistenceManager} instance of the persistence manager for the played game
     */
    protected abstract BoardPersistenceManager getPersistenceManager();

}
