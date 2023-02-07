package boardgame;

import exceptions.BoarderException;

import static views.MessageError.*;

public class Board {

    private final int rows;
    private final int columns;
    private final Piece[][] pieces;

    public Board(int rows, int columns) {
        if (rows < 1 && columns < 1) {
            throw new BoarderException(valueTheBoardInvalid());
        }
        this.rows = rows;
        this.columns = columns;
        pieces = new Piece[rows][columns];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Piece piece(int row, int column) {
        if (!positionExists(row, column)) {
            throw new BoarderException(notPositionOnBoard());
        }
        return pieces[row][column];
    }

    public Piece piecePosition(Position position) {
        if (!positionExists(position)) {
            throw new BoarderException(notPositionOnBoard());
        }
        return pieces[position.getRow()][position.getColumn()];
    }

    public void placePiece(Piece piece, Position position) {
        if (thereIsAPiece(position)) {
            throw new BoarderException(existPieceInTheBoard() + position);
        }
        pieces[position.getRow()][position.getColumn()] = piece;
        piece.position = position;
    }

    public Piece removePiece(Position position) {
        if (!positionExists(position)) {
            throw new BoarderException(notPositionOnBoard());
        }
        if (piecePosition(position) == null) {
            return null;
        }
        Piece aux = piecePosition(position);
        aux.position = null;
        pieces[position.getRow()][position.getColumn()] = null;
        return aux;
    }

    //Revision in of method positionExist
    public boolean positionExists(int row, int column) {
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

    public boolean positionExists(Position position) {
        return positionExists(position.getRow(), position.getColumn());
    }

    public boolean thereIsAPiece(Position position) {
        if (!positionExists(position)) {
            throw new BoarderException(notPositionOnBoard());
        }
        return piecePosition(position) != null;
    }
}
