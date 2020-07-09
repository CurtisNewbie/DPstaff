import java.util.*;

/**
 * This problem consists of two difficulties:
 * <p>
 * 1. String matching
 * <p>
 * 2. Minimum number of char that are not matched
 * <p>
 * For the the first one, we consider how we handle the {@code dictionary} for
 * efficient searching.
 * <p>
 * 1) One solution will be using hash e.g., Rabin-Karp + HashTable. Rabin-Karp
 * for O(1) time hash calculation, and HashTable for O(1) hash matching. In this
 * approach, all words in {@code dictionary} goes into the HashTable, and
 * Rabin-Karp is then used to calculate hash for all possible words.
 * <p>
 * Since we don't have a specific length for the word being searched, let n be
 * the length of the {@code sentense}, Rabin-Karp takes ~ O(N^2) time (for index
 * j we do j times hash matching [0:j]). To prepare the HashTable, we at least,
 * traverse the {@code dictionary} for one time. Thus, let m be the size of the
 * {@code dictionary}, it takes ~ O(M) time. Thus, the overall time complexity:
 * O(M + N^2)
 * <p>
 * 2) Another solution will be using Trie, in this solution the Trie is a
 * R-Tree, not a three way Trie. Trie is better than using hash, since Trie
 * takes ~ O(W) time to find a match for a word, where W is the average length
 * of words in {@code dictionary}. In common situations, it doesn't need ~W
 * times comparison to find a word that doesn't exist. Thus, it's much faster,
 * but the time complexity, overall, are the same. Let M, be the size of the
 * {@code dictionary}, to construct a Trie, we take ~ O(M) time.
 * <p>
 * Before figuring out how a Trie helps find word matching, we should know that
 * a Trie is constructed by reveresed words. This is tricky, e.g., in normal
 * situation, when we only have a word 'apple', the Trie will root at 'a'.
 * Differently, for this problem, we construct Trie with reversed word, i.e.,
 * the Trie should be rooted at 'e' all the way to 'a'. Then, as we traverse the
 * {@code sentense}, say that we are at j. We consider char j as the possible
 * end of a word. E.g., 'e' in 'apple'. Then we starts backtracking (j -> 0) and
 * see if it's actually the end of a word. This essentially how a Trie is used
 * to match words.
 * <p>
 * Let N, be the size of the {@code sentense}, we will traverse the
 * {@code sentense} at least once, and as we are traversing, we backtrack in the
 * Trie starting at each char (say j), in worst case scenorio the Trie becomes
 * linear and it may traverse the whole Trie, thus O(N^2). The overall time
 * complexity is: O(M + N^2)
 * <p>
 * For the second difficulty, we consider how to record the previous results
 * such that we don't do repeatitive operation. I.e., how we break down the
 * problem in to sub-problems, and use Memorisation technique to record these
 * results, and finally combine them as a final solution.
 * <p>
 * Consider following example:
 * <p>
 * Dictionary: ['looked', 'just']
 * <p>
 * Sentense: 'jesslookedjust'
 * <p>
 * For 'j' we have 1 unmatched, 'e' -> 2, 's' -> 3, 's' -> 4, 'l' -> 5, 'o' ->
 * 6, 'o' -> 7, 'k' -> 8, 'e' -> 9
 * <p>
 * When we are at 'd', we treat 'd' as the end of a word (In fact this process
 * repeats for each char), we backtrack: 'd' => 'ed' => 'ked' ... => 'looked',
 * we find a match! Then the remaining will be 10 - 6 = 4.
 * <p>
 * E.g., for char at 9, we have result 4. I.e., f(9) = 4.
 * <p>
 * Let's continue, for next char 'j', we will have 5 remaining (excluding
 * 'looked'). We should again end at 't' which is supposed to have result 8, but
 * as we backtrack, we found a match 'just', thus, 8 - 4 = 4. Everything seems
 * okay so far, and it seems to be how the solution looks like.
 * <p>
 * However, lets consider following example:
 * <p>
 * Dictionary: ['see', 'seem']
 * <p>
 * Sentense: 'seem'
 * <p>
 * The result should be: 1200
 * <p>
 * But when we are at 'm', 1 - 4 = -3, it doesn't seem right. We should not use
 * subtraction, instead we should do following relation equation:
 * <p>
 * When we found a match at i, of length w:
 * <p>
 * 
 * <pre>
 * f(i) = MIN(f(i - w), f(i));
 * </pre>
 * 
 * A sentense can have multiple matches that overlaps, we find one with the
 * minimum number of remaining characters. We use a dp[] to stores these
 * results.
 * <p>
 * https://leetcode-cn.com/problems/re-space-lcci/
 * 
 */
public class ReSpaceLCCI {

    public int respace(String[] dictionary, String sentense) {
        int n = sentense.length();
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        Trie t = new Trie(dictionary);

        for (int i = 1; i <= n; i++) {
            // traversing in Trie
            dp[i] = dp[i - 1] + 1;
            Node curr = t.root;
            for (int j = i; j >= 1; j--) {
                int c = sentense.charAt(j - 1) - 'a';
                if (curr.next[c] == null)
                    break;

                if (curr.next[c].isEnd) {
                    dp[i] = Math.min(dp[i], dp[j - 1]);
                }

                if (dp[i] == 0)
                    break;
                curr = curr.next[c];
            }
        }
        // System.out.println(Arrays.toString(dp));
        return dp[n];
    }
}

class Trie {
    Node root;

    Trie(String[] dict) {
        for (String s : dict)
            root = add(root, s, s.length() - 1);
    }

    public Node add(Node r, String s, int j) {
        if (r == null) {
            r = new Node();
        }
        if (j == -1) {
            r.isEnd = true;
            return r;
        }
        int nv = s.charAt(j) - 'a';
        r.next[nv] = add(r.next[nv], s, j - 1);
        return r;
    }
}

class Node {
    // lowercase latters
    final int R = 26;
    Node[] next = new Node[R];
    boolean isEnd = false;
}
