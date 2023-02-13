package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.enums.Color;

public class Queen extends ChessPiece {
    public Queen(Board board, Color color) {
        super(board, color);
    }
    @Override
    public String toString() {
        return "Q ";
    }

    @Override
    public boolean[][] possibleMoves() {
        return new boolean[0][];
    }
}
