package dp;

public class _6LongestPalindromicSubsequence {
    public static void main(String[] args) {
        String[] str = {"adbbca", "forgeeksskeegfor", "agbdb"};
        System.out.println(lps_rec(str[0], 0, str[0].length() - 1));
        System.out.println(lps_dp(str[0]));
        System.out.println(lps_rec(str[1], 0, str[1].length() - 1));
        System.out.println(lps_dp(str[1]));
        System.out.println(lps_rec(str[2], 0, str[2].length() - 1));
        System.out.println(lps_dp(str[2]));
    }

    public static int lps_rec(String str, int i, int j) {
        if (i == j) return 1;
        if (i > j) return 0;
        if (str.charAt(i) == str.charAt(j))
            return 2 + lps_rec(str, i+1, j-1);
        else
            return Math.max(lps_rec(str,i+1, j), lps_rec(str,i,j-1));
    }

    public static int lps_dp(String str){
        int[][] dp = new int[str.length()][str.length()];
        for (int i = 0; i < str.length(); i++) {
            dp[i][i] = 1;
        }

        /*
             a d b b c a
        0  a 1
        1  d 0 1
        2  b 0 0 1
        3  b 0 0 0 1
        4  c 0 0 0 0 1
        5  a 0 0 0 0 0 1

        (0,0)   (0,1)   (0,2)   (0,3)   (0,4)   (0,5)
        -----     +
        (1,0)   (1,1)   (1,2)   (1,3)   (1,4)   (1,5)
                -----     +
        (2,0)   (2,1)   (2,2)   (2,3)   (2,4)   (2,5)
                        -----     +
        (3,0)   (3,1)   (3,2)   (3,3)   (3,4)   (3,5)
                               -------     +
        (4,0)   (4,1)   (4,2)   (4,3)   (4,4)   (4,5)
                                        ------    +
        (5,0)   (5,1)   (5,2)   (5,3)   (5,4)   (5,5)
                                                -----
              a d b b c a
            a 1 1 1 2 2 4
            d 0 1 1 2 2 2
            b 0 0 1 2 2 2
            b 0 0 0 1 1 2
            c 0 0 0 0 1 1
            a 0 0 0 0 0 1

              a g b d b
           a  1 1 1 1 3
           g  0 1 1 1 3
           b  0 0 1 1 3
           d  0 0 0 1 1
           b  0 0 0 0 1


         */

        int start = 0, end = str.length() - 1;
        for (int step = 1; step <= end; step++) {
            for (int i = 0; i <= end-step; i++) {
               // System.out.println(i + ", " + (i+step));
                int j = i+step;
                if(str.charAt(i) == str.charAt(j))
                    dp[i][j] = 2 + dp[i+1][j-1];
                else
                    dp[i][j] = Math.max(dp[i][j-1], dp[i+1][j]);
            }
        }
        printparticpants(str, dp);
        return dp[0][end];
    }

    private static void printparticpants(String str, int[][] dp) {
        int start = 0, i = 0;
        int palindrome_len = dp[0][str.length() - 1] ;
        int end = palindrome_len - 1, j = str.length() - 1;
        /*
                (0,0)   (0,1)   (0,2)   (0,3)   (0,4)   (0,5)
                 -----     +
                (1,0)   (1,1)   (1,2)   (1,3)   (1,4)   (1,5)
                        -----     +
                (2,0)   (2,1)   (2,2)   (2,3)   (2,4)   (2,5)
                                -----     +
                (3,0)   (3,1)   (3,2)   (3,3)   (3,4)   (3,5)
                                      -------     +
                (4,0)   (4,1)   (4,2)   (4,3)   (4,4)   (4,5)
                                                 ------    +
                (5,0)   (5,1)   (5,2)   (5,3)   (5,4)   (5,5)
                                                         -----
              a d b b c a
            a 1 1 1 2 2 4
            d 0 1 1 2 2 2
            b 0 0 1 2 2 2
            b 0 0 0 1 1 2
            c 0 0 0 0 1 1
            a 0 0 0 0 0 1
         */
        char[] palindrome = new char[palindrome_len];
        while(end - start>=0){
            if (str.charAt(i) == str.charAt(j)){
                palindrome[start] = str.charAt(i);
                palindrome[end] = str.charAt(j);
                start++;
                i++;
                end--;
                j--;
            }
            else {
                if (dp[i + 1][j] > dp[i][j - 1])
                    i++;
                else
                    j--;
            }
        }
        System.out.println(new String(palindrome));
    }


}
