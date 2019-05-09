package matrix;

import java.util.*;

public class C_Maze_ShortestDistance {
    /*
There is a ball in a maze with empty spaces and walls.
The ball can go through empty spaces by rolling up, down, left or right, but it won't stop rolling until hitting a wall.
When the ball stops, it could choose the next direction.

Given the ball's start position, the destination and the maze, find the shortest distance for the ball to stop at the
destination. The distance is defined by the number of empty spaces traveled by the ball from the start position
(excluded) to the destination (included). If the ball cannot stop at the destination, return -1.

The maze is represented by a binary 2D array. 1 means the wall and 0 means the empty space.
You may assume that the borders of the maze are all walls. The start and destination coordinates are represented by row and column indexes.

Input 1: a maze represented by a 2D array

0 0 1 0 0
0 0 0 0 0
0 0 0 1 0
1 1 0 1 1
0 0 0 0 0

Input 2: start coordinate (rowStart, colStart) = (0, 4)
Input 3: destination coordinate (rowDest, colDest) = (4, 4)

Output: 12

Explanation: One shortest way is : left -> down -> left -> down -> right -> down -> right.
             The total distance is 1 + 1 + 3 + 1 + 2 + 2 + 2 = 12.

     */

    public static int shortestDistance(int[][] maze, int[] start, int[] dest) {
        int [][] t = new int[maze.length][maze[0].length];
        for (int i = 0; i < maze.length; i++) {
            Arrays.fill(t[i], Integer.MAX_VALUE);
        }
        t[start[0]][start[1]] = 0;
        Queue<int[]> q = new LinkedList<>();
        q.add(start);
        //up, down, left or right
        int[][] dirs = {{0,1},{0,-1},{-1,0},{1,0}};

        while (!q.isEmpty()) {
            int[] s = q.remove();
            for (int[] dir : dirs){
                int newX = s[0] + dir[0];
                int newY = s[1] + dir[1];
                int count = 0;
                while(newX>=0 && newY>=0 && newX< maze.length && newY < maze[0].length && maze[newX][newY] == 0) {
                    //continue in same direction till you can
                    newX +=dir[0];
                    newY +=dir[1];
                    count++;
                }
                if (t[s[0]][s[1]] + count < t[newX-dir[0]][newY - dir[1]]){
                    t[newX-dir[0]][newY - dir[1]] = count+t[s[0]][s[1]];
                    q.add(new int[]{newX - dir[0], newY - dir[1]});
                }
            }
        }

        return t[dest[0]][dest[1]] == Integer.MAX_VALUE ? -1 : t[dest[0]][dest[1]];
    }

    public static void main(String[] args) {
        int[][] maze = {
                {0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0},
                {1, 1, 0, 1, 1},
                {0, 0, 0, 0, 0}
        };

        System.out.println(shortestDistance(maze, new int[]{0,4}, new int[]{4,4}));

    }


}
