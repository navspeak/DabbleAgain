import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Solution {



    // Complete the totalCandies function below.
    static long totalCandies(List<String> initial, List<String> reorder) {

        List<Integer> previousInReorderArray = new ArrayList<>();
        Map<String, Integer> mapWithOrigNumber = new HashMap<>();
        for (int i = 0; i < initial.size(); i++) {
            String key = initial.get(i);
            mapWithOrigNumber.put(key, i);
        }
        int count = 0;
        for (int i = 0; i < reorder.size(); i++) {
            int currentNum = mapWithOrigNumber.get(reorder.get(i));

            for (Integer prev : previousInReorderArray) {
                if (prev> currentNum)
                    count++;
            }
            previousInReorderArray.add(currentNum);

        }
        return count;
    }

    public static void main(String[] args) throws IOException {
        long x = totalCandies(Arrays.asList("A", "B", "C", "D", "E"),
                Arrays.asList("D", "B", "A", "E", "C"));
        System.out.println(x);

        /*
        5
lherhbzbn
gsj
mqvfiapmr
tiumpu
iffyanbyc
5
tiumpu
gsj
lherhbzbn
iffyanbyc
mqvfiapmr
         */
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
//
//        int initialCount = Integer.parseInt(bufferedReader.readLine().trim());
//
//        List<String> initial = new ArrayList<>();
//
//        IntStream.range(0, initialCount).forEach(i -> {
//            try {
//                initial.add(bufferedReader.readLine());
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }
//        });
//
//        int reorderCount = Integer.parseInt(bufferedReader.readLine().trim());
//
//        List<String> reorder = new ArrayList<>();
//
//        IntStream.range(0, reorderCount).forEach(i -> {
//            try {
//                reorder.add(bufferedReader.readLine());
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }
//        });
//
//        long res = totalCandies(initial, reorder);
//
//        bufferedWriter.write(String.valueOf(res));
//        bufferedWriter.newLine();
//
//        bufferedReader.close();
//        bufferedWriter.close();
//    }
    }
}