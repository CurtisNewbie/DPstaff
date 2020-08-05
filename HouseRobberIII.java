/**
 * https://leetcode-cn.com/problems/house-robber-iii/
 */
public class HouseRobberIII {

    /**
     * If we are solving similar problem in an array, we decides that whether we rob house[i] or
     * house[i+1].
     * <p>
     * Same applied for a binary tree.
     * <p>
     * In this case, we decide whether we should rob the current one and skip the next layer, or
     * skip the current one and rob the next layer.
     * <p>
     * Then we have the following idea (in general):
     * 
     * <pre>
     * MAX(rob(curr.next.next) + curr, rob(curr.next));
     * </pre>
     * 
     * But soon, we can realise that we call rob() same nodes over and over again, just like how we
     * calculate fib number in an naive way. However, if we change the approach to bottom-up, this
     * becomes O(n). For each node, we calculate the results with or without the node's value, and
     * each node will only be processed once.
     * <p>
     * E.g., with int[2], where int[0] is the result with node.val, and int[1] is the result without
     * node.val.
     * <p>
     * Then, for current node, res[1] will be
     * 
     * <pre>
     * res[1] = node.val + left[1] + right[1]
     * </pre>
     * 
     * , since the next layer must be skipped.
     * <p>
     * And res[0] will be
     * 
     * <pre>
     * res[0] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
     * </pre>
     * 
     * Because, we can select both left and right, but it doesn't mean we have to.
     */
    public int rob(TreeNode root) {
        if (root == null)
            return 0;
        // must be bottom up
        int[] res = dfs(root);
        // with root or without root
        return Math.max(res[0], res[1]);
    }

    // [0] with root.val, [1] without root.val
    private int[] dfs(TreeNode root) {
        int[] left, right;
        if (root == null)
            return new int[] {0, 0};

        // bottom-up to avoid repeatitive operations
        left = dfs(root.left);
        right = dfs(root.right);
        int withoutRoot = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        int withRoot = root.val + left[1] + right[1];
        // System.out.printf("WithoutRoot: %d, WithRoot: %d", withoutRoot, withRoot);
        return new int[] {withRoot, withoutRoot};
    }
}
