/**
 * https://leetcode-cn.com/problems/three-steps-problem-lcci/
 */
public class ThreeStepsProblemLCCI {

    /**
     * Similar to two steps problem. We have relation:
     * <p>
     * f(n) = f(n-1) + f(n-2) + f(n-3)
     */
    public int waysToStep(int n) {
        if (n <= 2) {
            return n;
        }

        int a = 1; // f(1)
        int b = 2; // f(2)
        int c = 4; // f(3)
        int steps = c;
        for (int i = 4; i <= n; i++) {
            steps = ((a + b) % 1000000007 + c) % 1000000007;
            a = b;
            b = c;
            c = steps;
        }
        return steps;
    }
}