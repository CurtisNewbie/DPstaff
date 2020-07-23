/**
 * https://leetcode-cn.com/problems/minimum-path-sum/
 */
public class MinimumPathSum {

    /**
     * We move from top-left towards bottom-right, so we can either verify the min
     * path sum at top-left or bottom-right, which is only a matter of style.
     * <p>
     * We can use a dp[i][j] to records the minimum sum at grid[i][j], but we don't
     * need to. Because as we are moving from top-left towards bottom-right, we can
     * update the sum of the path, the previous values in grid[x][y] are not needed
     * anymore as long as these are included in the sum of the path. We don't need
     * extra space complexity to find the minimum path sum, and we will eventually
     * get the result at grid[n-1][m-1].
     * <p>
     * Lets move downwards. When we are at grid[i][j], we are either from
     * grid[i-1][j] or grid[i][j-1], and we continue until we move to
     * grid[n-1][m-1];
     */
    public int minPathSum(int[][] grid) {
        int n = grid.length;
        if (n == 0)
            return 0;
        int m = grid[0].length;

        for (int i = 1; i < m; i++)
            grid[0][i] += grid[0][i - 1];
        for (int i = 1; i < n; i++)
            grid[i][0] += grid[i - 1][0];

        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                grid[i][j] += Math.min(grid[i - 1][j], grid[i][j - 1]);
            }
        }
        return grid[n - 1][m - 1];
    }
}