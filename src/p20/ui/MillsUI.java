package p20.ui;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import p20.AI;
import p20.ImmutableBoard;
import p20.Mills;
import p20.Move;
import p20.persistence.MillsPersistenceManager;

public class MillsUI extends BaseUI<Move> {
    @Override
    protected ImmutableBoard<Move> getNewBoard() {
        return new Mills();
    }

    @Override
    protected void help() {
    	//TODO: correct string
        String millsHelp = "Spielen Sie Tic-Tac-Toe gegen den Computer. Die Positionen des Spielfelds sind von 1-24 nummeriert:\n" +
                "\n" +
                "%1$1s-----%2$1s-----%3$1s\n" +
                "| %9$1s---%10$1s---%11$1s |\n" +
                "| | %17$1s-%18$1s-%19$1s | |\n" +
                "%8$1s-%16$1s-%24$1s   %20$1s-%12$1s-%4$1s\n" +
                "| | %23$1s-%22$1s-%21$1s | |\n" +
                "| %15$1s---%14$1s---%13$1s |\n" +
                "%7$1s-----%6$1s-----%5$1s" + 
                "\n" +
                "Es wird abwechselnd gezogen. Gewonnen hat, wer zuerst drei Spielsteine (entweder X oder O) in Reihe anordnet." +
                "\n\n" +
                "1-9\t\t\tPosition des Feldes, das man besetzten möchte\n" +
                "0\t\t\tDer Computer macht für Sie einen Zug\n" +
                getBaseHelp();
    }

    @Override
    protected void save(Path path) {
        MillsPersistenceManager persistenceManager = new MillsPersistenceManager();
        persistenceManager.save((Mills) board, path);
    }

    @Override
    protected void load(Path path) {
        MillsPersistenceManager persistenceManager = new MillsPersistenceManager();
        board = persistenceManager.load(path);
    }

    @Override
    protected void printInputInstructions() {
        System.out.println(board.toString());
        //TODO: Move instruction in help
        System.out.println("Gib gültigen Spielzug ein ('1-24'-'1-24'-'1-24' + <ENTER>)");
        System.out.println("[0: Computer zieht, ?: Hilfe]:");
    }

    @Override
    protected boolean move(String input) {
    	Pattern p = Pattern.compile("\\d{1,2}");
    	Matcher matcher = p.matcher(input);
    	List<String> matches = new ArrayList<>();
    	while(matcher.find()) matches.add(matcher.group());
   
    	int firstArg = -1;
    	int secondArg = -1;
    	int thirdArg = -1;
    	if(matches.size() > 0){
    		try {
                firstArg = Integer.parseInt(matches.get(0));
                secondArg = (matches.size() > 1) ? Integer.parseInt(matches.get(1)) : -1;
                thirdArg = (matches.size() > 2) ? Integer.parseInt(matches.get(2)) : -1;
            } catch (NumberFormatException e) {
                return false;
            }
    	}
        if(firstArg == 0){
        	Move m = runAI();
        	board = board.makeMove(m);
        	return true;
        }
        
        // TODO: print error message / move suggestions here
        // Map human friendly field number to count-by-zero
        //the second argument represents remove while game is in first phase
        Move move;
        if(board.getHistory().size() < 19) move = new Move(firstArg -1, -1, secondArg - 1);
        else move = new Move(firstArg -1, secondArg -1, thirdArg -1);
        
        if(board.moves().contains(move)) {
            board = board.makeMove(move);
            return true;
        }
        return false;
    }

	@Override
	protected Move runAI() {
		return new AI<Move>().monteCarlo(board, 10);
	}
}
