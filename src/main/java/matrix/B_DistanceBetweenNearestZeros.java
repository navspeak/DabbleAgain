package matrix;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class B_DistanceBetweenNearestZeros {
    /*
Given a matrix consists of 0 and 1, find the distance of the nearest 0 for each cell.

The distance between two adjacent cells is 1.

Example 1:

Input:
[[0,0,0],
 [0,1,0],
 [0,0,0]]

Output:
[[0,0,0],
 [0,1,0],
 [0,0,0]]
Example 2:

Input:
[[0,0,0],
 [0,1,0],
 [1,1,1]]

Output:
[[0,0,0],
 [0,1,0],
 [1,2,1]]

     */

    public static void main(String[] args) {
        int[][] matrix = {
                {0, 0, 0},
                {0, 1, 0},
                {0, 0, 0}
        };
        B_DistanceBetweenNearestZeros obj = new B_DistanceBetweenNearestZeros();
        int[][] output = obj.updateMatrix(matrix);
        System.out.println(Arrays.deepToString(output));

        int[][] matrix1 = {
                {0, 0, 0},
                {0, 1, 0},
                {1, 1, 1}
        };

        output = obj.updateMatrix(matrix1);
        System.out.println(Arrays.deepToString(output));

        int[][] matrix2 = {
                {0,1,0,1,1},
                {1,1,0,0,1},
                {0,0,0,1,0},
                {1,0,1,1,1},
                {1,0,0,0,1}
        };
        output = obj.updateMatrix(matrix2);
        System.out.println(Arrays.deepToString(output));
    }

    public int[][] updateMatrix_dp(int[][] matrix) {
        final int row = matrix.length;
        final int col = matrix[0].length;
        int[][] t =  new int[row][col];
        for (int i = 0; i < row ; i++) {
            Arrays.fill(t[i], Integer.MAX_VALUE - 10000);
        }

        // left and up
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col ; j++) {
                if (matrix[i][j] == 0) {
                    t[i][j] = 0;
                } else {
                    if (i > 0) {
                        t[i][j] = Math.min(t[i-1][j] + 1, t[i][j]); // top
                    }
                    if (j > 0) {
                        t[i][j] = Math.min(t[i][j-1] + 1, t[i][j]); // left
                    }
                }
            }

        }

        //right and down
        for (int i = row-1; i >=0; i--) {
            for (int j = col-1; j >= 0 ; j--) {
                    if (i<row -1)
                        t[i][j] = Math.min(t[i+1][j] + 1, t[i][j]); // down
                    if (j<col -1)
                        t[i][j] = Math.min(t[i][j+1] + 1, t[i][j]); // down

            }
        }

        return t;
    }

    public int[][] updateMatrix(int[][] matrix) {
        final int row = matrix.length;
        final int col = matrix[0].length;
        int[][] dist =  new int[row][col];
        for (int i = 0; i < row ; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE - 10000);
        }
        Queue<int[]> q = new ArrayDeque<>();
        for (int i = 0; i < row ; i++) {
            for (int j = 0; j < col; j++) {
                if (matrix[i][j] == 0) {
                    q.add(new int[]{i, j});
                    dist[i][j] = 0;
                }
            }
        }
        int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};


        while (!q.isEmpty()){
            int[] curr = q.remove();
            // examine all neighbors in four direction
            for (int[] dir: dirs) {
                int x = curr[0] + dir[0];
                int y = curr[1] + dir[1];
                if (x < row && y < col && x >=0 && y >= 0){
                    // if dist[x][y] > dist[currX][currY] => not visited.
                   if (dist[x][y] > dist[curr[0]][curr[1]] + 1) {
                       dist[x][y] = 1 + dist[curr[0]][curr[1]];
                       q.add(new int[]{x, y});
                   }
                }
            }
        }


        return dist;
    }

}
