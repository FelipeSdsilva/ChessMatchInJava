package views;

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
}
