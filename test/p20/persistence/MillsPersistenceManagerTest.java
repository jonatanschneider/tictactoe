package p20.persistence;

import org.junit.Before;
import org.junit.Test;
import p20.Mills;
import p20.Move;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by David Donges on 27.06.2017.
 */
public class MillsPersistenceManagerTest {
    private final Path SAVE_PATH = Paths.get(System.getProperty("user.dir") + "/savegame.mills");
    private Mills board;
    private MillsPersistenceManager persistenceManager;

    @Before
    public void initialize() {
        board = (Mills) new Mills()
                .makeMove(new Move(0))
                .makeMove(new Move(2))
                .makeMove(new Move(6))
                .makeMove(new Move(4))
                .makeMove(new Move(3))
                .makeMove(new Move(7))
                .makeMove(new Move(8))
                .makeMove(new Move(21))
                .makeMove(new Move(12, -1, 2))
                .makeMove(new Move(9))
                .makeMove(new Move(11))
                .makeMove(new Move(19))
                .makeMove(new Move(14))
                .makeMove(new Move(15))
                .makeMove(new Move(23))
                .makeMove(new Move(16))
                .makeMove(new Move(1))
                .makeMove(new Move(2))
                .makeMove(new Move(5, 6))
                .makeMove(new Move(10, 9))
                .makeMove(new Move(13, 5, 11));
        persistenceManager = new MillsPersistenceManager();
    }

    @Test
    public void savingT3BoardShouldSucceed() {
        assertTrue(saveMills(board, SAVE_PATH));
    }

    @Test
    public void loadedT3BoardShouldBeEqualToSavedBoard() {
        saveMills(board, SAVE_PATH);
        assertEquals(board.hashCode(), loadMills(SAVE_PATH).hashCode());
    }

    @Test
    public void loadedFlippedT3BoardShouldBeEqualToSavedBoard() {
        saveMills((Mills) board.flip(), SAVE_PATH);
        Mills loadedMills = loadMills(SAVE_PATH);
        assertEquals(board.hashCode(), loadedMills.hashCode());
        assertTrue(loadedMills.isFlipped());
    }

    private boolean saveMills(Mills board, Path path) {
        return persistenceManager.save(board, path);
    }

    private Mills loadMills(Path path) {
        return persistenceManager.load(path);
    }

}
