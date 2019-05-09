package matrix;

import java.lang.reflect.Array;
import java.util.Arrays;

public class D_RatInTheMaze {
    public static void main(String[] args) {
        // 0 = can go | 1 = can't go
        int [][] maze = {
                { 1, 0, 0, 0},
                { 1, 1, 0, 0},
                { 1, 1, 1, 0},
                { 1, 0, 1, 1}
        };

        int[][] solution = new int[maze.length][maze[0].length];
        if (findPath(maze, new int[]{0,0}, new int[]{3,3}, solution)){
            for (int i = 0; i < maze.length; i++) {
                System.out.println(Arrays.toString(solution[i]));
            }
        }

    }

    // brack-tracking + dfs
    private static boolean findPath(int[][] maze, int[] start, int[] end, int[][] solution) {
        int x = start[0];
        int y = start[1];
        if (x == end[0] && y == end[1]) {
            solution[x][y] = 1;
            return true;
        }
        if (x < maze.length && x >= 0 && y >=0 && y < maze[0].length && maze[x][y] == 1){
            solution[x][y] = 1;
            if (findPath(maze, new int[]{x+1, y}, end, solution))
                return true;
            if (findPath(maze, new int[]{x, y+1}, end, solution))
                return true;
            solution[x][y] = 0;
        }
        return false;
    }


}
