package p20.persistence;

import p20.ImmutableBoard;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by David Donges on 26.06.2017.
 */
public abstract class BoardPersistenceManager<T extends ImmutableBoard> {
    public T load(Path path) {
        String savegame = "";
        try {
            savegame = new String(Files.readAllBytes(path));
        } catch(IOException e) {
            // TODO: handle exception properly
            e.printStackTrace();
        }
        return stringToBoard(savegame);
    }

    public boolean save(T board, Path path) {
        String savegame = boardToString(board);
        try {
            Files.write(path, savegame.getBytes());
        } catch(IOException e) {
            return false;
        }
        return true;
    }

    protected abstract String boardToString(T board);

    protected abstract T stringToBoard(String savegame);
}
