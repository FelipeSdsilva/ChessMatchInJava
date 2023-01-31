package chess;

import boardgame.Board;
import chess.enums.Color;

public class ChessMatch {

    private int turn;
    private Color currentPlayer;
    private boolean check;
    private boolean checkMatch;
    private ChessPiece enPassantVulnerable;
    private ChessPiece promoted;
    private final Board board;

    public ChessMatch() {
        board = new Board(8, 8);
    }

    public ChessPiece[][] getPieces() {
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                mat[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        return mat;
    }
}
