package dp;

public class _2KnapSack {
    public static void main(String[] args) {
        _2KnapSack ks = new _2KnapSack();
        int maxProfit = ks.solve(new int[] {1,2,18,4}, new int[] {9,1,3,5}, 6);
        System.out.println(maxProfit == 20? "Test1 passed" : "Test1 Failed. Expected 7, got " + maxProfit);
    }

    private int solve(int[] vals, int[] wts, int capacity) {
        return knapsack_rec(vals, wts, capacity, 0);
    }

    private int knapsack_rec(int[] vals, int[] wts, int capacity, int index) {
        if (index == vals.length) return 0;
        int consideringCurrentIndex = 0;
        int notConsideringCurrentIndex = 0;
        if (capacity >= wts[index])
            consideringCurrentIndex = vals[index] + knapsack_rec(vals, wts, capacity - wts[index], index+1);
        notConsideringCurrentIndex = knapsack_rec(vals, wts, capacity, index+1);
        int maxProfit = Math.max(consideringCurrentIndex, notConsideringCurrentIndex);
        return maxProfit;
    }
}
