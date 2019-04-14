package dp;

import static com.google.common.base.Preconditions.*;

public class _1RodCutting {

    public static void main(String[] args) {
        int[] prices1 = new int[]{1, 5, 8, 9, 10, 17, 17, 20};
        // 1m cut costs Rs 1,
        // 2m cut costs Rs 5,
        // ...
        // 8m cut costs Rs 20
        Integer memo[] = new Integer[prices1.length];
        int profit = cut_Recursively(prices1, prices1.length, memo);
        System.out.println(profit == 22 ? "Passed" : "Failed. Expected 22. Got " + profit);

        profit = cut_DP(prices1, prices1.length);
        System.out.println(profit == 22 ? "Passed" : "Failed. Expected 22. Got " + profit);


        /* Variation of above where length isn't same as size of array */
        /* Meaning we might have rod of length 6 and we can cut it into say
           1m (2$), 2m(5$), 3m(7$), 4m(3$)
         */
        int[] prices2 = new int[]{2, 5, 7, 3};
        int len = 5;

        profit = cut_DP(prices2, 5);
        System.out.println(profit == 12 ? "Passed" : "Failed. Expected 12. Got " + profit);

    }

    public static int cut_DP(int[] prices, int length) {
        int[][]dp = new int[prices.length + 1][length + 1];

        /*
                0 1 2 3 4 5 6
            0 0 0 0 0 0 0 0 0
            1 2 0 2 4 6 8 10 12
            2 5 0 2 5 7 10 12 15
            3 7 0 2 5 7 10 12 17
            4 3 0 2 5 7 10 12
         */
        for (int i = 1; i <= prices.length ; i++) {
            for (int j = 1; j <= length; j++) {
                int p1 = j>=i? prices[i-1] + dp[i][j-i] : 0;
                int p2 = dp[i-1][j];
                dp[i][j] = Math.max(p1, p2);
            }
        }
        printParticipants(prices, length, dp);
        return dp[prices.length][length];
    }

    private static void printParticipants(int[] prices, int length, int[][] dp) {
        int row = prices.length;
        int col = length;
        int profit = dp[row][col];
        while(row>0 && col>0){
            if (dp[row][col] != dp[row -1][col]){
                System.out.print("{Length = " + row + " , Price = " + prices[row-1] + "}, ");
                col = col - row;
            } else
                row--;
        }
        System.out.print(" Total Profit => " + dp[prices.length][length] + "\n");
    }

    // Time complexity = O(n^2) with memo - otherwise O(2^n) - see NOTE
    // Space complexity = O(n) for stack and O(n) for memo
    // NOTE:
    // How many ways are there to cut up a rod of length n?
    // Answer: 2 ^ (n-1)
    // because there are n − 1 places where we can choose to make cuts, and at
    // each place, we either make a cut or we do not make a cut.
    // https://web.stanford.edu/class/archive/cs/cs161/cs161.1168/lecture12.pdf
    //https://www.youtube.com/watch?v=ElFrskby_7M
    private static int cut_Recursively(int[] prices, int length, Integer[] memo) {
        if (length == 0) return 0;
        if (memo[length - 1]!= null) return memo[length - 1];
        int profit = -1;
        for (int i = 1; i <= length; i++) {
            int p1 = prices[i-1] + cut_Recursively(prices, length-i, memo);
            profit = Math.max(profit, p1);
        }
        memo[length - 1] = profit;
        return profit;
    }
}

