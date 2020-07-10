/**
 * <p>
 * Say we have 0*1, 2*2, 3*3, 1*4. Four numbers.
 * <p>
 * If we delete and earn the number 4, say f(4), we lose f(3) and f(2). If we
 * delete f(3), we lose f(2) and f(1).
 * <p>
 * When we decide to delete number 4, the result will be:
 * 
 * <pre>
 * f(4) + f(2)
 * </pre>
 * 
 * When we delete number 3, we have:
 * 
 * <pre>
 * f(3) + f(1)
 * </pre>
 * 
 * <p>
 * Thus for i, the optimal result will be:
 * 
 * <pre>
 * Result(i) = Max(f(i) + f(i - 2), f(i - 1))
 * </pre>
 * 
 * Note that the base case is when the number is 1 or n. Since we starts from
 * the left, we only take cares of f(1), that:
 * 
 * <pre>
 * f(1) = 1 * count[1]
 * </pre>
 * 
 * For number 2, we select the max among 1 and 2:
 * 
 * <pre>
 * f(2) = Max(2 * count[2], f(1))
 * </pre>
 * 
 * Because we may select f(1) and f(4), if f(1) > f(2).
 * <p>
 * https://leetcode-cn.com/problems/delete-and-earn/
 */
public class DeleteAndEarn {

    public int deleteAndEarn(int[] nums) {
        int[] count = new int[10001];
        int n = 10001;
        int max = 0;
        for (int v : nums) {
            ++count[v];
            if (v > max)
                max = v;
        }
        int[] dp = new int[n];
        dp[1] = count[1] * 1;
        // e.g., if dp[1] > dp[2], we might pick dp[1] and dp[4]
        dp[2] = Math.max(count[2] * 2, dp[1]);
        for (int i = 3; i <= max; i++) {
            dp[i] = Math.max(count[i] * i + dp[i - 2], dp[i - 1]);
        }
        return dp[max];
    }
}