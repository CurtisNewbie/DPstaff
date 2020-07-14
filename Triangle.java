import java.util.*;

/**
 * https://leetcode-cn.com/problems/triangle/
 */
public class Triangle {

    /**
     * Question is pretty straight-forward. We are looking for a path from the top
     * to the bottom that has a minimum sum. And we can only move to an adjacent
     * number at each layer. Then we have already known that:
     * <p>
     * Let i be the row/layer, j be the one in the row i:
     * <p>
     * triangle[i][j] can only go to triangle[i+1][j] or triangle[i+1][j+1].
     * <p>
     * Then, if F(triangle[x][y]) is the function for calculating the sum after
     * going to triangle[x][y]. Then we know that the best decision for
     * triangle[i][j], we are looking for:
     * 
     * <pre>
     * MIN(F(triangle[i + 1][j]), F(triangle[i + 1][j + 1]))
     * </pre>
     * 
     * Then, based on this relation, we can have a dp[][] that records the sum for
     * each triangle. This will take O(N) time complexity, but to find the solution,
     * we can optimise it to be O(Last_Layer), where "Last_Layer" is the length of
     * the bottom layer, since it's a triangle, "Last_Layer" is enough for the
     * result.
     * <p>
     * We then traverse the triangle bottom-up or top-down, as we are traversing, we
     * update dp[i] using the equation above. The base case for this will be that
     * 
     * <pre>
     * FOR i IN bottomLayer:
     *     dp[i] = triangle[bottomLayer][i];
     * </pre>
     * 
     * The final result should be dp[0];
     */
    public int minimumTotal(List<List<Integer>> triangle) {
        int layers = triangle.size();
        if (layers == 0)
            return 0;
        // dp is of the length of the last layer, no need to warry about
        // out-of-boundary
        int[] dp = new int[triangle.get(layers - 1).size()];

        for (int i = layers - 1; i >= 0; i--) { // for each row, bottom->up
            int m = triangle.get(i).size();
            if (i == layers - 1) { // the bottom layer
                for (int j = 0; j < m; j++)
                    dp[j] = triangle.get(i).get(j);
            } else {
                for (int j = 0; j < m; j++)
                    dp[j] = Math.min(dp[j], dp[j + 1]) + triangle.get(i).get(j);
            }
        }
        return dp[0];
    }
}