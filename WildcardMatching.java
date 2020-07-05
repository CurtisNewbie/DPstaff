import java.util.*;

/**
 * Given an input string ({@code s}) and a pattern ({@code p}), implement
 * wildcard pattern matching with support for '?' and '*'. '?' matches any
 * character, but only matches one. '*' matches zero or many characters.
 * <p>
 * Let dp[i][j] representing the matching result of p[i-1] and s[j-1]. The m is
 * the length of p, and n is the length of s. Thus dp[m][n] represents whether s
 * matches p.
 * <p>
 * We start evaluating both the pattern and text from left to right. There are
 * <p>
 * 1. p[i] is neither '*' nor '?', then we compare s[j] with p[i], and the
 * current matches is only valid when the previous one is also valid. Then we
 * have:
 * <p>
 * > dp[i][j] = s[j-1] == p[i-1] && dp[i-1][j-1];
 * <p>
 * 2. p[i] is a '*', then we either choose the use '*' for current char, or not.
 * If we choose to use '*', we move to dp[i][j-1]. If we choose not to use '*'
 * for current char, we move to dp[i-1][j]. This is the key on solving this
 * problem. Remembers that when using '*', then dp[i][j] = dp[i][j-1]. This
 * essentially means that, at p[i] and s[j-1], we again considers whether we
 * want to use '*' for s[j-1], this essentially simulates how '*' works, that it
 * matches ONE or MANY characters.
 * <p>
 * To extend it a bit more, for dp[5][6], if p[5] is '*', we starts considering
 * whether we want to use '*' to replace s[6]. If we do, then dp[5][6] =
 * dp[5][5]. Again, we considers replacing s[5] with '*', the * mentioned here
 * is still p[5]. If we stops using '*' at s[4], then that means, dp[5][6] =
 * dp[5][5] != dp[5][4]. More precisely, we have use '*' for two characters,
 * replacing s[5:6]. This one is tricky. Overall, we have:
 * <p>
 * > dp[i][j] = dp[i-1][j] || dp[i][j-1];
 * <p>
 * 3. p[i] is '?', then it's guaranteed that s[j] == p[i]. Then as in situation
 * 1, we have:
 * <p>
 * > dp[i][j] = dp[i-1][j-1];
 * <p>
 * https://leetcode-cn.com/problems/wildcard-matching/
 * <p>
 */
public class WildcardMatching {
    public boolean isMatch(String s, String p) {
        int pl = p.length();
        int sl = s.length();
        boolean[][] dp = new boolean[pl + 1][sl + 1];
        // base cases
        dp[0][0] = true;
        for (int i = 1; i <= pl; i++) {
            if (p.charAt(i - 1) == '*') // only the first '*' can match empty s
                dp[i][0] = true;
            else
                break;
        }
        // fill dp table
        // dp[i][j], s[j-1], p[i-1], i is for p, j is for s
        for (int j = 1; j <= sl; j++) {
            for (int i = 1; i <= pl; i++) {
                char pc = p.charAt(i - 1);
                if (pc == '*') {
                    // use * or not, dp[i-1][j]: not use *, dp[i][j-1]: use *
                    dp[i][j] = dp[i - 1][j] || dp[i][j - 1];
                } else if (pc == '?' || pc == s.charAt(j - 1)) {
                    // only when the previous matches, the current one can be matched
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = false;
                }
            }
        }
        return dp[pl][sl];
    }
}