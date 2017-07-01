package p20.ui;

import p20.AI;
import p20.ImmutableBoard;
import p20.Mills;
import p20.Move;
import p20.persistence.BoardPersistenceManager;
import p20.persistence.MillsPersistenceManager;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MillsUI extends BaseUI<Move> {

    public MillsUI() {
        super();
        // Overwrite the savegame path
        savegamePath = Paths.get(System.getProperty("user.dir") + "/savegame.mills");
    }

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
   
    	int firstArg = 0;
    	int secondArg = 0;
    	int thirdArg = 0;
    	if(matches.size() > 0){
    		try {
                firstArg = Integer.parseInt(matches.get(0));
                secondArg = (matches.size() > 1) ? Integer.parseInt(matches.get(1)) : 0;
                thirdArg = (matches.size() > 2) ? Integer.parseInt(matches.get(2)) : 0;
            } catch (NumberFormatException e) {
                return false;
            }
    	}
        if(firstArg == 0){
        	Move m = runAI();
        	board = board.makeMove(m);
        	return true;
        }
        
        // Map human friendly field number to count-by-zero
        // the second argument represents remove while game is in first phase
        Move move;
        if(board.getHistory().size() < 19) move = new Move(firstArg -1, -1, secondArg - 1);
        else move = new Move(firstArg -1, secondArg -1, thirdArg -1);
        
		if (board.moves().contains(move)) {
			board = board.makeMove(move);
			return true;
		} else {
			System.out.println("Dieser Zug ist nicht möglich!");
			return false;
		}
    }

	@Override
	protected Move runAI() {
		//using monteCarlo search only for performance
		return new AI<Move>().monteCarlo(board, 10);
		//return new AI<Move>().getBestMove(board, 2, 10);
	}

    @Override
    protected BoardPersistenceManager getPersistenceManager() {
        return new MillsPersistenceManager();
    }
}
