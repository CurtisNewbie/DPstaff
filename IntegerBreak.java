import java.util.*;

/**
 * https://leetcode-cn.com/problems/integer-break/
 */
public class Solution{

   public int integerBreak(int n){
        int[] dp = new int[n+1];
        // starts from 1...n, because when a number n is splited to smaller integers k1, k2, k3...kn
        // {k1, k2, ...kn} must be smaller than n, thus calculating the result from 1 to n, ensures that
        // when we calculate n, and split n to nk and n-nk, we already have the result of dp[nk] and dp[n-nk].
        for(int i = 1; i <= n; i++){ 
            dp[i] = maxMult(i, dp);
        }
        return dp[n];
   }

   public int maxMult(int n, int[] dp){
       if(n == 1){
           return 1; // 1 won't affect the result of multiplication
       }
       int max = 1;
       // starts from 1, since an integer must be splited to at least two integers
       for(int i = 1; i < n; i++){
           // either n-i * i or dp[n-i] * i (where n-i is splited to smaller integers that yields greater result of multiplication)
           // e.g., 5 splited to 2 and 3, where dp[2] = 1 and dp[3] = 2, thus we have 2 * 3 > dp[2] * dp[3].
           max = Math.max(max, Math.max((n-i)* i, dp[n-i] * i));   
       }
       return max;
   }
}
