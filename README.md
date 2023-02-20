# Chess Match In Java
Project is a Chess Match in terminal Java. WIth objective usually learn more for structure my projects.  And  uses the clean code in this application Java.

# UML Modell

![image](https://user-images.githubusercontent.com/47900701/219397622-af95c7c1-9663-4045-b0d7-1cf2ec6ebe7d.png)

create by https://github.com/acenelio

# Layout in console

![image](https://user-images.githubusercontent.com/47900701/220191804-da384a3b-2e4a-423e-9c06-660228d6e4b0.png)


# Method setupAllPieces

Create in middle of development, inciative for use clear code.

```
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
```

