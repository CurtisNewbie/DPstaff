import java.util.*;

public class LongestIncreasingSubsequence {

    public static void main(String[] args) {
        // int[] arr = new int[] { 1, 7, 2, 8, 3, 4 };
        // int[] arr = new int[] { 2, 1, 5, 3, 6, 4, 8, 9, 7 };
        int[] arr = new int[] { 2, 1, 5, 3, 6, 4, 8, 9, 7, 10, 3, 24, 25, 32, 3, 35 };
        System.out.printf("Arr: %s\n", Arrays.toString(arr));
        System.out.printf("(DP LIS) Longest Increasing Subsequence: %d characters\n", lis(arr));
        System.out.printf("(BS+DP LIS) Longest Increasing Subsequence: %d characters\n", bsLis(arr));
    }

    /**
     * To find the LIS (1,2,3,4), we must first find 1, then 2, then 3, etc.
     * Subproblems exist.
     * <p>
     * E.g., we can split arr into two parts, then 1,2 will be on the left, and 3,4
     * will be on the right We can imagine, LIS(N) to be LIS(0->N), and for LIS(N),
     * we are looking for MAX(LIS(0), LIS(1), LIS(2), LIS(3)...LIS(N-1)) + arr[N-1]
     * Then we can starts from 0 towards N.
     * <p>
     * O(N^2)
     */
    public static int lis(int[] arr) {
        int longest = -1;
        int[] dp = new int[arr.length]; // LIS for 0:N
        for (int i = 0; i < arr.length; i++) {
            dp[i] = 1; // the arr[i] itself
            // find LIS in LIS[0], LIS[1] ... LIS[i-1]
            // as LIS[i], we only consider LIS[j] where j < i
            for (int j = 0; j < i; j++) {
                if (arr[i] >= arr[j] && dp[i] < dp[j] + 1) { // find max(LIS[j])
                    dp[i] = dp[j] + 1;
                }
            }
            if (longest == -1 || dp[longest] < dp[i])
                longest = i;
        }
        return dp[longest];
    }

    /**
     * Binary Search and DP
     * <p>
     * Note the resulting ans[] is not the actual LIS, ans[j] indicates the LIS of
     * length j ends with ans[j].
     * <p>
     * I.e., if we want to get a LIS of length k, we traverse arr[], from n.length
     * -> 0, and collects all elements that are less than k and in descending order,
     * the elements collected will be the LIS of length k.
     * <p>
     * O(NlogN)
     */
    public static int bsLis(int[] arr) {
        int ans[] = new int[arr.length];
        ans[0] = arr[0];
        int len = 0;
        // for each char in arr
        for (int i = 1; i < arr.length; i++) {
            System.out.printf("ans: %s\n", Arrays.toString(ans));

            // if arr[i] is greater than the last char in ans,
            // we append arr[i] into ans
            if (arr[i] > ans[len])
                ans[++len] = arr[i];
            else {
                // if arr[i] is less than arr[len] (last char),
                // we replace arr[pos] with arr[i] by using BinarySearch,
                // note that ans[] is not an actual LIS, instead, it
                // represents that for length of j, ans[j] is the last
                // value in the LIS.
                int pos = bs(ans, 0, len, arr[i]);
                ans[pos] = arr[i];
            }
        }
        System.out.printf("ans: %s\n", Arrays.toString(ans));
        return len + 1;
    }

    private static int bs(int[] ans, int l, int h, int t) {
        while (l < h) {
            int mid = l + (h - l) / 2;
            if (ans[mid] >= t)
                h = mid; // take left value, thus h = mid rather than h = mid - 1
            else
                l = mid + 1;
        }
        return l;
    }
}