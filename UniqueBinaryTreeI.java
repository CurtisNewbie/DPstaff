/**
 * https://leetcode-cn.com/problems/unique-binary-search-trees/
 */
public class UniqueBinaryTreeI {
    /**
     * We are looking for unique BST structures, the rules of BST must be followed.
     * Set: For n nodes, there are f(n) number of unique structures
     * 
     * In the structure that is rooted at i, there must be nodes [1:i-1] on the left
     * sub-tree and [i+1:n] nodes on the right sub-tree. So we can treat each
     * sub-tree as a sub-problem.
     * 
     * For n nodes and a tree rooted at i, let C(i) represents the number of unique
     * binary trees that is rooted at i.
     * 
     * <pre>
     * C(i) = C([1:i-1]) * C([i+1:n])
     * </pre>
     * 
     * The above equation means: the number of unique sturcture for the trees rooted
     * at i, are the number of unique left sub-trees * right sub-trees. This is
     * classic combination calculation.
     * 
     * For i as root, there are C(i) number of unique structure. For the left
     * sub-tree there are SUM(C(0...i-1)) number of unique structures, because the
     * nodes on the left sub-tree are 1, 2 ... i-1, and each can be the root of the
     * left sub-tree. For the right sub-tree there are SUM(C(i+1...n)) number of
     * unique structures, because the nodes on the right sub-tree are i+1 ... n, and
     * each can be the root of the right sub-tree as well. Then we have:
     * 
     * <pre>
     * C(root) = C(leftSubTree) * C(rightSubTree)
     * </pre>
     * 
     * And for n nodes, we have f(n) as the result, where:
     * 
     * <pre>
     * FOR i IN [1:n]: 
     *     f(n) += C(i);
     * </pre>
     * 
     * It's pretty clear that when we calculate C(i), we must have calculated
     * C(0...i-1). And for C(i+1...n) where i+1...n = k (number of elements), it's
     * nothing different from f(k). For example, if we have [3,4,5], the number of
     * unique binary tree created is of course the same as [1,2,3]. What matters is
     * the number of elements. I.e., the following equation can be converted.
     * 
     * <pre>
     * C(i) = SUM(C(1...i-1) * SUM(C(i+1...n));
     * </pre>
     * 
     * To:
     * 
     * <pre>
     * C(i) = f(i - 1) * f(n - i)
     * </pre>
     * 
     * Then we have the final equation:
     * 
     * <pre>
     * FOR i IN [1:n]: 
     *     f(n) += f(i - 1) * f(n - i);
     * </pre>
     * 
     */
    public int numTrees(int n) {
        int dp[] = new int[n + 1];
        for (int i = 1; i <= n; i++) { // i refers to number of nodes
            dp[i] = count(dp, i);
        }
        // System.out.println(Arrays.toString(dp));
        return dp[n];
    }

    public int count(int[] dp, int n) {
        if (n >= 0 && n <= 1)
            return 1;
        else if (dp[n] > 0)
            return dp[n];

        for (int i = 1; i <= n; i++) { // for n nodes, rooted at i
            if (dp[i - 1] == 0)
                dp[i - 1] = count(dp, i - 1);
            if (dp[n - i] == 0)
                dp[n - i] = count(dp, n - i);
            dp[n] += dp[i - 1] * dp[n - i];
        }
        return dp[n];
    }
}