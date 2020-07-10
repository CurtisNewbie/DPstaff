import java.util.*;

/**
 * We can solve this problem as in StockProblem.java, though we need to modify a
 * bit on how we handle decision making.
 * <p>
 * Each decision involves three possible actions:
 * <p>
 * 1. buy a stock,
 * <p>
 * 2. sell a stock and
 * <p>
 * 3. do nothing.
 * <p>
 * For each decision we make, we also enter three possible states:
 * <p>
 * S1. Have a stock
 * <p>
 * S2. Don't have a stock and not in cooldown
 * <p>
 * S3. In cooldown.
 * <p>
 * Based on above observation, it's essentially a state machine.
 * <p>
 * For S1, we can either do nothing (remains S1), or sell a stock (becomes S3).
 * <p>
 * For S2, We can either do nothing (remains S2), or buy a stock (becomes S1).
 * <p>
 * For S3, We have to wait for the cooldown, and we will automatically enters S2
 * in the next step.
 * <p>
 * Then all we need to do is to track these states, and make the best decision
 * for each state: E.g., If we are in S2, we make the optimal decision among
 * buying a stock or do nothing, such that our profit is maximised.
 * <p>
 * So, for S0, we have:
 * 
 * <pre>
 * S0(i) = Max(S0(i - 1), S1(i - 1) - prices[i]);
 * </pre>
 * 
 * For S1, we have:
 * 
 * <pre>
 * S1(i) = Max(S1(i - 1), S2(i - 1));
 * </pre>
 * 
 * For S2, we have:
 * 
 * <pre>
 * S2(i) = S1(i - 1) + prices[i];
 * </pre>
 * <p>
 * https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/
 */
public class StockProblemWithCooldown {

    public int dpMaxProfit(int[] prices) {
        int n = prices.length;
        if (n < 2)
            return 0;

        // states
        // dp[i][0] => we have stock
        // dp[i][1] => we don't have stock and we are not in cooldown
        // dp[i][2] => we are in cooldown
        int[][] dp = new int[n][3];
        dp[0][0] = -prices[0];
        dp[0][1] = 0;
        dp[0][2] = 0;
        for (int i = 1; i < n; i++) {
            // We have stock, because we did nothing (we had one already), or we just bought
            // one
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] - prices[i]);
            // We don't have stock (but we are not in cooldown), because we were in cooldown
            // or we did nothing
            dp[i][1] = Math.max(dp[i - 1][2], dp[i - 1][1]);
            // we are in cooldown because we just sold our stock
            dp[i][2] = dp[i - 1][0] + prices[i];
        }
        for (int[] r : dp)
            System.out.println(Arrays.toString(r));
        return Math.max(dp[n - 1][1], dp[n - 1][2]);
    }

    // O(1) space complexity
    public int maxProfit(int[] prices) {

        int n = prices.length;
        if (n < 2)
            return 0;
        int s0 = -prices[0];
        int s1 = 0;
        int s2 = 0;
        for (int i = 1; i < n; i++) {
            int c0 = Math.max(s0, s1 - prices[i]);
            int c1 = Math.max(s2, s1);
            int c2 = s0 + prices[i];
            s0 = c0;
            s1 = c1;
            s2 = c2;
        }
        return Math.max(s1, s2);
    }
}
