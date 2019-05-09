package dp.b.unbounded.knapsack.category;
/*

We are given a ribbon of length ‘n’ and a set of possible ribbon lengths.
Now we need to cut the ribbon into the maximum number of pieces that comply with the above-mentioned possible lengths. Write a method that will return the count of pieces.

Example 1:

n: 5
Ribbon Lengths: {2,3,5}
Output: 2
Explanation: Ribbon pieces will be {2,3}.
Example 2:

n: 7
Ribbon Lengths: {2,3}
Output: 3
Explanation: Ribbon pieces will be {2,2,3}.
Example 3:

n: 13
Ribbon Lengths: {3,5,7}
Output: 3
Explanation: Ribbon pieces will be {3,3,7}.
 */
public class MaxRibbonPiece {
    public static void main(String[] args) {

        int[] ribbonLengths = {2,3,5};
        System.out.println(maxRibbonPiece(ribbonLengths, 5, 0));
        ribbonLengths = new int[]{2,3};
        System.out.println(maxRibbonPiece(ribbonLengths, 7,0));
        ribbonLengths = new int[]{3,5,7};
        System.out.println(maxRibbonPiece(ribbonLengths, 13, 0));
        ribbonLengths = new int[]{3,5};
        System.out.println(maxRibbonPiece(ribbonLengths, 7, 0));
    }
    static int maxRibbonPiece(int[] lens, int n, int index){
        if (n == 0) return 0;
        if (lens.length == 0 || index >= lens.length)
            return Integer.MIN_VALUE;
        int c1 = Integer.MIN_VALUE;
        if (n>=lens[index]) {
            int res = maxRibbonPiece(lens, n - lens[index], index);
            if (res != Integer.MIN_VALUE){
                c1 = 1+res;
            }
        }
        int c2 = maxRibbonPiece(lens, n, index+1);
        return Math.max(c1,c2);
    }
}
