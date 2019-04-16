package dp;

import com.google.common.base.Preconditions;

public class _4CoinChange {
    public static void main(String[] args) {
        _4CoinChange cc = new _4CoinChange();
        int a = cc.solve(new int[]{1,2,3}, 4);
        System.out.println(a== 4? " Test1 passed" : "Test1 failed.Expected 4 got " + a);
    }

    private int solve(int[] vals, int sum) {
        int a = coinChange_rec(vals, sum, 0, new Integer[vals.length+1][sum+1]);
        int b = coinChange_dp(vals, sum);
        Preconditions.checkArgument(a==b, "Rec = %d, DP = %d", a, b);
        return a;
    }

    private int coinChange_rec(int[] vals, int sum, int i, Integer[][] memo) {
        if (i == vals.length) return 0;
        if (sum == 0) return 1;
        if (memo[i][sum]!=null) return memo[i][sum];

        int a = coinChange_rec(vals, sum, i+1, memo);
        int b = 0;
        if (sum >= vals[i])
            b = coinChange_rec(vals, sum - vals[i], i, memo );
        memo[i][sum]= a+b;
        return memo[i][sum];
    }

    private int coinChange_dp(int[] vals, int sum) {
        int[][] t = new int[vals.length+1][sum+1];
      /*
            0   1   2   3   4
         0  1   0   0   0   0
         1  1
         2  1
         3  1

       */
        for (int i = 0; i <= vals.length; i++) {
            t[i][0] = 1;
        }

        for (int i = 1; i < vals.length + 1; i++) {
            for (int j = 1; j < sum + 1; j++) {
                t[i][j] = t[i-1][j];
                if(j>=vals[i-1])
                    t[i][j] +=t[i][j-vals[i-1]];
            }
        }
        return t[vals.length][sum];
    }

}
