package chess;

import boardgame.Position;
import exceptions.ChessException;

import static views.MessageError.chessValuesValid;

public record ChessPosition(char column, int row) {
    public ChessPosition {
        if (column < 'a' || column > 'h' || row < 1 || row > 8) throw new ChessException(chessValuesValid());
    }

    protected Position toPosition() {
        return new Position(8 - row, column - 'a');
    }

    protected static ChessPosition fromPosition(Position position) {
        return new ChessPosition((char) ('a' + position.getColumn()), 8 - position.getRow());
    }

    @Override
    public String toString() { return "" + column + row; }
}
