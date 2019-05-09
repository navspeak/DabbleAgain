package matrix;

import java.util.Arrays;

public class D_NQueenProblem {

    public static void main(String[] args) {
        int N = 3;
        int[][] board = new int[N][N];
       if(solve(N, board)) {
           for (int i = 0; i < N; i++) {
               System.out.println(Arrays.toString(board[i]));
           }
       } else {
           System.out.println("No solution!");
       }
    }

    // O(N!)
    private static boolean solve(final int N, final int[][] board) {
        return placeQueen(0,  N, board);
    }

    private static boolean placeQueen(int col, final int N, final int[][] board) {
        if (col == N) return true;
        for (int i = 0; i < N ; i++) {
            board[i][col] = 1;
            if (isQueenSafeHere(i,col, board)){
                if (placeQueen(col+1, N, board) == true)
                    return  true;
            }
            board[i][col] = 0;
        }
        return false;
    }

    private static boolean isQueenSafeHere(int row, int col, final int[][] board) {
        // is row safe:
        int sum = 0;
        for (int i = 0; i < board.length; i++) {
            sum += board[row][i];
            if (sum > 1) return false;
        }
        sum = 0;
        // is diagonal from top left to bottom right safe
        int rowStart = 0, colStart = 0;
        if (row > col) rowStart = row - col;
        if (row < col) colStart = col - row;
        while(rowStart < board.length && colStart < board.length) {
            sum += board[rowStart++][colStart++];
            if (sum > 1) return false;
        }
        sum = 0;

        // is diagonal from top right to bottom left safe
        int N = board.length;
         rowStart = 0; colStart = N - 1;
        if (row + col > N) rowStart = (row + col ) % (N -1);
        if (row + col < N) colStart = Math.min((row+col), N-1);
        while(rowStart < board.length && colStart > 0) {
            sum += board[rowStart++][colStart--];
            if (sum > 1) return false;
        }
        return true;
    }
}
