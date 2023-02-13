package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.enums.Color;
import chess.pieces.*;
import exceptions.ChessException;
import views.MessageError;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static views.MessageError.*;

public class ChessMatch {

    private int turn;
    private Color currentPlayer;
    private boolean checkMatch;
    private boolean check;
    private ChessPiece enPassantVulnerable;
    private ChessPiece promoted;
    private final Board board;

    private final List<Piece> piecesOnTheBoard;
    private final List<Piece> capturedPieces;

    public ChessMatch() {
        board = new Board(8, 8);
        turn = 1;
        currentPlayer = Color.WHITE;
        check = false;
        piecesOnTheBoard = new ArrayList<>();
        capturedPieces = new ArrayList<>();
        initialSetup();
    }

    public int getTurn() {
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean getCheck() {
        return check;
    }

    public void nextTurn() {
        turn++;
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
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

    private Color opponent(Color color) {
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private void placeNewPieceWithChessCoordinates(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        piecesOnTheBoard.add(piece);
    }

    private ChessPiece king(Color color) {
        List<Piece> pieceList = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color).toList();
        for (Piece p : pieceList) {
            if (p instanceof King) {
                return (ChessPiece) p;
            }
        }
        throw new IllegalStateException(notKingColor(color));
    }

    private boolean testCheck(Color color) {
        Position kingPosition = king(color).getChessPosition().toPosition();
        List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == opponent(color)).collect(Collectors.toList());
        for (Piece p : opponentPieces) {
            boolean[][] mat = p.possibleMoves();
            if (!mat[kingPosition.getRow()][kingPosition.getColumn()]) {
                return false;
            }
        }
        return true;
    }

    public void initialSetup() {
        setupAllPiecesForMatch();
    }

    public boolean[][] possibleMovies(ChessPosition sourcePosition) {
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return board.piecePosition(position).possibleMoves();
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);

        if (testCheck(currentPlayer)) {
            undoMove(source, target, capturedPiece);
            throw new ChessException(cantNotStayInCheck());
        }

        check = (testCheck(opponent(currentPlayer))) ? true : false;

        nextTurn();
        return (ChessPiece) capturedPiece;
    }

    private Piece makeMove(Position source, Position target) {
        Piece p = board.removePiece(source);
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(p, target);

        if (capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }
        return capturedPiece;
    }

    public void undoMove(Position source, Position target, Piece capturedPiece) {
        Piece p = board.removePiece(target);
        board.placePiece(p, source);

        if (capturedPiece != null) {
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }
    }

    private void validateSourcePosition(Position position) {
        if (!board.thereIsAPiece(position)) {
            throw new ChessException(thereIsNotAPiece());
        }
        if (currentPlayer != ((ChessPiece) board.piecePosition(position)).getColor()) {
            throw new ChessException(notYourPiece());
        }
        if (!board.piecePosition(position).isThereAnyPossibleMovie()) {
            throw new ChessException(isNotPossibleMovie());
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
                if (i == 8 && j == 97 || i == 8 && j == 104)
                    placeNewPieceWithChessCoordinates(j, i, new Rook(board, Color.BLACK));
                if (i == 8 && j == 98 || i == 8 && j == 103)
                    placeNewPieceWithChessCoordinates(j, i, new Knight(board, Color.BLACK));
                if (i == 8 && j == 99 || i == 8 && j == 102)
                    placeNewPieceWithChessCoordinates(j, i, new Bishop(board, Color.BLACK));
                if (i == 8 && j == 100) placeNewPieceWithChessCoordinates(j, i, new King(board, Color.BLACK));
                if (i == 8 && j == 101) placeNewPieceWithChessCoordinates(j, i, new Queen(board, Color.BLACK));
                if (i == 7 && j < 105) {
                }// placeNewPieceWithChessCoordinates(j, i, new Pawn(board, Color.BLACK));

                if (i == 1 && j == 97 || i == 1 && j == 104)
                    placeNewPieceWithChessCoordinates(j, i, new Rook(board, Color.WHITE));
                if (i == 1 && j == 98 || i == 1 && j == 103)
                    placeNewPieceWithChessCoordinates(j, i, new Knight(board, Color.WHITE));
                if (i == 1 && j == 99 || i == 1 && j == 102)
                    placeNewPieceWithChessCoordinates(j, i, new Bishop(board, Color.WHITE));
                if (i == 1 && j == 101) placeNewPieceWithChessCoordinates(j, i, new King(board, Color.WHITE));
                if (i == 1 && j == 100) placeNewPieceWithChessCoordinates(j, i, new Queen(board, Color.WHITE));
                if (i == 2 && j < 105) {
                } //placeNewPieceWithChessCoordinates(j, i, new Pawn(board, Color.WHITE));

            }
        }
    }
}
