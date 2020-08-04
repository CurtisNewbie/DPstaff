/**
 * https://leetcode-cn.com/problems/jump-game/
 * <p>
 * Game can be solved using DP or greedy algorithm.
 */
public class JumpGame {

    /**
     * DP approach O(n*m), where m is the average value in nums.
     */
    public boolean canJumpDP(int[] nums) {
        int n = nums.length;
        if (n == 0 || n == 1)
            return true;
        boolean[] dp = new boolean[n];
        dp[n - 1] = true; // destination
        // from destination towards starting point
        for (int i = n - 2; i >= 0; i--) {
            // whether current position i can reach to a valid pos that can reach destination
            for (int j = 0; j <= nums[i] && i + j < n; j++) {
                if (dp[i + j] == true) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[0];
    }

    /**
     * Greedy Algorithm O(n)
     */
    public boolean canJump(int[] nums) {
        int n = nums.length;
        if (n == 0 || n == 1)
            return true;
        int rightMost = 0; // right most position that we can reach (so far)
        for (int i = 0; i < n; i++) {
            if (i <= rightMost) { // i is a point where we can reach
                // update rightMost
                rightMost = Math.max(rightMost, i + nums[i]);
                if (rightMost >= n - 1) // we can reach destination already
                    return true;
            }
        }
        return false;
    }
}
