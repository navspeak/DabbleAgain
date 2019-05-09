package matrix;


import java.util.LinkedList;
import java.util.Queue;

public class C_Maze_hasPath {

    public static void main(String[] args) {

        int[][] maze = {
                {0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0},
                {1, 1, 0, 1, 1},
                {0, 0, 0, 0, 0}};
        System.out.println(hasPath_bfs(maze, new int[]{0,4}, new int[]{3,2}));
//0 means can go
        maze = new int[][]{
                {0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0},
                {1, 1, 0, 1, 1},
                {0, 0, 0, 0, 0}
        };

        System.out.println(hasPath_bfs(maze, new int[]{0,4}, new int[]{4,4}));
    }

    //Lee's algorithm
    public static boolean hasPath_bfs(int[][] maze, int[] start, int[] destination) {
        boolean[][] visited = new boolean[maze.length][maze[0].length];
        Queue<int[]> q = new LinkedList<>();
        q.add(start);
        visited[start[0]][start[1]] = true;
        int[][] dirs = {{0,1},{0,-1},{-1,0},{1,0}};
        while (!q.isEmpty()) {
            int[] curr = q.remove();
            if (curr[0] == destination[0] && curr[1] == destination[1])
                return true;
            for (int[] dir : dirs) {
                int x = curr[0] + dir[0];
                int y = curr[1] + dir[1];
                while (x >= 0 && y >= 0 && x < maze.length && y < maze[0].length && maze[x][y] == 0) {
                    x += dir[0];
                    y += dir[1];
                }
                if (!visited[x - dir[0]][y - dir[1]]) {
                    visited[x - dir[0]][y - dir[1]] = true;
                    q.add(new int[]{x - dir[0], y - dir[1]});
                }
            }
        }
        return false;
    }


    public static boolean hasPath(int[][] maze, int[] start, int[] destination) {
        boolean[][] visited = new boolean[maze.length][maze[0].length];
        return dfs(maze, start, destination, visited);
    }
    public static boolean dfs(int[][] maze, int[] start, int[] destination, boolean[][] visited) {
        if (visited[start[0]][start[1]])
            return false;
        if (start[0] == destination[0] && start[1] == destination[1])
            return true;
        visited[start[0]][start[1]] = true;
        //up, down, left or right
        int[][] dirs = {{0,1},{0,-1},{-1,0},{1,0}};
        for(int[] dir: dirs){
            int x = start[0] + dir[0];
            int y = start[1] + dir[1];
            while (x >= 0 && y >= 0 && x < maze.length && y < maze[0].length && maze[x][y] == 0) {
                x += dir[0];
                y += dir[1];
            }
            if (dfs(maze, new int[]{x - dir[0],y - dir[1]}, destination, visited))
                return true;

        }

        return false;
    }

}
