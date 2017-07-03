package p20.persistence;

import p20.ImmutableBoard;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by David Donges on 26.06.2017.
 */
public abstract class BoardPersistenceManager<T> {
    public ImmutableBoard<T> load(Path path) {
        String savegame;
        try {
            savegame = new String(Files.readAllBytes(path));
        } catch(IOException e) {
            return null;
        }
        // Flag passed to implementing class indicating whether the board is flipped
        boolean isFlipped = false;

        // Cut off terminating line break
        savegame = savegame.substring(0, savegame.length() - 1);

        // If the there is a flipped flag...
        if(savegame.toLowerCase().indexOf("f") > -1) {
            isFlipped = true;
            // ... Cut off the flipped flag so the move generation stream works properly
            savegame = savegame.substring(0, savegame.length() - 2);
        }
        return stringToBoard(savegame, isFlipped);
    }

    public boolean save(ImmutableBoard<T> board, Path path) {
        String savegame = boardToString(board);
        // Add flipped flag
        if (board.isFlipped()) {
            savegame += ",f";
        }
        // Add terminating line break according to specification
        savegame += "\n";
        try {
            Files.write(path, savegame.getBytes());
        } catch(IOException e) {
            return false;
        }
        return true;
    }

    protected abstract String boardToString(ImmutableBoard<T> board);

    protected abstract ImmutableBoard<T> stringToBoard(String savegame, boolean isFlipped);
}
