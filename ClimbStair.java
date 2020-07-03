
/**
 * https://blog.csdn.net/zhuanzhe117/article/details/72846939
 * <p>
 * Each time, we can only climb 1 or 2 steps
 */
public class ClimbStair {

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            System.out.printf("For %d steps stair, there are %d way to climb it.\n", i, climb(i));
        }
    }

    // climb(n) = climb(n-1) + climb(n-2)
    public static int climb(int n) {
        if (n <= 1) { // when there is only 1 or none step, there can only be n ways to climb it
            return n;
        }

        int m = 1;
        int oneStep = 1; // init: climb(1)
        int twoStep = 1; // init: climb(2)
        for (int steps = 2; steps <= n; steps++) {
            // for climb(steps), there are m ways
            // where oneStep is actually climb(steps-1)
            // twoStep is actually climb(steps-2)
            m = oneStep + twoStep;
            oneStep = twoStep;
            twoStep = m;
        }
        return m;
    }
}