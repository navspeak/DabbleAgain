package dp.b.unbounded.knapsack.category;
/*
Given the weights and profits of ‘N’ items, we are asked to put these items in a knapsack which has a capacity ‘C’.
 The goal is to get the maximum profit from the items in the knapsack.
 The only difference between the 0/1 Knapsack problem and this problem is that we are allowed to use an unlimited quantity of an item.

Let’s take the example of Merry, who wants to carry some fruits in the knapsack to get maximum profit.
Here are the weights and profits of the fruits:

Items: { Apple, Orange, Melon }
Weights: { 1, 2, 3 }
Profits: { 15, 20, 50 }
Knapsack capacity: 5

Let’s try to put different combinations of fruits in the knapsack, such that their total weight is not more than 5.

5 Apples (total weight 5) => 75 profit
1 Apple + 2 Oranges (total weight 5) => 55 profit
2 Apples + 1 Melon (total weight 5) => 80 profit
1 Orange + 1 Melon (total weight 5) => 70 profit

This shows that 2 apples + 1 melon is the best combination, as it gives us the maximum profit and the total weight does not exceed the capacity.


 */
public class UnboundedKnapSack {
    public static void main(String[] args) {
        int[] weights = new int[] {1,2,3};
        int[] profits = new int[] {15,20,50};
        int a = solve(weights, profits, 5);
        System.out.println(a);
    }

    public static int solve(int[] weights, int[] profits, int capacity) {
        if (weights.length != profits.length) throw new RuntimeException("weights.length != profits.length");
        int a = knapsack_dp(weights, profits, capacity);
        return a;
    }

    private static int knapsack_dp(int[] weights, int[] profits, int capacity) {
        int[][] t = new int[weights.length+1][capacity+1];
        // t[i][0] = 0 & t[0][j] = 0
        for (int i = 1; i < weights.length + 1; i++) {
            for (int j = 1; j < capacity + 1; j++) {
                int p1 =0, p2;
                if (j>= weights[i-1]){
                    p1 = profits[i-1]+t[i][j-weights[i-1]]; // note here we have t[i][j-wt[i-1] instead of
                                                            // t[i-1][j-wt[i-1]]
                }
                p2 = t[i-1][j];
                t[i][j] = Math.max(p1,p2);
            }
        }
        printParticipants(weights, profits, capacity, t);
        return t[weights.length][capacity];
    }

    private static void printParticipants(int[] weights, int[] profits, int capacity, int[][] t) {
        int i = weights.length, j = capacity;
        /*           0 1 2 3 4 5
            - -   0  0 0 0 0 0 0
            1 15  1  0 15
            2 20  2  0
            3 50  3  0
         */
        System.out.printf("{");
        while(i>0 && j>0){
            if (t[i][j] != t[i-1][j]){
                System.out.printf("wt = %d, profit = %d, ", weights[i-1], profits[i-1]);
                j = j - weights[i-1];
            } else {
                i--;
            }
        }
        System.out.printf("} gives => %d \n", t[weights.length][capacity]);
    }
}
