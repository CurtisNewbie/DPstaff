import java.util.*;

/**
 * https://leetcode-cn.com/problems/contiguous-sequence-lcci/
 */
public class ContiguousSequenceLCCI {

    /**
     * O(N).
     */
    public int maxSubArray(int[] nums) {
        int n = nums.length;
        if (n == 0)
            return 0;

        int sum = nums[0]; // current sum
        int max = sum; // max sum
        for (int i = 1; i < n; i++) {
            // if sum < 0, restart from nums[i]
            // sum < 0, also indicates that the elements included in the current sum are not
            // worth adding into subarray, since it makes no contribution
            if (sum < 0) {
                sum = nums[i];
            } else {
                sum += nums[i];
            }
            if (sum > max)
                max = sum;
        }
        return max;
    }
}