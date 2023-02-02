package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.enums.Color;

public class ChessPiece extends Piece {

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

    protected void isThereOpponentPiece(Position position) {
    }

    protected void increaseMovieCont() {

    }

    protected void decreaseMovieCont() {

    }
}
