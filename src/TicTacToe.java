import java.util.Scanner;

public class TicTacToe {
    private static final int ROWS = 3;
    private static final int COLS = 3;
    private static String[][] board = new String[ROWS][COLS];
    private static String currentPlayer = "X";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean playAgain;

        do {
            clearBoard();
            currentPlayer = "X";
            boolean gameWon = false;
            int moveCount = 0;

            while (!gameWon && moveCount < ROWS * COLS) {
                displayBoard();

                // Get row and column move from the player using SafeInput
                int row = SafeInput.getRangedInt(scanner, "Enter row (1-3): ", 1, 3) - 1;
                int col = SafeInput.getRangedInt(scanner, "Enter column (1-3): ", 1, 3) - 1;

                if (isValidMove(row, col)) {
                    board[row][col] = currentPlayer;
                    moveCount++;
                    gameWon = isWin(currentPlayer);

                    if (gameWon) {
                        displayBoard();
                        System.out.println("Player " + currentPlayer + " wins!");
                    } else if (isTie()) {
                        displayBoard();
                        System.out.println("It's a tie!");
                        break;
                    }

                    // Toggle the current player
                    currentPlayer = currentPlayer.equals("X") ? "O" : "X";
                } else {
                    System.out.println("Invalid move. Cell is already occupied. Try again.");
                }
            }

            // Prompt to play again
            playAgain = SafeInput.getYNConfirm(scanner, "Would you like to play again? (Y/N): ");
        } while (playAgain);

        scanner.close();
    }

    // Clears the board at the start of each game
    private static void clearBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = " ";
            }
        }
    }

    // Displays the current state of the board
    private static void displayBoard() {
        System.out.println("Current Board:");
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                System.out.print(board[i][j]);
                if (j < COLS - 1) System.out.print("|");
            }
            System.out.println();
            if (i < ROWS - 1) System.out.println("-----");
        }
    }

    // Checks if the cell at row, col is empty for a valid move
    private static boolean isValidMove(int row, int col) {
        return board[row][col].equals(" ");
    }

    // Checks if the current player has a winning condition
    private static boolean isWin(String player) {
        return isRowWin(player) || isColWin(player) || isDiagonalWin(player);
    }

    private static boolean isRowWin(String player) {
        for (int i = 0; i < ROWS; i++) {
            if (board[i][0].equals(player) && board[i][1].equals(player) && board[i][2].equals(player)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isColWin(String player) {
        for (int i = 0; i < COLS; i++) {
            if (board[0][i].equals(player) && board[1][i].equals(player) && board[2][i].equals(player)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isDiagonalWin(String player) {
        return (board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player)) ||
                (board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player));
    }

    // Checks if the board is full without a winner, resulting in a tie
    private static boolean isTie() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (board[i][j].equals(" ")) {
                    return false;
                }
            }
        }
        return true;
    }
}

class SafeInput {
    // Method to get an integer within a specified range
    public static int getRangedInt(Scanner scanner, String prompt, int min, int max) {
        int input;
        do {
            System.out.print(prompt);
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid input. " + prompt);
                scanner.next();
            }
            input = scanner.nextInt();
        } while (input < min || input > max);
        return input;
    }

    // Method to confirm yes or no input from the user
    public static boolean getYNConfirm(Scanner scanner, String prompt) {
        String input;
        System.out.print(prompt);
        while (true) {
            input = scanner.next().trim().toUpperCase();
            if (input.equals("Y")) {
                return true;
            } else if (input.equals("N")) {
                return false;
            } else {
                System.out.print("Invalid input. " + prompt);
            }
        }
    }
}
