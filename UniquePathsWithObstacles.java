/**
 * <p>
 * https://leetcode-cn.com/problems/unique-paths-ii/
 * <p>
 */
public class UniquePathsWithObstacles {

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int row = obstacleGrid.length;
        if (row == 0)
            return 1;
        int col = obstacleGrid[0].length;

        int[][] dp = new int[row][col]; // dp[i][j] represents the number unique path to obstacle[i][j]
        // init dp for base cases, that we only move down or only move right
        // and there is no obstacle in our way
        for (int i = 0; i < row && obstacleGrid[i][0] == 0; i++) {
            dp[i][0] = 1;
        }
        for (int i = 0; i < col && obstacleGrid[0][i] == 0; i++) {
            dp[0][i] = 1;
        }

        // for each node we only move down or right
        // for each decision we make, a unique path is created
        // regardless of whether its a correct path or not
        // thus for (x, y), the only way to move to this point is via
        // (x-1, y) or (x, y-1), thus we have:
        // f(x,y) = f(x-1, y) + f(x, y-1);
        for (int x = 1; x < row; x++) {
            for (int y = 1; y < col; y++) {
                if (obstacleGrid[x][y] == 0)
                    dp[x][y] = dp[x - 1][y] + dp[x][y - 1];
            }
        }
        return dp[row - 1][col - 1];
    }
}