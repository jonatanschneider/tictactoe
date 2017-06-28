package p20.persistence;

import org.junit.Before;
import org.junit.Test;
import p20.T3Board;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * Created by David Donges on 27.06.2017.
 */
public class T3PersistenceManagerTest {
    private final Path SAVE_PATH = Paths.get(System.getProperty("user.dir") + "/savegame.t3");
    private T3Board board;
    private T3PersistenceManager persistenceManager;

    @Before
    public void initialize() {
        board = (T3Board) new T3Board().makeMove(0).makeMove(6).makeMove(3).makeMove(2);
        persistenceManager = new T3PersistenceManager();
    }

    @Test
    public void savingT3BoardShouldSucceed() {
        assertTrue(saveT3(board, SAVE_PATH));
    }

    @Test
    public void loadedT3BoardShouldBeEqualToSavedBoard() {
        saveT3(board, SAVE_PATH);
        assertEquals(board.hashCode(), loadT3(SAVE_PATH).hashCode());
    }

    @Test
    public void loadedFlippedT3BoardShouldBeEqualToSavedBoard() {
        saveT3((T3Board) board.flip(), SAVE_PATH);
        T3Board loadedT3 = loadT3(SAVE_PATH);
        assertEquals(board.hashCode(), loadedT3.hashCode());
        assertTrue(loadedT3.isFlipped());
    }

    private boolean saveT3(T3Board board, Path path) {
        return persistenceManager.save(board, path);
    }

    private T3Board loadT3(Path path) {
        return persistenceManager.load(path);
    }

}
