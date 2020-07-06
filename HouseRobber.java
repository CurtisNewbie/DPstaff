import java.util.*;

/**
 * https://leetcode-cn.com/problems/house-robber/
 */
public class HouseRobber {
    public int rob(int[] nums) {
        int n = nums.length;
        if (n == 0)
            return 0;

        int[] dp = new int[n + 1];
        // because the amount of money is always positive, we will
        // inherently try to rob as many house as we can, except the one next to us.
        // thus f(n) = nums[n-1] + MAX(f(n-2), f(n-3));
        for (int i = 0; i <= n; i++) {
            int n2 = i - 2 >= 0 ? dp[i - 2] : 0;
            int n3 = i - 3 >= 0 ? dp[i - 3] : 0;
            dp[i] = (i < n ? nums[i] : 0) + max(n2, n3);
        }
        // dp[n] is for max(dp[n-2], dp[n-3]), where we skipped n-1
        // dp[n-1] is for max(dp[n-3], dp[n-4]), where we skipped n-2
        // such that every element must have a chance to be skipped, and we pick one
        // with the most amount of money
        return max(dp[n], dp[n - 1]);
    }

    public int betterRob(int[] nums) {
        // Same logic but with O(1) space complexity
        int n = nums.length;
        if (n == 0)
            return 0;
        if (n == 1)
            return nums[0];

        int prev = nums[0], curr = max(nums[0], nums[1]);

        // dp[n] = max(dp[n-1], dp[n-1] + nums[n-1])
        // because dp[n-1] is adjacent to dp[n], we don't add nums[n-1]
        for (int i = 2; i < n; i++) {
            int t = curr;
            curr = max(prev + nums[i], curr);
            prev = t;
        }
        return curr;
    }

    private int max(int a, int b) {
        return a > b ? a : b;
    }
}