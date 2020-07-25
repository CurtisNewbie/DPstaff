import java.util.*;

/**
 * https://leetcode-cn.com/problems/split-array-largest-sum/
 */
public class SplitArrayLargestSum {

    public int splitArray(int[] nums, int m) {
        int n = nums.length;

        // preSum[i] refers to the sum of nums[0...i-1]
        int[] preSum = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            preSum[i] = nums[i - 1] + preSum[i - 1];
        }

        // dp[i][j] refers to the minimum sum among groups where the first i numbers
        // [0...i] are splited into j groups
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 0; i <= n; i++) {
            // since we are finding min sum among splited groups,
            // max_value makes sure the illegal values are ignored in comparison,
            // e.g., empty groups.
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }

        dp[0][0] = 0;
        for (int i = 1; i <= n; i++) { // for the first i items (until n items)
            for (int j = 1; j <= Math.min(i, m); j++) { // split i items into j groups, where j in [1...m]
                for (int k = 0; k < i; k++) {
                    // [k : i] is the last group (the j_th group)
                    // i.e., dp[k][j-1] is the min sum for the first k items that is splited into
                    // j-1 groups and the preSum[i]-preSum[k] is the sum of [k..i] which the last
                    // group (j).
                    dp[i][j] = Math.min(dp[i][j], Math.max(dp[k][j - 1], preSum[i] - preSum[k]));
                }
            }
        }
        return dp[n][m];
    }

    /**
     * Using BinarySearch rather than DP
     * <p>
     * The general idea is that the min sum among groups must be witin:
     * 
     * <pre>
     * res >= MAX(nums[i]) WHERE m == 1
     * </pre>
     * 
     * and
     * 
     * <pre>
     * res <= SUM(nums[0...n-1]) WHERE m == 1
     * </pre>
     * 
     * Then, based on these conditions, we use binary search to find the sum
     * directly.
     */
    public int splitArrayBS(int[] nums, int m) {
        int l = max(nums), h = sum(nums), midSum;
        while (l < h) {
            midSum = l + ((h - l) >> 1);
            // if midSum is too small, it means
            // the number of groups splited > m
            // else if midSum is too greate, it means
            // the number of groups splited < m
            int groupCount = countGroups(nums, midSum);
            if (groupCount > m)
                l = midSum + 1;
            else
                h = midSum;
        }
        return l;
    }

    private int max(int[] nums) {
        int max = nums[0];
        for (int i = 1; i < nums.length; i++)
            if (nums[i] > max)
                max = nums[i];
        return max;
    }

    private int sum(int[] nums) {
        int sum = 0;
        for (int n : nums)
            sum += n;
        return sum;
    }

    /**
     * Return the number of groups splited for {@code midSum}
     */
    private int countGroups(int[] nums, int midSum) {
        int groupCount = 0;
        int groupSum = 0;
        for (int i = 0; i < nums.length; i++) {
            groupSum += nums[i];
            if (groupSum > midSum) {
                groupCount++;
                groupSum = nums[i]; // reset
            }
        }
        return groupCount + 1;
    }
}