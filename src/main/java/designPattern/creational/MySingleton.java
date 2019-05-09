package designPattern.creational;

public class MySingleton {
    private MySingleton(){};
    public  static MySingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        static final MySingleton INSTANCE =  new MySingleton();
    }
}
class SingletonDemo {
    public static void main(String[] args) {
        MySingleton obj = MySingleton.getInstance();
        MySingleton obj2 = MySingleton.getInstance();
        MySingleton obj3 = MySingleton.getInstance();
        if (obj == obj2) {
            if (obj2 == obj3) {
                System.out.println("Singleton Holder is working!!");
            }
        } else
            System.out.println("Somethings wrong!");

    }
}
