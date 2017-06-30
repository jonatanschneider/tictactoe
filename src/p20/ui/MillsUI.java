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
        String millsHelp = "Spielen Sie Mühle gegen den Computer. Die Positionen des Spielfelds sind von 1-24 nummeriert:\n" +
                "\n" +
                "01-------02------03\n" +
                "|  09----10---11  |\n" +
                "|  |  17-18-19 |  |\n" +
                "08-16-24    20-12-04\n" +
                "|  |  23-22-21 |  |\n" +
                "|  15----14----13 |\n" + 
                "07-------06------05\n" +
                "\n\n" +
                "Einen Zug können Sie auf die folgenden Art und Weisen machen:\n" +
                "[Ziehe zu Position] oder\n" +
                "[Ziehe zu Position]-[Position des zu entfernenden Steins] oder\n" +
                "[Ziehe zu Position]-[Von Position]-[Position des zu entfernenden Steins]\n" + 
                "\n\n" +
                "1-24\t\t\tPosition des Feldes, das man besetzten möchte\n" +
                "0\t\t\tDer Computer macht für Sie einen Zug\n" +
                getBaseHelp() + "\n\n" +
                
		        "Es wird abwechselnd gezogen. Verloren hat, wer nur noch zwei eigene Spielsteine hat.\n" +
		        "Eine Mühle schließen Sie indem sie drei ihrer Steine in Reihe bringen (horizontal oder vertikal).\n" + 
		        "Das Spiel beginnt mit dem abwechselnden Setzen von jeweils 9 Steinen. Sobald jeder Spieler 9x \n" +
		        "am Zug war darf jeder Spieler in seinem Zug einen Stein von sich auf ein freies Nachbarfeld bewegen.\n" +
		        "Falls Sie in ihrem Zug eine Mühle schließen dürfen Sie einen Stein des Gegners entfernen, " +
		        "welcher nicht in einer Mühle ist,\nes sei denn alle seine Steine sind in einer Mühle, dann dürfen Sie auch " +
		        "einen davon auswählen.\n" +
		        "Sobald ein Spieler nur noch drei eigene Steine auf dem Feld hat, darf er mit seinen Steinen zu jeder Position springen\n";
        System.out.println(millsHelp);
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
