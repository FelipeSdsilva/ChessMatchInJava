package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.enums.Color;
import chess.pieces.*;
import exceptions.ChessException;

import java.util.ArrayList;
import java.util.List;

import static views.MessageError.*;

public class ChessMatch {

    private int turn;
    private Color currentPlayer;
    private boolean checkMate;
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

    public boolean isCheckMate() {
        return checkMate;
    }

    public ChessPiece getEnPassantVulnerable() {
        return enPassantVulnerable;
    }

    public ChessPiece getPromoted() {
        return promoted;
    }

    public List<Piece> getPiecesOnTheBoard() {
        return piecesOnTheBoard;
    }

    public List<Piece> getCapturedPieces() {
        return capturedPieces;
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
        List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == opponent(color)).toList();
        for (Piece p : opponentPieces) {
            boolean[][] mat = p.possibleMoves();
            if (mat[kingPosition.getRow()][kingPosition.getColumn()]) return true;
        }
        return false;
    }

    private boolean testCheckMate(Color color) {
        if (!testCheck(color)) return false;

        List<Piece> verificationAllPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color).toList();
        for (Piece p : verificationAllPieces) {
            boolean[][] mat = p.possibleMoves();
            for (int i = 0; i < board.getRows(); i++) {
                for (int j = 0; j < board.getColumns(); j++) {
                    if (mat[i][j]) {
                        Position source = ((ChessPiece) p).getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        Piece capturedPiece = makeMove(source, target);
                        boolean testCheck = testCheck(color);
                        undoMove(source, target, capturedPiece);

                        if (!testCheck) return false;

                    }
                }
            }
        }
        return true;
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

        // ChessPiece movedPiece = (ChessPiece) board.piecePosition(target);

        check = testCheck(opponent(currentPlayer));

        if (testCheckMate(opponent(currentPlayer))) {
            checkMate = true;
        } else {
            nextTurn();
        }
        return (ChessPiece) capturedPiece;
    }

    private Piece makeMove(Position source, Position target) {
        ChessPiece p = (ChessPiece) board.removePiece(source);
        Piece capturedPiece = board.removePiece(target);
        p.increaseMoveCount();
        board.placePiece(p, target);

        if (capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }
        if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
            Position sourceDestRook = new Position(source.getRow(), source.getColumn() + 4);
            Position targetDestRook = new Position(target.getRow(), target.getColumn() - 1);
            ChessPiece rook = (ChessPiece) board.removePiece(sourceDestRook);
            board.placePiece(rook, targetDestRook);
        }

        if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
            Position sourceDestRook = new Position(source.getRow(), source.getColumn() - 3);
            Position targetDestRook = new Position(target.getRow(), target.getColumn() + 1);
            ChessPiece rook = (ChessPiece) board.removePiece(sourceDestRook);
            board.placePiece(rook, targetDestRook);
        }

        return capturedPiece;
    }

    public void undoMove(Position source, Position target, Piece capturedPiece) {
        ChessPiece p = (ChessPiece) board.removePiece(target);
        p.decreaseMoveCount();
        board.placePiece(p, source);

        if (capturedPiece != null) {
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }

        if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
            Position sourceDestRook = new Position(source.getRow(), source.getColumn() + 4);
            Position targetDestRook = new Position(target.getRow(), target.getColumn() + 1);
            ChessPiece rook = (ChessPiece) board.removePiece(targetDestRook);
            board.placePiece(rook, sourceDestRook);
            rook.decreaseMoveCount();
        }

        if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
            Position sourceDestRook = new Position(source.getRow(), source.getColumn() - 3);
            Position targetDestRook = new Position(target.getRow(), target.getColumn() - 1);
            ChessPiece rook = (ChessPiece) board.removePiece(targetDestRook);
            board.placePiece(rook, sourceDestRook);
            rook.decreaseMoveCount();
        }
    }

    public void nextTurn() {
        turn++;
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private void validateSourcePosition(Position position) {
        if (!board.thereIsAPiece(position)) throw new ChessException(thereIsNotAPiece());
        if (currentPlayer != ((ChessPiece) board.piecePosition(position)).getColor())
            throw new ChessException(notYourPiece());
        if (!board.piecePosition(position).isThereAnyPossibleMovie()) throw new ChessException(isNotPossibleMovie());
    }

    private void validateTargetPosition(Position source, Position target) {
        if (!board.piecePosition(source).possibleMove(target)) throw new ChessException(notMovePieceChosen());
    }

    private void placeNewPieceWithChessCoordinates(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        piecesOnTheBoard.add(piece);
    }

    /*
        public void replacePromotedPiece(String type) {
        }
    */
    public void initialSetup() {
        setupAllPiecesForMatch();
    }

    public void setupAllPiecesForMatch() {
        for (int i = 1; i <= board.getRows(); i++) {
            for (int c = 0; c < board.getColumns(); c++) {
                char j = (char) (97 + c);
                if (i == 8 && j == 97 || i == 8 && j == 104) placeNewPieceWithChessCoordinates(j, i, new Rook(board, Color.BLACK));
                if (i == 8 && j == 98 || i == 8 && j == 103) placeNewPieceWithChessCoordinates(j, i, new Knight(board, Color.BLACK));
                if (i == 8 && j == 99 || i == 8 && j == 102) placeNewPieceWithChessCoordinates(j, i, new Bishop(board, Color.BLACK));
                if (i == 8 && j == 100) placeNewPieceWithChessCoordinates(j, i, new King(board, Color.BLACK, this));
                if (i == 8 && j == 101) placeNewPieceWithChessCoordinates(j, i, new Queen(board, Color.BLACK));
                if (i == 7 && j < 105) placeNewPieceWithChessCoordinates(j, i, new Pawn(board, Color.BLACK));

                if (i == 1 && j == 97 || i == 1 && j == 104) placeNewPieceWithChessCoordinates(j, i, new Rook(board, Color.WHITE));
                if (i == 1 && j == 98 || i == 1 && j == 103) placeNewPieceWithChessCoordinates(j, i, new Knight(board, Color.WHITE));
                if (i == 1 && j == 99 || i == 1 && j == 102) placeNewPieceWithChessCoordinates(j, i, new Bishop(board, Color.WHITE));
                if (i == 1 && j == 101) placeNewPieceWithChessCoordinates(j, i, new Queen(board, Color.WHITE));
                if (i == 1 && j == 100) placeNewPieceWithChessCoordinates(j, i, new King(board, Color.WHITE, this));
                if (i == 2 && j < 105) placeNewPieceWithChessCoordinates(j, i, new Pawn(board, Color.WHITE));
            }
        }
    }
}
