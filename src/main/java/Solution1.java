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

public class Solution1 {



    // Complete the findNumber function below.
    static String findNumber(List<Integer> arr, int k) {

       return arr.stream().anyMatch(i-> i == k) ? "YES" : "NO";

    }

    static List<Integer> oddNumbers(int l, int r) {

        if (l > r) return new ArrayList<>(); //empty list
        List<Integer> list = new ArrayList<>();
        for (int i = l ; i <= r; i++) {
            if (i%2 != 0)
                list.add(i);
        }
        return list;
    }


    public static void main(String[] args) throws IOException {



        List<Integer> arr1 = Arrays.asList(1,2,3,4,5);
        System.out.println(findNumber(arr1, 1));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int arrCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<String> arrTemp = new ArrayList<>();

        IntStream.range(0, arrCount).forEach(i -> {
            try {
                arrTemp.add(bufferedReader.readLine().replaceAll("\\s+$", ""));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        List<Integer> arr = arrTemp.stream()
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(toList());

        int k = Integer.parseInt(bufferedReader.readLine().trim());

        String res = findNumber(arr, k);

        bufferedWriter.write(res);
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}