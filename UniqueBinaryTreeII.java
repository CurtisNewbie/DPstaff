import java.util.*;

/**
 * Similar to "UniqueBinaryTreeI", we try to find the unique binary trees of a n
 * number of nodes.
 * <p>
 * Again, let f(n) represents the number of unique binary trees for n nodes. We
 * have:
 * <p>
 * 
 * <pre>
 * FOR i IN [1...n]: 
 *     f(n) = f(i-1) * f(n-i)
 * </pre>
 * 
 * However, as we are getting the trees not the total number of unique binary
 * trees. We can change this idea a bit. Again, let f(n) represents a List of
 * unique binary trees for n nodes.
 * <p>
 * Then, for [1,2,3], we have f(3) as the list of binary trees. Again, what
 * would f(3) be for [3,4,5]? They both have 3 nodes, thus they both use f(3) as
 * the result. We can realise that the structres are the same for both array,
 * except that the values in nodes are different.
 * <p>
 * By observing these two example, we have:
 * 
 * <pre>
 * _
 * f(3) = getTreesOf([1,2,3])  
 * resultOf([3,4,5]) = incrementValueOfNodes(f(3), 2) // increment value of each node by 2
 * </pre>
 * 
 * Then we can have a dp[] that recordes the unique sturcture/binary trees for n
 * nodes. Then dp[n] will be the final result.
 * <p>
 * I.e., dp[j] is the list of unique binary trees when there are j nodes.
 * 
 */
public class UniqueBinaryTreeII {
    // record the unique structures for j nodes (dp[j])
    // the values in nodes doesn't matter
    List<TreeNode> dp[];

    @SuppressWarnings("unchecked")
    public List<TreeNode> generateTrees(int n) {
        if (n == 0) {
            return new ArrayList<>();
        }
        // dp[3] is the list of unique binary tree when there are three nodes
        dp = (List<TreeNode>[]) new ArrayList[n + 1];
        for (int len = 0; len <= n; len++) {
            generateTreesFor(len); // unique structres for number of nodes
        }
        return dp[n];
    }

    public void generateTreesFor(int len) {
        dp[len] = new ArrayList<>();
        if (len > 0) {
            // r as root
            for (int r = 1; r <= len; r++) {
                for (TreeNode leftTree : dp[r - 1]) { // left-subtree always valid, we can use them directly
                    for (TreeNode rightTree : dp[len - r]) { // right-subtree are of different value but same structure
                        TreeNode root = new TreeNode(r);
                        root.left = leftTree;
                        root.right = copyTree(rightTree, r); // increment the values in tree, e.g., [1,2] -> [4,5] by +3
                        dp[len].add(root);
                    }
                }
            }
        } else {
            dp[len].add(null);
        }
    }

    /**
     * Deep copy and increments values of all nodes by {@code change}
     * 
     * @param root
     * @param change
     * @return
     */
    private TreeNode copyTree(TreeNode root, int change) {
        if (root == null)
            return null;
        TreeNode cr = new TreeNode(root.val + change);
        cr.left = copyTree(root.left, change);
        cr.right = copyTree(root.right, change);
        return cr;
    }
}