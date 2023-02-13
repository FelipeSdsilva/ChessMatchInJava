package views;

import chess.enums.Color;

public class MessageError {
    public static String thereIsNotAPiece() {
        return "There is no piece in source position";
    }

    public static String isNotPossibleMovie() {
        return "There is no possibles move for the chosen piece";
    }

    public static String notMovePieceChosen() {
        return "The chosen piece can't move to target position";
    }

    public static String chessValuesValid() {
        return "Error instantiating ChessPosition. Valid values are from a1 to h8.";
    }

    public static String notPositionOnBoard() {
        return "Position not on the board";
    }

    public static String existPieceInTheBoard() {
        return "There is already a piece on position ";
    }

    public static String valueTheBoardInvalid() {
        return "Error creating board: There must ber at least 1 row and 1 column";
    }

    public static String notYourPiece() {
        return "The chosen piece is not yours";
    }

    public static String notKingColor(Color color) {
        return "There is no " + color + " king on the board";
    }

    public static String cantNotStayInCheck() {
        return "You can't put yourself in check";
    }
}
