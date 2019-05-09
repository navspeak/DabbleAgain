package dp.b.unbounded.knapsack.category;
/*
Introduction
Given an infinite supply of ‘n’ coin denominations and a total money amount,
we are asked to find the minimum number of coins needed to make up that amount.

Example 1:

Denominations: {1,2,3}
Total amount: 5
Output: 2
Explanation: We need minimum of two coins {2,3} to make a total of '5'
Example 2:

Denominations: {1,2,3}
Total amount: 11
Output: 4
Explanation: We need minimum four coins {2,3,3,3} to make a total of '11'
 */

/*

 */
public class MinNumOfCoins {
    public static void main(String[] args) {
        System.out.println(minNumOfCoins(new int[]{1,2,3}, 0, 5));
        System.out.println(minNumOfCoins(new int[]{1,2,3}, 0, 11));
        System.out.println(minNumOfCoins(new int[]{1,2,3}, 0, 7));
        System.out.println(minNumOfCoins(new int[]{3,5}, 0, 7));
        System.out.println("=====");
        System.out.println(minNumOfCoins(new int[]{1,2,3},  5));
        System.out.println(minNumOfCoins(new int[]{1,2,3}, 11));
        System.out.println(minNumOfCoins(new int[]{1,2,3}, 7));
        System.out.println(minNumOfCoins(new int[]{3,5}, 7));

    }
    static int minNumOfCoins(int[] denominations, int index, int total){
        if (total == 0) return 0;
        if (denominations.length == 0 || index>= denominations.length)
            return Integer.MAX_VALUE;

        int n1 = Integer.MAX_VALUE, n2;
        if (total >= denominations[index]) {
            int res = minNumOfCoins(denominations, index, total - denominations[index]);
            if (res != Integer.MAX_VALUE)
                n1 = 1+res;
        }
        n2 = minNumOfCoins(denominations,index+1, total);
        return Math.min(n1, n2);
    }

    static int minNumOfCoins(int[] denominations, int total){
        Integer[][] t = new Integer[denominations.length+1][total+1];
        /*
              0  1  2   3   4   5
           0  0  0  0   0   0   0
           1  0
           2  0
           3  0
         */
        for (int i = 0; i < denominations.length+1; i++) {
            t[i][0] = 0;
        }
        for (int i = 1; i < denominations.length+1; i++) {
            for (int j = 1; j < total+1; j++) {
                Integer n1 = null, n2=null;
                if (j>=denominations[i-1] && t[i][j-denominations[i-1]]!=null)
                    n1 = 1+ t[i][j-denominations[i-1]];
                n2 = t[i-1][j];
                if (n1!=null && n2!=null){
                   t[i][j] = Math.min(n1,n2);
                } else if (n1 == null) {
                    t[i][j] = n2;
                } else
                    t[i][j] = n1;
            }
        }
        final Integer ret = t[denominations.length][total];
        return ret == null? -1: ret ;
    }
}
