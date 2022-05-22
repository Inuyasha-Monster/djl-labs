package array;

/**
 * @author djl
 */
public class MyTest {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println("i = " + i);
        }
        System.out.println();

        int i = 1;
        for (; i < 5; i++) {
            System.out.println("i = " + i);
        }

        int num = 1;
        int num2 = ++num;
        System.out.println("num2 = " + num2);
    }
}
