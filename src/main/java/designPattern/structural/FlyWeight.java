package designPattern.structural;

import com.google.common.base.Preconditions;

public class FlyWeight {
    public static void main(String[] args) {
        Integer a = Integer.valueOf(10);
        Integer b = Integer.valueOf(10);
        Preconditions.checkArgument(a == b);

        String x = "Navneet";
        String x1 = new String("Navneet");
        String x2 = x1.intern();
        System.out.println("x == x1 (false) " + (x==x1));
        System.out.println("x == x2 (true) " + (x==x1));

    }
}
