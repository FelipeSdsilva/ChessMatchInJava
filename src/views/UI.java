package views;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.enums.Color;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static views.MessageError.chessValuesValid;

public class UI {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static ChessPosition readChessPosition(Scanner sc) {
        try {
            String s = sc.nextLine();
            char column = s.charAt(0);
            int row = Integer.parseInt(s.substring(1));
            return new ChessPosition(column, row);
        } catch (RuntimeException e) {
            throw new InputMismatchException(chessValuesValid());
        }
    }

    public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
        printBoard(chessMatch.getPieces());
        printCapturedPieces(captured);
        System.out.println("\n" +
                "Turn: " + chessMatch.getTurn()
                + "\n");
        if (!chessMatch.isCheckMate()) {
            System.out.println("Waiting player: " +
                    ((chessMatch.getCurrentPlayer() == Color.WHITE) ? ANSI_CYAN : ANSI_RED) +
                    chessMatch.getCurrentPlayer() + ANSI_RESET);
            if (chessMatch.getCheck()) System.out.println("CHECK!");
        } else {
            System.out.println("CHECKMATE!" + "\n" +
                    "Winner: " + ((chessMatch.getCurrentPlayer() == Color.WHITE) ? ANSI_CYAN : ANSI_RED) +
                    chessMatch.getCurrentPlayer() + ANSI_RESET);
        }

    }

    public static void printBoard(ChessPiece[][] pieces) {
        System.out.println(ANSI_BLACK_BACKGROUND + "  a b c d e f g h   " + ANSI_RESET);
        for (int i = 0; i < pieces.length; i++) {
            System.out.print((i == 8) ? ANSI_WHITE_BACKGROUND : ANSI_BLACK_BACKGROUND + (8 - i) + " ");
            for (int j = 0; j < pieces.length; j++) {
                printBackgroundBoard(i, j);
                printPiece(pieces[i][j], false);
            }
            System.out.print((i == 8) ? ANSI_WHITE_BACKGROUND : ANSI_BLACK_BACKGROUND + (8 - i) + " ");
            System.out.println(ANSI_RESET);
        }
        System.out.println(ANSI_BLACK_BACKGROUND + "  a b c d e f g h   " + ANSI_RESET);
    }

    public static void printBoardBackgroundColorWithPossibleMovesOfPiece(ChessPiece[][] pieces, boolean[][] possibleMoves) {
        System.out.println(ANSI_BLACK_BACKGROUND + "  a b c d e f g h   " + ANSI_RESET);
        for (int i = 0; i < pieces.length; i++) {
            System.out.print((i == 8) ? ANSI_WHITE_BACKGROUND : ANSI_BLACK_BACKGROUND + (8 - i) + " ");
            for (int j = 0; j < pieces.length; j++) {
                printBackgroundBoard(i, j);
                printPiece(pieces[i][j], possibleMoves[i][j]);
            }
            System.out.print((i == 8) ? ANSI_WHITE_BACKGROUND : ANSI_BLACK_BACKGROUND + (8 - i) + " ");
            System.out.println(ANSI_RESET);
        }
        System.out.println(ANSI_BLACK_BACKGROUND + "  a b c d e f g h   " + ANSI_RESET);
    }

    private static void printBackgroundBoard(int i, int j) {
        if (i % 2 == 0 && j % 2 == 0) System.out.print(ANSI_WHITE_BACKGROUND);
        if (i % 2 == 1 || j % 2 == 1) System.out.print(ANSI_BLACK_BACKGROUND);
        if (j % 2 == 1 && i % 2 == 1) System.out.print(ANSI_WHITE_BACKGROUND);
    }

    public static void printPiece(ChessPiece piece, boolean background) {
        if (background) System.out.print(ANSI_BLUE_BACKGROUND);
        if (piece == null) System.out.print("  ");
        else {
            if (piece.getColor() == Color.WHITE) System.out.print(ANSI_CYAN + piece + ANSI_RESET);
            else System.out.print(ANSI_RED + piece + ANSI_RESET);
        }
        System.out.print("");
    }

    private static void printCapturedPieces(List<ChessPiece> captured) {
        List<ChessPiece> captWithe = captured.stream().filter(x -> x.getColor() == Color.WHITE).toList();
        List<ChessPiece> captBlack = captured.stream().filter(x -> x.getColor() == Color.BLACK).toList();
        System.out.println("""
                                
                Captured Pieces:
                                
                White: """ + ANSI_CYAN + Arrays.toString(captWithe.toArray()) + ANSI_RESET +
                "\nBlack:" + ANSI_RED + Arrays.toString(captBlack.toArray()) + ANSI_RESET);
    }
}
//public static final String ANSI_GREEN = "\
//public static final String ANSI_BLACK = "\u001B[30m";u001B[32m";
//public static final String ANSI_YELLOW = "\u001B[33m";
//public static final String ANSI_BLUE = "\u001B[34m";
//public static final String ANSI_PURPLE = "\u001B[35m";
//public static final String ANSI_WHITE = "\u001B[37m";
//public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
//public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
//public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
//public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
//public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";