package dp.c.fibonnaci.category;

import java.util.Stack;

class ArrayJump {

    public int countMinJumps(int[] jumps) {
        int dp[] = new int[jumps.length];
        return this.countMinJumpsRecursive(dp, jumps, 0);
    }

    private int countMinJumpsRecursive(int[] dp, int[] jumps, int currentIndex) {
        if (currentIndex == jumps.length -1) return 0;
        if (jumps[currentIndex] == 0) return Integer.MAX_VALUE;
        if (dp[currentIndex] != 0) return dp[currentIndex];
        int start = currentIndex+1;
        int end = currentIndex + jumps[currentIndex];
        int totalJumps = Integer.MAX_VALUE;
        while(start < jumps.length && start <= end) {
            // jump one step and recurse for the remaining array
            int minJumps = countMinJumpsRecursive(dp, jumps, start++);
            if(minJumps != Integer.MAX_VALUE)
                totalJumps = Math.min(totalJumps, minJumps + 1);
        }
        dp[currentIndex] = totalJumps;
        return dp[currentIndex];

    }

    private int countMinJumpsDP(int[] array) {
        int[] jumps = new int[array.length];
        int[] results = new int[array.length];
        for (int i = 1; i < array.length; i++) {
            jumps[i] = Integer.MAX_VALUE - 1;
        }

        for (int i = 1; i <  array.length; i++) {
            for (int j = 0; j < i ; j++) {
                if (j + array[j] >= i){
                    if (jumps[i] > 1+ jumps[j]) {
                        jumps[i] = 1 + jumps[j];
                        results[i] = j;
                        if (jumps[i] == 1) break;
                    }
                }
            }
        }
        int i = array.length -1;
        Stack<Integer> stack = new Stack<>();
        stack.add(i);
        while(results[i] > 0) {
            stack.add(results[i]);
            i = results[i];
        }
        stack.add(0);
        while(!stack.isEmpty()){
            System.out.printf("%d -> ", stack.pop());
        }
        System.out.println();
        return jumps[array.length - 1];
    }

    public static void main(String[] args) {
        ArrayJump aj = new ArrayJump();
        int[] jumps = {2, 1, 1, 1, 4};
        System.out.println(aj.countMinJumpsDP(jumps));
        jumps = new int[]{1, 1, 3, 6, 9, 3, 0, 1, 3};
        System.out.println(aj.countMinJumpsDP(jumps));

    }
}