/**
 * https://leetcode-cn.com/problems/interleaving-string/
 */
public class InterleavingString {
    /**
     * let m1 be s1.length
     * <p>
     * let m2 be s2.length
     * <p>
     * if s3 is the interleaving string of s1 and s2, then if s3 is of length n,
     * then n has to be of (m1 + m2).
     * <p>
     * Interleaving means that the characters of both s1 or s2 are in order. Then
     * for every char in s3, there are two possible options, either one from s1 or
     * one from s2. Each decision (for each char) is affected by previous decision
     * (previous char), thus the decision tree is in fact a binary tree. We can use
     * two pointers to traverse the decision tree to verify of s3 is the
     * interleaving string. However, this approach will fail if there are duplicate
     * char in s1 and s2.
     * <p>
     * We can also have a state table to record the states all these decisions. Then
     * we will have a dp[m1][m2] that records the states on these decisions. A path
     * from the top-left cornor to the bottom-right conor indicates a possible
     * combination. If the current decision (dp[i][j]) could not lead to an
     * interleaving string, we mark it as false. Thus all decision in a path of an
     * interleaving string must be true.
     * <p>
     * In such state tables, we cannot move backward (choosing the char that we have
     * chosen). I.e., we can only move down or right. Further, we can only move to
     * adjacent char, or i.e., we have to either pick one from s1 or s2. Then, for
     * s3[i] (i = j + k - 1), we check the previous decisions in
     * 
     * <pre>
     * dp[j][k-1] OR dp[j-1][k]
     * </pre>
     * 
     * The base cases for such approach are choosing all char in s1 then s2, or
     * choosing all char in s2 then s1.
     */
    public boolean isInterleave(String s1, String s2, String s3) {
        int m1 = s1.length();
        int m2 = s2.length();
        int n = m1 + m2;
        if (s3.length() != n) {
            return false;
        }

        boolean dp[][] = new boolean[m1 + 1][m2 + 1];
        dp[0][0] = true;
        // select all s1 first then s2
        for (int i = 1; i <= m1 && s3.charAt(i - 1) == s1.charAt(i - 1); i++) {
            dp[i][0] = true;
        }

        // select all s2 first then s1
        for (int i = 1; i <= m2 && s3.charAt(i - 1) == s2.charAt(i - 1); i++) {
            dp[0][i] = true;
        }

        for (int i = 1; i <= m1; i++) {
            for (int j = 1; j <= m2; j++) {
                int s3c = s3.charAt(i + j - 1);
                // from top or left
                dp[i][j] = (s3c == s2.charAt(j - 1) && dp[i][j - 1]) || (s3c == s1.charAt(i - 1) && dp[i - 1][j]);
            }
        }
        return dp[m1][m2];
    }

    // Optimised in terms of space complexity O(m2)
    public boolean isInterleaveOptimised(String s1, String s2, String s3) {
        int m1 = s1.length();
        int m2 = s2.length();
        int n = m1 + m2;
        if (s3.length() != n) {
            return false;
        }

        boolean dp[] = new boolean[m2 + 1];
        dp[0] = true;
        for (int i = 0; i <= m1; i++) {
            for (int j = 0; j <= m2; j++) {
                int s3c = i + j - 1;
                if (i > 0) // this handles base case when only s1 is selected (j == 0)
                    dp[j] = (dp[j] && s3.charAt(s3c) == s1.charAt(i - 1));
                if (j > 0) // this handles base case when only s2 is selected (i == 0)
                    dp[j] = dp[j] || (dp[j - 1] && s3.charAt(s3c) == s2.charAt(j - 1));
            }
        }
        return dp[m2];
    }
}