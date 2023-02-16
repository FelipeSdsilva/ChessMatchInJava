package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.enums.Color;

public class Knight extends ChessPiece {
    public Knight(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "N ";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);

        p.setValueRowAndColumn(position.getRow() - 2, position.getColumn() - 1);
        if (getBoard().positionExists(p) && canMove(p)) mat[p.getRow()][p.getColumn()] = true;

        p.setValueRowAndColumn(position.getRow() - 2, position.getColumn() + 1);
        if (getBoard().positionExists(p) && canMove(p)) mat[p.getRow()][p.getColumn()] = true;

        p.setValueRowAndColumn(position.getRow() - 1, position.getColumn() - 2);
        if (getBoard().positionExists(p) && canMove(p)) mat[p.getRow()][p.getColumn()] = true;

        p.setValueRowAndColumn(position.getRow() - 1, position.getColumn() + 2);
        if (getBoard().positionExists(p) && canMove(p)) mat[p.getRow()][p.getColumn()] = true;

        p.setValueRowAndColumn(position.getRow() + 1, position.getColumn() - 2);
        if (getBoard().positionExists(p) && canMove(p)) mat[p.getRow()][p.getColumn()] = true;

        p.setValueRowAndColumn(position.getRow() + 1, position.getColumn() + 2);
        if (getBoard().positionExists(p) && canMove(p)) mat[p.getRow()][p.getColumn()] = true;

        p.setValueRowAndColumn(position.getRow() + 2, position.getColumn() - 1);
        if (getBoard().positionExists(p) && canMove(p)) mat[p.getRow()][p.getColumn()] = true;

        p.setValueRowAndColumn(position.getRow() + 2, position.getColumn() + 1);
        if (getBoard().positionExists(p) && canMove(p)) mat[p.getRow()][p.getColumn()] = true;

        return mat;
    }
}
