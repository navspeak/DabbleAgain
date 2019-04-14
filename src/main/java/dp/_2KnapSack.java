package dp;

import com.google.common.base.Preconditions;

public class _2KnapSack {
    public static void main(String[] args) {
        _2KnapSack ks = new _2KnapSack();
        int maxProfit = ks.solve(new int[] {1,2,18,4}, new int[] {9,1,3,5}, 6);
        System.out.println(maxProfit == 20? "Test1 passed" : "Test1 Failed. Expected 7, got " + maxProfit);

        maxProfit = ks.solve(new int[] {1,6,10,16}, new int[] {1,2,3,5}, 7);
        System.out.println(maxProfit == 22? "Test2 passed" : "Test1 Failed. Expected 22, got " + maxProfit);

        maxProfit = ks.solve(new int[] {1,6,10,16}, new int[] {1,2,3,5}, 6);
        System.out.println(maxProfit == 17? "Test3 passed" : "Test1 Failed. Expected 17, got " + maxProfit);

    }

    public int solve(int[] vals, int[] wts, int capacity) {
        Preconditions.checkArgument(vals.length == wts.length);
        int a = knapsack_rec(vals, wts, capacity, 0, new Integer[vals.length+1][capacity+1]);
        int b = knapsack_dp(vals, wts, capacity);
        int c = knapsack_dp_spaceOptimized(vals, wts, capacity);
        /* check if all approaches give same result */
        Preconditions.checkArgument(a==b);
        Preconditions.checkArgument(b==c);
        return a;
    }

    /*
        without memoization the time complexity is O(2^n) which is reduced to O(n*capacity)
     */
    private int knapsack_rec(int[] vals, int[] wts, int capacity, int index, Integer[][] memo) {
        if (index == vals.length) return 0;
        int consideringCurrentIndex = 0;
        int notConsideringCurrentIndex = 0;
        if (memo[index][capacity] != null) return memo[index][capacity];
        if (capacity >= wts[index])
            consideringCurrentIndex = vals[index] + knapsack_rec(vals, wts, capacity - wts[index],
                    index+1, memo);
        notConsideringCurrentIndex = knapsack_rec(vals, wts, capacity, index+1, memo);
        int maxProfit = Math.max(consideringCurrentIndex, notConsideringCurrentIndex);
        memo[index][capacity] = maxProfit;
        return maxProfit;
    }

    // DP solution with space and time complexity O(n*capacity)
    private int knapsack_dp(int[] vals, int[] wts, int capacity){
        int[][] dp = new int[vals.length+1][capacity+1];
        //dp[0][i] = 0 & dp[i][0]=0
        for (int i = 1; i < vals.length+1; i++) {
            for (int j = 1; j < capacity + 1; j++) {
                int p1 = dp[i-1][j]; // exclude this elem
                // Include this element if its weight is not more than capacity
                int p2 = j>= wts[i-1] ? vals[i-1] + dp[i-1][j-wts[i-1]] : 0;
                dp[i][j] = Math.max(p1, p2);
            }
        }
        printParticipants(vals, wts, dp);
        return dp[vals.length][capacity];
    }

    private void printParticipants(int[] vals, int[] wts, int[][] dp) {
        int row = vals.length;
        int col = dp[0].length - 1;
        int profit = dp[row][col];
        while (row > 0 && col > 0){
            if (dp[row][col] != dp[row -1][col]){
                System.out.printf("{val = %d, wt = %d }, ", vals[row - 1], wts[row -1]);
                col = col - wts[row -1];
            }
            row--;
        }
        System.out.printf(" => Profit %d \n", profit);
    }

    // DP solution with space optimized to O(capacity)
    private int knapsack_dp_spaceOptimized(int[] vals, int[] wts, int capacity){
        int[][] dp = new int[2][capacity+1];
        //dp[0][i] = 0 & dp[i][0]=0

        int prev =0, curr = 1;
        for (int i = 1; i < vals.length+1; i++) {
            for (int j = 1; j < capacity + 1; j++) {
                int p1 = dp[prev][j]; // exclude this elem
                // Include this element if its weight is not more than capacity
                int p2 = j>= wts[i-1] ? vals[i-1] + dp[prev][j-wts[i-1]] : 0;
                dp[curr][j] = Math.max(p1, p2);
            }
            prev = prev == 0? 1: 0;
            curr = curr == 0? 1: 0;
        }
        return dp[prev][capacity];
    }
}
