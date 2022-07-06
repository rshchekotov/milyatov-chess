package org.frost.chess.pieces;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessPieceTest {
    @Test
    public void testPiece() {
        ChessPiece blackRook = new ChessPiece(4);
        assertFalse(blackRook.isWhite());
        assertEquals(4, blackRook.getValue());
        assertEquals('r', blackRook.getName());
        blackRook.getValue();

        ChessPiece whiteRook = new ChessPiece(12);
        assertTrue(whiteRook.isWhite());
        assertEquals(12, whiteRook.getValue());
        assertEquals('R', whiteRook.getName());
    }
}