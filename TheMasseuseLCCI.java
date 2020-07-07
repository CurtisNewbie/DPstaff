/**
 * https://leetcode-cn.com/problems/the-masseuse-lcci/
 */
public class TheMasseuseLCCI {

    /**
     * This is essentially HouseRobber problem
     * <p>
     * f(n) = MAX(f(n-1), f(n-2) + nums[n-1])
     */
    public int massage(int[] nums) {
        int n = nums.length;
        if (n == 0)
            return 0;
        else if (n == 1)
            return nums[0];

        int first = nums[0];
        int second = max(nums[0], nums[1]);
        for (int i = 2; i < n; i++) {
            int t = second;
            second = max(first + nums[i], second);
            first = t;
        }
        return second;
    }

    private int max(int a, int b) {
        return a > b ? a : b;
    }
}