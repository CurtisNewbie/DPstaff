/**
 * https://leetcode-cn.com/problems/min-cost-climbing-stairs/
 */
public class MinCostClimbStairs {

    /**
     * We can climb 1 or 2 steps. Each stair associated with different cost. Thus,
     * for stair k, we pick the min cost among k + 1, and k + 2.
     * <p>
     * Thus, f(n) = MIN(f(n-1), f(n-1))
     */
    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        if (n == 0)
            return 0;
        if (n == 1)
            return cost[0];

        // dp[i] represents the min cost for moving to step i
        int[] dp = new int[n + 1];
        dp[0] = cost[0];
        dp[1] = cost[1];
        for (int i = 2; i <= n; i++) {
            // we can move one/two step, and we pick the one with minimum cost
            dp[i] = min(dp[i - 1], dp[i - 2]) + (i < n ? cost[i] : 0);
        }
        return dp[n];
    }

    private static int min(int a, int b) {
        return a < b ? a : b;
    }
}