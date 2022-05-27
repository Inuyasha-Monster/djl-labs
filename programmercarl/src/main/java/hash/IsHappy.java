package hash;

import java.util.HashSet;
import java.util.Set;

/**
 * @author djl
 */
public class IsHappy {

    /**
     * 快乐数：
     * 1、注意数学层面无限循环的话，会出现相同的数字
     * 2、计算每个数字的平方和
     *
     * @param n
     * @return
     */
    public static boolean isHappy(int n) {
        if (n == 1) {
            return true;
        }
        Set<Integer> set = new HashSet<>();
        while (true) {
            if (n == 1) {
                return true;
            }
            if (set.contains(n)) {
                return false;
            }
            set.add(n);
            // 计算每位的平方和
            int result = 0;
            while (n > 0) {
                // 求最后一位数
                final int mod = n % 10;
                // 平方和
                final int tmp = mod * mod;
                // 累加
                result += tmp;
                // 去掉最后一位
                n = n / 10;
            }
            n = result;
            System.out.println("n = " + n);
        }
    }

    public static void main(String[] args) {
        final boolean happy = isHappy(116);
        System.out.println("happy = " + happy);
    }
}
