import java.util.Arrays;

/**
 * <p>
 * Instead of bursting the balloons and find the maximum coins, we reverse the
 * process, and look for the last balloons being bursted such that we gain the
 * maximum coins. It's similar to inserting new balloons to maximise the coins.
 * <p>
 * I.e., when we look for the balloons [i,j,k] that has the maximum coins
 * gained, we can imagine it as bursting all the other balloons, such that
 * [i,j,k] are the last three remaining. In other words, in any [i,j,k], that we
 * are looking for, we are always removing j, but j is in fact the last one
 * being removed such that we maximise the coins gained.
 * <p>
 * However, i and k are in fact irrelavant, what matters is that in which order
 * the j is removed. I.e., what are the j(s). While we iterating the array, and
 * compares the result of each j, we remember that j is the last coin being
 * removed, so, there must be some balloons bursted before j (e.g., 0...j) and
 * after j (e.g., j...n).
 * <p>
 * For (0...j) and (j...n), these are sub-problems that do not interfere with
 * each other. Then we can have dp array to memorise these results. For example,
 * we can have dp[x][y] to memorise the result of f(x...y). So, the equation
 * will be:
 * 
 * <pre>
 * f(i, k) = f(i, j) + nums[i] * nums[j] * nums[k] + f(j, k);
 * // or
 * f(i, k) = dp[i][j] + nums[i] * nums[j] * nums[k] + dp[j][k];
 * </pre>
 * <p>
 * Quite importantly, f(i, k) represents the results between (i,k), and j must
 * not be i or k, such that the sub-problems are independent of each other.
 * <p>
 * https://leetcode-cn.com/problems/burst-balloons/
 */
public class BurstBalloons {

    public int maxCoins(int[] nums) {
        int n = nums.length;
        // handles out-of-boundary base cases,
        // e.g., (-1, 0, 1) and (n-2, n-1, n) in nums
        nums = pad(nums);
        // dp[i][k] represents the maximum coins gained between (i,k)
        int[][] dp = new int[n + 2][n + 2];
        for (int i = n - 1; i >= 0; i--) {
            for (int k = i + 2; k <= n + 1; k++) {
                for (int j = i + 1; j < k; j++) {
                    int sum = nums[i] * nums[j] * nums[k];
                    // sum is the max coins for [i,j,k]
                    // we also take into account the max coins in
                    // (i, j) and (j, k)
                    // because j must be removed to gain the coin (of [i,j,k])
                    // (i, j) and (j, k) are essentially the max coins gained
                    // before removing j, we can imagine them as the snapshot before removing j
                    sum += dp[i][j] + dp[j][k];
                    dp[i][k] = Math.max(dp[i][k], sum);
                    System.out.printf("dp[%d][%d] = %d", i, k, dp[i][k]);
                }
            }
        }
        for (int[] row : dp)
            System.out.println(Arrays.toString(row));
        return dp[0][n + 1];
    }

    /**
     * Append 1 to nums[0] and nums[nums.length]
     */
    private int[] pad(int[] nums) {
        int n = nums.length;
        int[] arr = new int[n + 2];
        for (int i = 1; i <= n; i++) {
            arr[i] = nums[i - 1];
        }
        arr[0] = arr[arr.length - 1] = 1;
        return arr;
    }
}