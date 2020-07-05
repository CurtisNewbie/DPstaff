import java.util.*;

/**
 * <p>
 * Given two strings A and B, ask the minimum number of the editing (steps)
 * needed to transform A to B. A and B can be of different length, and there are
 * three editing operations available: replacement, deleting and inserting.
 * I.e., we can replace a char in A, delete a char in A, or insert/append a char
 * in A.
 * <p>
 * horse -> rorse (replace 'h' with 'r')
 * <p>
 * rorse -> rose (delete 'r')
 * <p>
 * rose -> ros (delete 'e')
 * <p>
 * Independent subproblems exist, thus this is a DP problem.
 * <p>
 * We can start with shorter strings and analyse how things work, Let the length
 * of A be m, and length of B be n:
 * <p>
 * 1. Let, A:'h', B:'r'
 * <p>
 * > To Transform A to B, we need to replace h with r.
 * <p>
 * > f(m, n) = 1
 * <p>
 * 2. Let, A:'h', B:'ro'
 * <p>
 * > To transform A to B, we need to replace h with r, and append o.
 * <p>
 * > f(m, n) = 2 = f(m, n-1) + 1 // f(m, n-1) is for A:'h', B:'r', extra 1 is
 * for adding 'o'. Notice that in such case, we know we have to add extra 1
 * because B has an extra char, but the overall value of f(m,n) is based on the
 * value of f(m,n-1), same applied to other situations.
 * <p>
 * 3. Let, A:'ho', B:'ro'
 * <p>
 * > To transform A to B, we need to replace h with r.
 * <p>
 * > f(m, n) = 1 = f(m-1, n-1) // f(m-1, n-1) is for A:'h', B:'r'
 * <p>
 * 4. Let, A:'ho', B:'r'
 * <p>
 * > To transform A to B, we need to replace h with r, and delete o
 * <p>
 * > f(m, n) = 2 = f(m-1, n) + 1 // f(m-1, n) is for A:'h', B:'r', extra is for
 * deleting 'o'.
 * <p>
 * Base on observation above, we can notice that these situations are similar to
 * states, that one can move to another. The combined equation will be:
 * <p>
 * > IF A[m-1]==B[n-1], f(m,n) = f(m-1,n-1)
 * <p>
 * > IF A[m-1]!=B[n-1], f(m,n) = MIN(f(m-1,n), f(m,n-1), f(m-1,n-1)) + 1;
 * <p>
 * The next step is to figure out the base cases:
 * <p>
 * 1. A: "", B: ""
 * <p>
 * > f(0, 0) = 0
 * <p>
 * 2. A: "a", B: ""
 * <p>
 * > f(1, 0) = 1
 * <p>
 * 3. A: "a", B: ""
 * <p>
 * > f(0, 1) = 1
 * <p>
 * For situations 2 and 3, we can have:
 * <p>
 * > f(0,k) = k, k in n
 * <p>
 * > f(j,0) = j, j in m
 * <P>
 * Based on these base cases, and equations, we can imagine a state table or DP
 * table that records the values of f(m,n). Where (0,0) represents the value of
 * f(m,n).
 * <p>
 * https://leetcode-cn.com/problems/edit-distance/
 */
public class EditDistance {
    public static void main(String[] args) {
        String a = "horse";
        String b = "ros";
        System.out.printf("Result: %d", minDistance(a, b));
    }

    /**
     * 
     * @param a String to be transformed
     * @param b Target String
     * @return minimum number of editing needed to transform a to b
     */
    public static int minDistance(String a, String b) {
        int m = a.length();
        int n = b.length();
        // for f(m,n)
        int[][] dp = new int[m + 1][n + 1];
        // init dp base cases
        dp[0][0] = 0;
        // for m
        for (int i = 1; i <= m; i++) {
            dp[i][0] = i;
        }
        // for n
        for (int i = 1; i <= n; i++) {
            dp[0][i] = i;
        }
        // fill DP table based on equations
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1];
                else
                    dp[i][j] = min(dp[i - 1][j - 1], dp[i][j - 1], dp[i - 1][j]) + 1;
            }
        }
        for (int[] r : dp) {
            System.out.println(Arrays.toString(r));
        }
        return dp[m][n];
    }

    public static int min(int a, int b, int c) {
        int min = a < b ? a : b;
        return min < c ? min : c;
    }
}