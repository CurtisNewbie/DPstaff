
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


        int prev = 1; // init: climb(1)
        int curr = 2; // init: climb(2)
        int m = curr;
        for (int steps = 3; steps <= n; steps++) {
            // for climb(steps), there are m ways
            // where prev is actually climb(steps-1)
            // curr is actually climb(steps-2)
            m = prev + curr;
            prev = curr;
            curr = m;
        }
        return m;
    }
}
