package dp;

public class _7LongestCommonSubsequence {
    public static void main(String[] args) {
        String[] str = {"AGGTAB","GXTXAYB"};
        int a = lcs(str[0], str[1],
                str[0].length() - 1,
                str[1].length() - 1);
        int b = lcs(str[0], str[1]);
        System.out.println(a);
        System.out.println(b);
    }

    //O(2^n)
    public static int lcs(String str1, String str2,
                          int end1,
                          int end2) {
        if (end1 < 0 || end2 < 0)
            return  0;
        if (str1.charAt(end1) == str2.charAt(end2)) {
            return 1 + lcs(str1, str2, end1-1, end2-1);
        }
        else
            return Math.max(
                    lcs(str1, str2,end1-1, end2 ),
                    lcs(str1, str2,  end1,end2-1));
    }

    public static int lcs(String str1, String str2){
            /*
                        0 A X Y T
                      0 0 0 0 0 0
                      A 0
                      Y 0
                      Z 0
                      X 0
                      T[i][j] = T[i-1][j] or T[i][j-1]
             */
        int[][] t = new int[str1.length()+1][str2.length()+1];
        for (int i = 1; i < str1.length() + 1; i++) {
            for (int j = 1; j < str2.length() + 1; j++) {
                if (str1.charAt(i-1) == str2.charAt(j-1)) {
                    t[i][j] = 1 + t[i-1][j-1];
                } else {
                    t[i][j] = Math.max(t[i-1][j], t[i][j-1]);
                }
            }
        }
        return t[str1.length()][str2.length()];
    }
}
