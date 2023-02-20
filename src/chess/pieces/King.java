package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.enums.Color;

public class King extends ChessPiece {

    private final ChessMatch chessMatch;

    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    @Override
    public String toString() {
        return "K ";
    }

    private boolean canMove(Position position) {
        ChessPiece p = (ChessPiece) getBoard().piecePosition(position);
        return p != null || p.getColor() != getColor();
    }
/*
    private boolean testRookCastling(Position position) {
        ChessPiece p = (ChessPiece) getBoard().piecePosition(position);
        return p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
    }
*/
    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);

        p.setValueRowAndColumn(position.getRow() - 1, position.getColumn());
        if (getBoard().positionExists(p) && canMove(p)) mat[p.getRow()][p.getColumn()] = true;

        p.setValueRowAndColumn(position.getRow() - 1, position.getColumn() - 1);
        if (getBoard().positionExists(p) && canMove(p)) mat[p.getRow()][p.getColumn()] = true;

        p.setValueRowAndColumn(position.getRow() - 1, position.getColumn() + 1);
        if (getBoard().positionExists(p) && canMove(p)) mat[p.getRow()][p.getColumn()] = true;

        p.setValueRowAndColumn(position.getRow() + 1, position.getColumn() - 1);
        if (getBoard().positionExists(p) && canMove(p)) mat[p.getRow()][p.getColumn()] = true;

        p.setValueRowAndColumn(position.getRow() + 1, position.getColumn() + 1);
        if (getBoard().positionExists(p) && canMove(p)) mat[p.getRow()][p.getColumn()] = true;

        p.setValueRowAndColumn(position.getRow() + 1, position.getColumn());
        if (getBoard().positionExists(p) && canMove(p)) mat[p.getRow()][p.getColumn()] = true;

        p.setValueRowAndColumn(position.getRow(), position.getColumn() + 1);
        if (getBoard().positionExists(p) && canMove(p)) mat[p.getRow()][p.getColumn()] = true;

        p.setValueRowAndColumn(position.getRow(), position.getColumn() - 1);
        if (getBoard().positionExists(p) && canMove(p)) mat[p.getRow()][p.getColumn()] = true;

        /*

        if (getMoveCount() == 0 && !chessMatch.getCheck()) {
            Position posT1 = new Position(position.getRow(), position.getColumn() + 3);
            if (testRookCastling(posT1)) {
                Position p1 = new Position(position.getRow(), position.getColumn() + 1);
                Position p2 = new Position(position.getRow(), position.getColumn() + 2);
                if (getBoard().piecePosition(p1) == null && getBoard().piecePosition(p2) == null) {
                    mat[position.getRow()][position.getColumn() + 2] = true;
                }
            }

            Position posT2 = new Position(position.getRow(), position.getColumn() - 4);
            if (testRookCastling(posT2)) {
                Position p1 = new Position(position.getRow(), position.getColumn() - 1);
                Position p2 = new Position(position.getRow(), position.getColumn() - 2);
                Position p3 = new Position(position.getRow(), position.getColumn() - 3);
                if (getBoard().piecePosition(p1) == null && getBoard().piecePosition(p2) == null && getBoard().piecePosition(p3) == null) {
                    mat[position.getRow()][position.getColumn() - 2] = true;
                }
            }
        }
*/
        return mat;
    }
}