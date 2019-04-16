package dp;

import com.google.common.base.Preconditions;

public class _3EggDropping {
    public static void main(String[] args) {
        _3EggDropping ed = new _3EggDropping();
        int minTries = ed.solve(100, 2);
        System.out.println(minTries ==14? "Test case 1 passed ": "Test case 1 failed. Expected 14 Got " + minTries );

        minTries = ed.solve(6, 2);
        System.out.println(minTries == 3 ? "Test case 2 passed" : "Test case 2 failed. " +
                "Expected 3. Got = " + minTries);


        minTries = ed.solve(14, 3);
        System.out.println(minTries == 4 ? "Test case 4 passed" : "Test case 3 failed. " +
                "Expected 4. Got = " + minTries);
    }

    public int solve(int floors, int eggs){
        int a = minTries_Rec(floors, eggs,new Integer[floors+1][eggs+1]);
        int b = minTries_DP(floors, eggs);
        int c = minTries_Mathmetical(floors, eggs);
        Preconditions.checkArgument(a==c, "Recurion gives " + a + " Mathemical approach gives " + c);
        Preconditions.checkArgument(a==b, "Recursion gives " + a + " DP gives " + b);
        return a;
    }

    // without memoization time complexity is O(2^n). With memo O(floor * eggs)
    private int minTries_Rec(int floors, int eggs, Integer[][] memo) {
        if (eggs == 1) return floors;
        if (floors == 1) return 1;
        if (memo[floors][eggs]!=null) return memo[floors][eggs];
        int minTries = Integer.MAX_VALUE;
        for (int i = 1; i < floors ; i++) {
            int eggBreaks = minTries_Rec(i-1, eggs -1, memo);
            int eggDoesntBreak = minTries_Rec(floors -i, eggs, memo);
            minTries = Math.min(minTries, Math.max(eggBreaks, eggDoesntBreak));
        }
        memo[floors][eggs] = minTries + 1;
        return memo[floors][eggs];
    }

    // Time complexity O(floor^2 * eggs)
    private int minTries_DP(int floors, int eggs) {
        int[][] dp = new int[floors+1][eggs+1];
        // if no. of floors = 1, no. of tries = 1
        for (int i = 0; i < eggs + 1; i++) {
            dp[1][i] = 1;
        }

        // if no. of eggs = 1, no. of tries = no. of floors
        for (int i = 0; i < floors + 1; i++) {
            dp[i][1] = i;
        }

        for (int i = 2; i < floors + 1; i++) {
            for (int j = 2; j < eggs + 1; j++) {
                 dp[i][j] = Integer.MAX_VALUE;
                for (int k = 1; k < i; k++) {
                    int eggBreaks = dp[k-1][j-1];
                    int eggDoesntBreak = dp[i-k][j];
                    dp[i][j] = Math.min(dp[i][j], Math.max(eggBreaks, eggDoesntBreak) + 1);
                }
            }
        }
        return dp[floors][eggs];
    }

    //Time Complexity O(egg Log Floors)
    private int minTries_Mathmetical(int floors, int eggs) {
        // say F(T,e) gives the maximum floor that can be tried with T trials and e eggs
        // With T tries we can have mix of e successful and failed tries
        // If there are 0 failed tries it is C(T,0), 1 failed tries C(T,1)
        // f(T,e) = Summation of C(T,i)  0<=i<=eggs
        //f(T,e) >= N

        int lo=0, hi=floors;
        while(lo < hi){
            int mid = (lo+hi)/2;
            int sum = sumOfBinomialCoeff(mid, eggs, floors);
            if (sum >= floors)
                hi = mid;
            else
                lo = mid + 1;
        }
        return lo;
    }

    private int sumOfBinomialCoeff(int T, int k, int sum) {
        // C(T,1) = T; C(T,0) = 1; C(T,T) = 1
        // C(T,x) = C(T, T-x)
        // C(n,k) = n/1*(n-1)/2*...*(n-k+1)/k
        int term = 1, res = 0;
        for (int i = 1; i <=k; i++) {
            term *=T-i+1;
            term /=i;
            res+=term;
            if (res >= sum) break;
        }
        return res;
    }
    /*

     public int superEggDrop(int K, int N) {
        int lo = 1, hi = N;
        while (lo < hi) {
            int mi = (lo + hi) / 2;
            if (f(mi, K, N) < N)
                lo = mi + 1;
            else
                hi = mi;
        }

        return lo;
    }

    public int f(int x, int K, int N) {
        int ans = 0, r = 1;
        for (int i = 1; i <= K; ++i) {
            r *= x-i+1;
            r /= i;
            ans += r;
            if (ans >= N) break;
        }
        return ans;
    }

     */

}
