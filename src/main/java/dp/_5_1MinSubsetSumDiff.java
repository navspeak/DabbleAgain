package dp;

import com.google.common.base.Preconditions;

import java.util.Arrays;

/*
Given a set of positive numbers, partition the set into two subsets with a minimum difference between their subset sums.

Example 1:
Input: {1, 2, 3, 9}
Output: 3
Explanation: We can partition the given set into two subsets where minimum absolute difference
between the sum of numbers is '3'. Following are the two subsets: {1, 2, 3} & {9}.
 */
public class _5_1MinSubsetSumDiff {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 9};
        System.out.println(solve(nums)); // 3
        nums = new int[]{1, 2, 7, 1, 5};
        System.out.println(solve(nums)); // 0
        nums = new int[]{1, 3, 100, 4};
        System.out.println(solve(nums)); // 92
    }

    public static int solve(int[] nums) {
        int sum = Arrays.stream(nums).sum();
        int a = canPartition_Rec(nums,0,0,0);
        int b = minSubSetDiff_dp(nums,sum);
        Preconditions.checkArgument(a==b, "Rec = %d, DP = %d", a, b);
        return a;
    }

    private static int minSubSetDiff_dp(int[] nums, int sum) {
        /*       0 1 2 3...
            0    T
            1 1  T
            2 2
            3 7
            4
            5
         */

        boolean[][] t = new boolean[nums.length+1][sum/2+1];
        for (int i = 0; i < nums.length + 1; i++) {
            t[i][0] = true;
        }

        for (int i = 1; i < nums.length+1; i++) {
            for (int j = 1; j < sum/2+1; j++) {
                t[i][j] = t[i-1][j];
                if (t[i][j] == false) {
                    if (j>=nums[i-1])
                        t[i][j] = t[i-1][j-nums[i-1]];
                }
            }
        }

        int lastRow = nums.length;
        int sum1 = 0;
        for (int i = sum/2; i >= 0 ; i--) {
            if (t[lastRow][i] == true) {
                sum1 = i;
                break;
            }
        }
        int sum2 = sum - sum1;
        return Math.abs(sum1 - sum2);
    }

    //without memo - O(2^n)
    private static int canPartition_Rec(int[] nums, int currentIndex, int sum1, int sum2) {
        if (currentIndex == nums.length)
            return Math.abs(sum1 - sum2);
        int diff1 = canPartition_Rec(nums, currentIndex+1, sum1+nums[currentIndex], sum2);
        int diff2 = canPartition_Rec(nums, currentIndex+1, sum1, sum2+nums[currentIndex]);
        return Math.min(diff1, diff2);
    }



}
