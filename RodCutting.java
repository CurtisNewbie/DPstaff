import java.util.*;

/**
 * <p>
 * [Copied From Book]
 * <p>
 * The rod-cutting problem is the following. Given a rod of length n inches and
 * a table of prices pi for i = 1, 2, ... n, determine the maximum revenue rn
 * obtainable by cutting up the rod and selling the pieces. Note that if the
 * price pn for a rod of length n is large enough, an optimal solution may
 * require no cutting at all.
 * <p>
 * In navie approach, we use recursion to calculate the maximum revenue, by
 * calculating the maximum value among the following situations:
 * <p>
 * MAX({p[n], cut(p,n-p1)+p[1], cut(p,n-p2)+p[2] ... cut(p,n-pi)+p[i]});
 * <P>
 * > p[n]: selling the rod without cutting, in actual implementation, p[n] =
 * p[n] + cut(p, n-n).
 * <p>
 * > cut(p, n-p1) + p[1]: cut down p1 length of rod, and calculate the remaining
 * by continue cutting or selling the remaining rod without cutting (recursion)
 * <p>
 * Thus, lots of repetive function calls are made in top-down approach. We can
 * visualise it by drawing a tree for the method call.
 * <p>
 * We are only traversing down in one of the path. For j, there are j-1 options
 * (the extra one is not cutting it at all) that we can pick, and for each
 * node/option, we can choose to cut or not cut, thus the time complexity will
 * be roughly 2^(n-1), that's O(2^n);
 * <p>
 * {cut(p, j-1), cut(p, j-2), cut(p, j-3),...cut(p, 1)}
 * <p>
 * With Memorization, we can record the results of j which is cut(p, j);
 * Memorization is essentially a recursive top-down technique, that traverses
 * the "tree" of results, from n -> 1, and return the results bottom up.
 * <p>
 * The time complexity is O(n^2), because for each j, we check the price for
 * cutting of 1, 2, 3...j.
 * <p>
 * The DP version is quite similar to the Memorization technique, except that
 * it's bottom-up, thus reduces quite a lot overhead caused by the recursion. In
 * DP, the cut(p, 1) is calculated first, then 2,3 ... n. Thus when we try to
 * calculate cut(p, j), the results of 1 -> j-1 must have been calculated.
 * <p>
 * The time complexity for the DP version is the same as the Memorization
 * version, O(N^2)
 * <p>
 * https://stackoverflow.com/questions/6184869/what-is-the-difference-between-memoization-and-dynamic-programming#:~:text=Memoization%20is%20the%20top-down%20technique%20%28start%20solving%20the,base%20case%20%28s%29%20and%20works%20its%20way%20upwards.
 * <p>
 * https://blog.csdn.net/iva_brother/article/details/84037050
 * <p>
 * Book "Introduction to Algorithm"
 * <p>
 */
public class RodCutting {

    public static void main(String[] args) {
        int[] p = new int[] { 1, 5, 8, 9, 10, 17, 17, 20, 24, 30, 35 };
        int n = 4;
        System.out.printf("Rod Length: %d\n", n);
        System.out.printf("Prices: %s\n", Arrays.toString(p));
        System.out.printf("(Recursive Approach) Max Price: %d\n", rcut(p, n));
        System.out.printf("(Memorisation Approach) Max Price: %d\n", memocut(p, n));
        System.out.printf("(DP Approach) Max Price: %d\n", dpcut(p, n));
    }

    /**
     * Recursive Version O(2^N)
     * <p>
     * One problem with this approach is that we keep calling cut() method on same
     * n.
     * 
     * @param p prices for rod of length (1 -> n)
     * @param n length of the rod
     */
    public static int rcut(int[] p, int n) {
        if (n == 0)
            return 0;
        int price = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) { // cut 1,2,3...n
            price = max(price, p[i - 1] + rcut(p, n - i)); // cut off pi length, p[0] represents the price for length 1
        }
        return price;
    }

    /**
     * Memorisation Version with memo array O(n^2), e.g., for each x in n, loop n
     * times
     * <p>
     * This solves the problems in recursive version, but we can further optimise it
     * if it's bottom-up rather than top-bottom.
     * 
     * @param p prices for rod of length (1 -> n)
     * @param n length of the rod
     */
    public static int memocut(int[] p, int n) {
        int[] memo = new int[p.length + 1];
        Arrays.fill(memo, -1);
        return mrcut(p, n, memo);
    }

    private static int mrcut(int[] p, int n, int[] memo) {
        if (n == 0)
            return 0;
        if (memo[n] >= 0)
            return memo[n];
        int price = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            price = max(price, p[i - 1] + mrcut(p, n - i, memo));
        }
        memo[n] = price;
        return price;
    }

    /**
     * Similar to the Memo approach, this DP version adopts a bottom-up approach.
     * Bottom-up approach in DP, solves the sub-problems first, and then the final
     * problem. At first glance, it may be similar to the memocut() that they
     * essentially reduce time complexity by recording results. But bottom-up
     * approach (or tabulation) reduces overhead caused by the recursion.
     * <p>
     * O(n^2), e.g., for each x in n, loop n times
     * 
     * @param p prices for rod of length (1 -> n)
     * @param n length of the rod
     */
    public static int dpcut(int[] p, int n) {
        if (n == 0) {
            return 0;
        }
        int[] dp = new int[p.length + 1];

        for (int j = 1; j <= n; j++) { // for rod of length j
            int price = Integer.MIN_VALUE;
            for (int i = 1; i <= j; i++) { // cut off length i
                price = max(price, p[i - 1] + dp[j - i]); // j-i is the remaining length of i after cutting of j
            }
            dp[j] = price;
        }
        return dp[n];
    }

    private static int max(int a, int b) {
        return a > b ? a : b;
    }
}