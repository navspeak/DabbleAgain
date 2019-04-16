package dp;

import com.google.common.base.Preconditions;

import java.util.Arrays;

public class _5EqualPartition {

    public static void main(String[] args) {
        _5EqualPartition ep = new _5EqualPartition();
        boolean a = ep.isPartionable(new int[] {1,2,3,4});
        System.out.println(a==true? "Test1 passed": "Test1 failed. Expected true. Got false");

        boolean b = ep.subsetsum_rec(new int[]{3, 34, 4, 12, 5, 2}, 14, 0);
        System.out.println(b == true? "Test2 passed" : "Test2 failed");

        boolean c = ep.subsetsum_dp(new int[]{3, 34, 4, 12, 5, 2}, 14);
        System.out.println(c == true? "Test3 passed" : "Test3 failed");

        boolean d = ep.subsetsum_dp(new int[]{3, 34, 4, 12, 5, 2}, 35);
        System.out.println(d == false? "Test4 passed" : "Test4 failed");
    }

    private boolean isPartionable(int[] vals) {
        int sum = Arrays.stream(vals).sum();
        if (sum % 2 != 0) return false;
        boolean a = subsetsum_rec(vals, sum/2, 0);
        boolean b = subsetsum_dp(vals, sum/2);
        Preconditions.checkArgument(a==b);
        return a;
    }

    public boolean subsetsum_rec(int[] vals, int sum, int i) {
        if (sum == 0) return true;
        if (i == vals.length) return false;
        boolean a, b;
        if (subsetsum_rec(vals, sum, i+1))
            return true;
        if (sum >= vals[i])
            return subsetsum_rec(vals, sum - vals[i], i+1);
        return false;
    }

    private boolean subsetsum_dp(int[] vals, int sum) {
        boolean[][] dp = new boolean[vals.length+1][sum+1];
        for (int i = 0; i < vals.length+1; i++) {
            dp[i][0] = true;
        }
        /*    0 1 2 3 4
           0  T
           1  T
           2  T
           3  T
         */
        for (int i = 1; i < vals.length + 1; i++) {
            for (int j = 1; j < sum + 1; j++) {
                dp[i][j] = dp[i-1][j];
                if (dp[i][j] == false){
                    if (j>=vals[i-1])
                        dp[i][j] = dp[i-1][j-vals[i-1]];
                }
            }
        }
        return dp[vals.length][sum];
    }
}
