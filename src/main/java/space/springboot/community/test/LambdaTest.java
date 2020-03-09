package space.springboot.community.test;

public class LambdaTest {

    public static void main(String[] args) {
        new Thread(
                () -> {
                    System.out.println("hello");
                    System.out.println("lambda");
                }
        ).start();

        Runnable runnable = () -> System.out.println("hello runnable with lambda");
        runnable.run();

    }


}
