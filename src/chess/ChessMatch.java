package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.enums.Color;
import chess.pieces.*;
import exceptions.ChessException;
import views.MessageError;

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
        initialSetup();
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

    private void placeNewPieceWithChessCoordinates(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
    }


    public void initialSetup() {
        setupAllPiecesForMatch();
    }

    public boolean[][] possibleMovies(ChessPosition sourcePosition) {
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return board.piecePosition(position).possibleMovies();
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);
        return (ChessPiece) capturedPiece;
    }

    private Piece makeMove(Position source, Position target) {
        Piece p = board.removePiece(source);
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(p, target);
        return capturedPiece;
    }

    private void validateSourcePosition(Position position) {
        if (!board.thereIsAPiece(position)) {
            throw new ChessException(MessageError.thereIsNotAPiece());
        }
        if (!board.piecePosition(position).isThereAnyPossibleMovie()) {
            throw new ChessException(MessageError.isNotPossibleMovie());
        }
    }

    private void validateTargetPosition(Position source, Position target) {
        if (!board.piecePosition(source).possibleMovie(target)) {
            throw new ChessException(MessageError.notMovePieceChosen());
        }
    }

    public void replacePromotedPiece(String type) {
    }

    public void setupAllPiecesForMatch() {
        for (int i = 1; i <= board.getRows(); i++) {
            for (int c = 0; c < board.getColumns(); c++) {
                char j = (char) (97 + c);
                if (i == 8 && j == 97 || i == 8 && j == 104) {
                    placeNewPieceWithChessCoordinates(j, i, new Rook(board, Color.BLACK));
                }
                if (i == 8 && j == 98 || i == 8 && j == 103) {
                    placeNewPieceWithChessCoordinates(j, i, new Knight(board, Color.BLACK));
                }
                if (i == 8 && j == 99 || i == 8 && j == 102) {
                    placeNewPieceWithChessCoordinates(j, i, new Bishop(board, Color.BLACK));
                }
                if (i == 8 && j == 100) {
                    placeNewPieceWithChessCoordinates(j, i, new King(board, Color.BLACK));
                }
                if (i == 8 && j == 101) {
                    placeNewPieceWithChessCoordinates(j, i, new Queen(board, Color.BLACK));
                }
                if (i == 7 && j < 105) {
                    placeNewPieceWithChessCoordinates(j, i, new Pawn(board, Color.BLACK));
                }
                if (i == 1 && j == 97 || i == 1 && j == 104) {
                    placeNewPieceWithChessCoordinates(j, i, new Rook(board, Color.WHITE));
                }
                if (i == 1 && j == 98 || i == 1 && j == 103) {
                    placeNewPieceWithChessCoordinates(j, i, new Knight(board, Color.WHITE));
                }
                if (i == 1 && j == 99 || i == 1 && j == 102) {
                    placeNewPieceWithChessCoordinates(j, i, new Bishop(board, Color.WHITE));
                }
                if (i == 1 && j == 101) {
                    placeNewPieceWithChessCoordinates(j, i, new King(board, Color.WHITE));
                }
                if (i == 1 && j == 100) {
                    placeNewPieceWithChessCoordinates(j, i, new Queen(board, Color.WHITE));
                }
                if (i == 2 && j < 105) {
                    //placeNewPieceWithChessCoordinates(j, i, new Pawn(board, Color.WHITE));
                }
            }
        }
    }
}
