package test.GameTest;

import game.Board;
import game.TileBag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board b;

    @BeforeEach
    public void setup() {
        this.b = new Board(new TileBag(36));
        b.getTileBag().populateBag();
    }

    /**
     * Test if isValidLocation is working as expected
     */
    @Test
    void testValidLocation() {
        assertTrue(b.isValidLocation(0));
        assertTrue(b.isValidLocation(35));
        assertFalse(b.isValidLocation(40));
    }

    /**
     * Test if location is empty
     */
    @Test
    void testIsEmptyLocation() {
        assertTrue(b.isEmptyLocation(1));
        b.movePiece(1, b.getTileBag().takeRandomPiece());
        assertFalse(b.isEmptyLocation(1));
    }

    /**
     * Test bonusQuotients generated by Board class
     * and BoardLocation class
     */
    @Test
    void testBonusQuotients() {
        assertEquals(1, b.getBoardLocation(0).getScorePoint());
        assertEquals(2, b.getBoardLocation(10).getScorePoint());
        assertEquals(3, b.getBoardLocation(2).getScorePoint());
        assertEquals(4, b.getBoardLocation(11).getScorePoint());
    }

    /**
     * Test if takePiece() method of Board works
     */
    @Test
    void testGetPiece() {
        b.movePiece(1, b.getTileBag().takeRandomPiece());
        assertNotNull(b.getPiece(1));
    }

    /**
     * Test if setMove() method works properly
     */
    @Test
    void testSetMove() {
        b.setMove(1, b.getTileBag().takeRandomPiece());
        assertEquals(b.getPiece(1), b.getBoardLocation(1).getPiece());
    }

    /**
     * Test validity of a move
     */
    @Test
    void testIsValidMove() {
        assertFalse(b.isValidMove(10, b.getTileBag().takeRandomPiece()));
        assertTrue(b.isValidMove(0, b.getTileBag().takeRandomPiece()));

    }

    @Test
    void testGetIndex() {
        assertEquals(8, b.getIndex(2, 2));
        assertEquals(24, b.getIndex(4, 4));
    }

    @Test
    void testMovePiece() {
        b.movePiece(10, b.getTileBag().takeRandomPiece());
        assertEquals(b.getPiece(10), b.getBoardLocation(10).getPiece());
    }

    @Test
    void testIsValidColor() {
        //TODO: implement test
    }

    @Test
    void testGetLeftPiece() {
        //TODO: implement test
    }

    @Test
    void testBoardIsEmpty() {
        assertTrue(b.boardIsEmpty());
        b.movePiece(0, b.getTileBag().takeRandomPiece());
        assertFalse(b.boardIsEmpty());
    }

}
