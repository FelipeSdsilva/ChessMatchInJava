package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.enums.Color;

public abstract class ChessPiece extends Piece {

    private Color color;
    private int moveCount;

    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void getChessPosition() {
    }

    protected boolean isThereOpponentPiece(Position position) {
        ChessPiece p = (ChessPiece) getBoard().piecePosition(position);
        return p != null && p.getColor() != color;
    }

    protected void increaseMovieCont() {

    }

    protected void decreaseMovieCont() {

    }

    public boolean canMove(Position position) {
        ChessPiece p = (ChessPiece) getBoard().piecePosition(position);
        return p == null || p.getColor() != getColor();
    }
}
