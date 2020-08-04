/**
 * https://leetcode-cn.com/problems/longest-palindromic-substring/
 * */
class LongestPalindrome {

    /**
     * Palindrome is found based on four assumptions:
     * <p>
     * 1 If a string is of length 0, it's a palindrome, e.g., ""
     * <p>
     * 2 If a string is of length 1, it's a palindrome, e.g., "a"
     * <p>
     * 3 If a string is of length 2, the two character must be the same, such that it's
     * a palindrome, e.g., "aa"
     * <p>
     * 4 If a string is of length greater than 2, such as 4, then only when the inner
     * part/substring (of length 2) is a palindrome, the current string can be a palindrome, 
     * more specifically, for a string starts at i and ends at j, it can only be palindrome 
     * when substring(i+1, j-1) is also a palindrome, plus, character at i must equal to the 
     * one at j
     * <p>
     * Then, we can have dp[i][j] to record whether a string that starts at i and ends at j, is a
     * palindrome
     * <p>
     * */
    public String longestPalindrome(String s) {
        int n = s.length();
        // substring (i, j) is palindrome
        boolean dp[][] = new boolean[n][n];
        int maxLen = 0;
        // final answer is [lps...lps+maxLen]
        int lps = 0, j; 
        // length of substring, str[i...j], which is essentially str[i...i+len]
        for(int len = 0; len < n; len++){
            for(int i = 0; i < n; i++){
                // ends at j
                j = i + len;
                if(j >= n)
                    break;
                
                if(len == 0) // assumption 1
                    dp[i][j] = true;
                else if(len == 1) // assumption 2
                    dp[i][j] = s.charAt(i) == s.charAt(j);
                else // assumption 3
                    dp[i][j] = dp[i+1][j-1] && s.charAt(i) == s.charAt(j);
                
                if(dp[i][j] && len + 1 > maxLen){
                    lps = i; 
                    maxLen = len + 1;
                }
            }
        }
        return s.substring(lps, lps + maxLen);
    }

    /**
     * Expand around center, which uses the same idea as the DP approach above, except
     * that it only needs O(1) space complexity.
     * */
    public String logestPalindromeCenterAppro(String s){
        int n = s.length();
        int maxLen = 0, len;
        int lps = 0;
        for(int i = 0; i < n; i++){
            len = Math.max(expandAtCenter(s, i, i), expandAtCenter(s, i, i + 1));
            if(len > maxLen){
                lps = i - (len - 1 ) / 2;
                maxLen = len;
            }
        }
        return s.substring(lps, lps + maxLen);
    }

    private int expandAtCenter(String s, int l, int h){
        int n = s.length();
        while(l >= 0 && h < n && s.charAt(l) == s.charAt(h)){
            --l;
            ++h;
        } 
        return h - l - 1;
    }
}
