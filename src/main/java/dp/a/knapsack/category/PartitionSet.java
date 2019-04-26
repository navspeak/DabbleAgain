package dp.a.knapsack.category;

import java.util.Arrays;

/*
Given a set of positive numbers, find if we can partition it into two subsets such that the sum of elements in both the subsets is equal.

Example 1:
Input: {1, 2, 3, 4}
Output: True
Explanation: The given set can be partitioned into two subsets with equal sum: {1, 4} & {2, 3}
Example 2:
Input: {1, 1, 3, 4, 7}
Output: True
Explanation: The given set can be partitioned into two subsets with equal sum: {1, 3, 4} & {1, 7}
Example 3:
Input: {2, 3, 4, 6}
Output: False
Explanation: The given set cannot be partitioned into two subsets with equal sum.
 */
public class PartitionSet {
    public static void main(String[] args) {
        int[][] set1 = {{1, 2, 3, 4},
                {1, 1, 3, 4, 7},
                {2, 3, 4, 6}
        };
        boolean a = canPartion(set1[0]);
        System.out.println(a);
         a = canPartion(set1[1]);
        System.out.println(a);
         a = canPartion(set1[2]);
        System.out.println(a);
    }

    private static boolean canPartion(int[] nums) {
        int sum = Arrays.stream(nums).sum();
        if (sum%2 != 0) return false;
        boolean a = canPartion_rec(nums, sum/2,0);
//        boolean b = canPartion_dp(nums, sum/2);
//        if (a!=b) throw new RuntimeException("Rec = " + a + " DP = " + b);
        return a;
    }
//O(2^n) time and O(n) space
    private static boolean canPartion_rec(int[] nums, int i, int sum) {
        if (/*i<nums.length*/ sum == 0) return true;
        if (nums.length == 0 || i>=nums.length /*&& sum!= 0*/) return false;

        boolean a, b;
        if (sum>=nums[i]) {
            if (canPartion_rec(nums, i + 1, sum - nums[i]))
                return true;
        }
        return  canPartion_rec(nums, i+1, sum);
    }
}
