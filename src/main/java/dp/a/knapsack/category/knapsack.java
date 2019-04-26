package dp.a.knapsack.category;
/*
Given the weights and profits of ‘N’ items, we are asked to put these items in a knapsack which has a capacity ‘C’.
The goal is to get the maximum profit from the items in the knapsack. Each item can only be selected once,
as we don’t have multiple quantities of any item.

Let’s take the example of Merry, who wants to carry some fruits in the knapsack to get maximum profit.
Here are the weights and profits of the fruits:

Items: { Apple, Orange, Banana, Melon }
Weights: { 2, 3, 1, 4 }
Profits: { 4, 5, 3, 7 }
Knapsack capacity: 5
 */

public class knapsack {
    public static void main(String[] args) {
        int[] weights = { 2, 3, 1, 4 };
        int[] profits = { 4, 5, 3, 7 };
        int a = solve(weights, profits, 5);
        System.out.println(a);
    }

    private static int solve(int[] weights, int[] profits, int capacity) {
        if (weights.length != profits.length)
            throw new RuntimeException("Weights and profits array mismatch");
        Integer[][] memo = new Integer[weights.length+1][capacity+1];
        int a = knapsack_rec(weights, profits, capacity, 0, memo);
        int b = knapsack_dp(weights, profits, capacity);
        int c = knapsack_dp_spaceOptimized(weights, profits, capacity);
        if (a!=b) throw new RuntimeException("Recursion and dp gave different results " + a + " ," + b);
        if (a!=c) throw new RuntimeException("Recursion and dp optimized gave different results " + a + " ," + c);
        return a;
    }

    //Without memo O(2^n) time complexity | O(n) - space => resursion proceeds in a depth first fashion
    //With memo O(n*capacity) time complexity & memo array & O(n) for recursion
    private static int knapsack_rec(int[] weights, int[] profits, int capacity, int currentIndex, Integer[][] memo) {

        if (currentIndex >= weights.length) return 0; // no elements left

        if (memo[currentIndex][capacity]!= null) return memo[currentIndex][capacity];
        int p1 =0; // considering current element
        int p2; // excluding current element
        if (weights[currentIndex] <= capacity)
            p1 = profits[currentIndex] +
                    knapsack_rec(weights, profits, capacity - weights[currentIndex], currentIndex+1, memo);
        p2 = knapsack_rec(weights, profits, capacity,currentIndex+1, memo);
        memo[currentIndex][capacity] = Math.max(p1,p2);
        return memo[currentIndex][capacity];
    }

    private static int knapsack_dp(int[] weights, int[] profits, int capacity) {
        int [][] t = new int[weights.length+1][capacity+1];
        for (int i = 1; i < weights.length+1 ; i++) {
            for (int j = 1; j < capacity+1; j++) {
                int p1 =0; // considering current element
                int p2; // excluding current element
                if (j>=weights[i-1])
                    p1 = profits[i-1]+t[i-1][j-weights[i-1]];
                p2 = t[i-1][j];
                t[i][j] = Math.max(p1,p2);
            }
        }
        printParticipants(weights, profits, capacity, t);
        return t[weights.length][capacity];
    }

    private static void printParticipants(int[] weights, int[] profits, int capacity, int[][] t) {
        int i = weights.length, j = capacity;
        System.out.printf("{");
        while(i>0 && j>0){
            if (t[i][j] != t[i-1][j]){
                System.out.printf("wt = %d, profit = %d, ", weights[i-1], profits[i-1]);
                j = j - weights[i-1];
            } else {
                i--;
            }
        }
        System.out.printf("} gives => %d \n", t[weights.length][capacity]);
    }

    // DP solution with space optimized to O(capacity)
    private static int knapsack_dp_spaceOptimized(int[] weights, int[] profits, int capacity) {
        // we only need two rows
        /*
                       0    1   2   3   4   5
              - - 0    0    0   0   0   0   0
              2 4 1    0
              3 5 2    0
              1 3 3    0
              4 7 4    0
         */
        int[][] t = new int[2][capacity + 1];
        int prev = 0, curr = 1;
        for (int i = 1; i < weights.length + 1; i++) {
            for (int j = 1; j < capacity+1; j++) {
                int p1 = 0, p2;
                if (j>=weights[i-1]){
                    p1 = profits[i-1]+t[prev][j-weights[i-1]];
                }
                p2 = t[prev][j];
                t[curr][j] = Math.max(p1, p2);
            }
            curr = curr == 0? 1: 0;
            prev = prev == 1? 0: 1;
        }
        return t[prev][capacity];
    }
}
