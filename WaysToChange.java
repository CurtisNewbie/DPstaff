/**
 * <p>
 * Let k be the coins {1, 5, 10, 25}
 * <p>
 * For k1 = 1, because there is only one coin, there will only be one way to
 * describe n value:
 * <p>
 * > n = 1, f(1) = 1
 * <p>
 * > n = 2, f(2) = 1
 * <p>
 * > ....
 * <p>
 * > f(n) = 1
 * <p>
 * For k2 = 5, when n < k2, we cannot use k2, thus:
 * <p>
 * > n = 1, f(1) = 1
 * <p>
 * > n = 2, f(2) = 1
 * <p>
 * > n = 5, f(5) = 2
 * <p>
 * We now can use k2 to describes n value, and whenever, n increments k2, there
 * is an extra combination. I.e., we can use an extra k2 to describe the value:
 * <p>
 * > n = 10, f(10) = 3
 * <p>
 * > n = 15, f(15) = 4
 * <p>
 * Then we can converts these phenomena to:
 * 
 * <pre>
 * for k <= i <= n, i++ AND k > 1, f(i) = f(i) + f(i - k)
 * </pre>
 * 
 * Before we update i, we must have already updated i-k, and when we use k coin,
 * we don't need to worry about n < k, since we don't even get use k coin.
 * <p>
 * Expand it a little bit, when we use coin 1 and coin 5. the possible
 * combination for n using these two coins are actually:
 * 
 * <pre>
 * coins(n) = coinOne(n) + coinFive(n)
 * </pre>
 * 
 * Thus we have:
 * 
 * <pre>
 * for k IN coins 
 *    for i, WHERE k <= i <= n 
 *      f(i) = f(i) + f(i - k)
 * </pre>
 * <p>
 * https://leetcode-cn.com/problems/coin-lcci/
 */
public class WaysToChange {

    public int waysToChange(int n) {
        int[] coins = { 25, 10, 5, 1 };
        int[] dp = new int[n + 1];
        dp[0] = 1;
        for (int k = 0; k < coins.length; k++) {
            for (int i = coins[k]; i <= n; i++) {
                dp[i] = (dp[i] + dp[i - coins[k]]) % 1000000007;
            }
        }
        return dp[n];
    }
}