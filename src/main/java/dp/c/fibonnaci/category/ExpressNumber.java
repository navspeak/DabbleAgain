package dp.c.fibonnaci.category;
/*
Given a number ‘n’, implement a method to count how many possible ways there are to express ‘n’
as the sum of 1, 3, or 4.

Example 1:

n : 4
Number of ways = 4
Explanation: Following are the four ways we can exoress 'n' : {1,1,1,1}, {1,3}, {3,1}, {4}
Example 2:

n : 5
Number of ways = 6
Explanation: Following are the six ways we can express 'n' : {1,1,1,1,1}, {1,1,3}, {1,3,1}, {3,1,1},
{1,4}, {4}
 */
public class ExpressNumber {

/*
Fibonacci number pattern
We can clearly see that this problem follows the Fibonacci number pattern. However, every number in a Fibonacci series is the sum of the previous two numbers, whereas in this problem every count is a sum of previous three numbers: previous-1, previous-3, and previous-4. Here is the recursive formula for this problem:

    CountWays(n) = CountWays(n-1) + CountWays(n-3) + CountWays(n-4), for n >= 4

 */

    static int countWays(int n) {
        if (n == 0 || n== 1 || n == 2) return 1;
        if (n==3) return 2;
        int dp[] = new int[n+1];
        dp[0] = 1;
        dp[1] = 1;
        dp[2] = 1;
        dp[3] = 2;

        for(int i=4; i<=n; i++)
            dp[i] = dp[i-1] + dp[i-3] + dp[i-4];

        return dp[n];
    }
    public static void main(String[] args) {
        System.out.println(countWays(4));
        System.out.println(countWays(5));
        System.out.println(countWays(6));
    }
}
