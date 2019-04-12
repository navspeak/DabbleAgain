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
    }

    // Time complexity = O(n^2) with memo - otherwise O(2^n) - see NOTE
    // Space complexity = O(n) for stack and O(n) for memo
    // NOTE:
    // How many ways are there to cut up a rod of length n?
    // Answer: 2 ^ (n-1)
    // because there are n − 1 places where we can choose to make cuts, and at
    // each place, we either make a cut or we do not make a cut.
    // https://web.stanford.edu/class/archive/cs/cs161/cs161.1168/lecture12.pdf
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

