package matrix;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.util.*;

public class A_CountAllPossiblePaths {
    /*Count all possible paths from top left to bottom right of a mXn matrix
Input :  m = 2, n = 2;
Output : 2
There are two paths
(0, 0) -> (0, 1) -> (1, 1)
(0, 0) -> (1, 0) -> (1, 1)

Input :  m = 2, n = 3;
Output : 3
There are three paths
(0, 0) -> (0, 1) -> (0, 2) -> (1, 2)
(0, 0) -> (0, 1) -> (1, 1) -> (1, 2)
(0, 0) -> (1, 0) -> (1, 1) -> (1, 2)
     */

    //O(ROWS*COLS) space and time
    public static int countPaths(final int ROWS, final int COLS){
        int[][] t = new int[ROWS][COLS];
        for (int i = 0; i < ROWS ; i++) {
            t[i][0] = 1;
        }

        for (int i = 0; i < COLS ; i++) {
            t[0][i] = 1;
        }
        for (int i = 0; i < COLS ; i++) {
            t[0][i] = 1;
        }

        for (int i = 1; i < ROWS; i++) {
            for (int j = 1; j < COLS; j++){
                t[i][j] = t[i-1][j] + t[i][j-1];
            }
        }
        return t[ROWS-1][COLS-1];
    }

    //O(ROWS*COLS) space and time
    public static int countPaths_spaceOptimized(final int ROWS, final int COLS){
        int[] t = new int[COLS];
        t[0]=1;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 1; j < COLS; j++){
                t[j] = t[j-1] + t[j];
            }
        }

        return t[COLS-1];
    }

    public static void printAllPaths(final int ROWS, final int COLS){
        String paths = "";
        printAllPaths(ROWS, COLS,0, 0, paths);
    }

    private static void printAllPaths(final int ROWS, final int COLS, int x, int y, String paths) {
        if (x == ROWS - 1) {
            for (int i = y; i < COLS; i++) {
                paths = paths + "-> (" + x + ", " + i + ") ";
            }
            System.out.println(paths);
           return;
        }
        if (y == COLS - 1) {
            for (int i = x; i < ROWS; i++) {
                paths = paths + "-> (" + i + ", " + y + ") ";
            }
            System.out.println(paths);
            return;
        }
        paths = paths + "-> (" + x + ", " + y + ") ";
        printAllPaths(ROWS, COLS, x, y+1, paths);
        printAllPaths(ROWS, COLS, x+1, y,paths);
    }

    public static void main(String[] args) {
        printAllPaths(2,2);
        System.out.println(countPaths(2,2));
        System.out.println("========");
        printAllPaths(2,3);
        System.out.println(countPaths(2,3));
        System.out.println("========");

        System.out.println(countPaths_spaceOptimized(2,2));
        System.out.println(countPaths_spaceOptimized(2,3));
        System.out.println(countPaths_spaceOptimized(8,3));
    }
}
