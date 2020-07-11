import java.util.*;

/**
 * https://leetcode-cn.com/problems/count-of-smaller-numbers-after-self/
 */
public class CountSmallerNumber {

    /**
     * Naive O(N^2) approach , timeout
     * <p>
     * Starts with an example:
     * 
     * <pre>
     * int[] nums = [1,9,7,6,5,8,5,7]
     * </pre>
     * 
     * For each number in i, WHERE i starts from n-1 and moves towards 0. We update the numbers on
     * the right that are less than nums[i], the result for i is recorded in dp[i].
     * <p>
     * > dp[7] = 0, because its' the last number.
     * <p>
     * > dp[6] = 0, because 5 < 7
     * <p>
     * For each number i, we have pointer j, where j moves from i+1 towards n-1. The first number
     * that is less than nums[j], we assign j to max, where max represents the maximum number that
     * is less than nums[i]. Then there are three situations:
     * <p>
     * > 1. nums[j] > nums[max], we take into acount the numbers that are less than nums[j]
     * including nums[j] itself, but because current max may have already taken into account the
     * numbers that the next max have, we have to remove duplicates, then we have:
     * 
     * <pre>
     * WHEN nums[j] > nums[max] THEN dp[i] = dp[i] - dp[max] + beteenMax + dp[j] + 1 AND max = j.
     * </pre>
     * 
     * > 2. nums[j] == nums[max], we know that, we have already added the numbers that are less than
     * nums[max], we only increments one, for the nums[j].
     * 
     * <pre>
     * WHEN nums[j] == nums[max] THEN dp[i]++;
     * </pre>
     * 
     * > 3. nums[j] < nums[max], in such case, we shall do nothing, but remembers that we are trying
     * to remove the duplicates before we meet the next 'max'. Thus, we records the count between
     * the two max, where the numbers are in fact less than nums[i] as well as nums[max].
     * 
     * <pre>
     * WHEN nums[j] < nums[max] THEN betweenMax++;
     * </pre>
     * <p>
     * For number 9, which is at index 1. We look for the numbers on the right that are less than 9.
     * <p>
     * The first max is 7, then we have:
     * 
     * <pre>
     * dp[1] += dp[2] + 1, AND max = 2.
     * </pre>
     * 
     * When we at number 8, which is nums[5], betweenMax should be 2. Because 8 > max, we have:
     * 
     * <pre>
     * dp[1] = dp[1] - dp[max] + betweenMax + dp[5] + 1;
     * </pre>
     * 
     * which is:
     * 
     * <pre>
     * dp[1] = dp[1] - dp[2] + 2 + dp[5] + 1.
     * </pre>
     * <p>
     */
    public List<Integer> naiveCountSmaller(int[] nums) {
        int n = nums.length;
        List<Integer> dp = new ArrayList<>(n);
        for (int i = 0; i < n; i++)
            dp.add(0);
        if (n == 0)
            return dp;

        dp.set(n - 1, 0); // intended for clarity
        for (int i = n; i >= 0; i--) {
            int max = -1;
            int countBetweenMax = 0;
            for (int j = i + 1; j < n; j++) {
                if (max == -1 && nums[j] < nums[i]) {
                    max = j;
                    dp.set(i, dp.get(i) + dp.get(j) + 1);
                } else if (nums[j] < nums[i]) {
                    if (nums[j] == nums[max]) {
                        dp.set(i, dp.get(i) + 1);
                    } else if (nums[j] > nums[max]) {
                        dp.set(i, dp.get(i) - dp.get(max) + countBetweenMax + dp.get(j) + 1);
                        max = j;
                        countBetweenMax = 0;
                    } else {
                        countBetweenMax++;
                    }
                }
            }
        }
        return dp;
    }

    /**
     * O(N + N * (lgN + M)), thus O(N*(lgN + M)) approach using buckets and discretization.
     * <p>
     * Note, M is likely to be huge
     * <p>
     * N is the length of nums, M is the lengh of buckets
     * <p>
     * The general idea is to use bucket to count the occurences of each number. Say, the numbers in
     * {@code nums} are within [1-9], then we only need 10 buckets. We traverse nums from n-1 -> 0,
     * and as we are traversing, we update the bucket.
     * <p>
     * 
     * <pre>
     * For nums[i], bucket[nums[i]]++;
     * </pre>
     * 
     * However, the problem is that we must know what the numbers in nums[] are beforehand, and
     * these numbers will surely not be consequtive. We must apply discretization, which is evenly
     * distributing the arbitray numbers into a continguous area (array).
     */
    public List<Integer> countSmaller(int[] nums) {
        int n = nums.length;
        LinkedList<Integer> res = new LinkedList<>();

        // use the unique numbers or i.e., discretized numbers to create buckets
        int[] uniqueNums = discretize(nums);
        int m = uniqueNums.length;
        int[] buckets = new int[m];

        for (int i = n - 1; i >= 0; i--) {
            // binary search to find it's correct position in buckets
            int v = nums[i];
            int l = 0;
            int h = m - 1;
            int mid = 0;
            while (l <= h) {
                mid = (l + h) >> 1;
                if (uniqueNums[mid] > v) {
                    h = mid - 1;
                } else if (uniqueNums[mid] < v) {
                    l = mid + 1;
                } else {
                    break;
                }
            }
            int count = 0;
            // buckets maintains an order, and we want to count the numbers that are less than it
            for (int j = 0; j < mid; j++) {
                count += buckets[j];
            }
            res.offerFirst(count);
            // update bucket for current number
            buckets[mid]++;
        }
        return res;
    }

    /**
     * O(N + N * (lgN + lgN + lgN), thus, O(NlgN)
     * <p>
     * Discretization + Binary Indexed Tree, or Fenwick Tree
     * <p>
     * https://leetcode-cn.com/problems/count-of-smaller-numbers-after-self/solution/shu-zhuang-shu-zu-by-liweiwei1419/
     * <p>
     * https://en.wikipedia.org/wiki/Fenwick_tree
     */
    public List<Integer> countSmallerWithTree(int[] nums) {
        int n = nums.length;
        LinkedList<Integer> res = new LinkedList<>();
        int[] uniqueNums = discretize(nums);
        int[] buckets = new int[n + 5];
        int m = buckets.length;

        for (int i = n - 1; i >= 0; i--) {
            // binary search to find it's correct position in buckets
            int v = nums[i];
            int index = bs(v, uniqueNums) + 1;
            // we want to count the numbers that are less than it
            // since buckets maintains an order, we can treat it as a completely left-leaning
            // tree, aka Binary Indexed Tree
            res.add(sum(index - 1, m, buckets));
            // update the tree
            update(index, m, buckets);
        }
        Collections.reverse(res);
        return res;
    }

    public int[] discretize(int[] nums) {
        // extract uniqune numbers as bucket, we want to maintain an order such that we can use
        // binary search
        Set<Integer> set = new TreeSet<>();
        for (int n : nums)
            set.add(n);

        // distribute them into continguous area
        int[] buckets = new int[set.size()];
        int bp = 0;
        for (int n : set)
            buckets[bp++] = n;
        return buckets;
    }

    int lowBit(int x) {
        return x & (-x);
    }

    void update(int pos, int m, int[] buckets) {
        while (pos < m) {
            buckets[pos] += 1;
            pos += lowBit(pos);
        }
    }

    int sum(int pos, int m, int[] buckets) {
        int count = 0;
        while (pos > 0) {
            count += buckets[pos];
            pos -= lowBit(pos);
        }
        return count;
    }

    int bs(int v, int[] uniqueNums) {
        int len = uniqueNums.length;
        int l = 0;
        int h = len - 1;
        int mid = 0;
        while (l <= h) {
            mid = (l + h) >> 1;
            if (uniqueNums[mid] > v) {
                h = mid - 1;
            } else if (uniqueNums[mid] < v) {
                l = mid + 1;
            } else {
                break;
            }
        }
        return mid;
    }
}
