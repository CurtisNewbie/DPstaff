import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * https://leetcode-cn.com/problems/longest-increasing-path-in-a-matrix/
 * <p>
 * 1. DFS + DP
 * <p>
 * 2. Topological Order + DP, using BFS to build Topological Order, and update
 * the longest increasing path at (i,j) with memo, while moving along the
 * topological order.
 */
public class LongestIncreasingPathInMatrix {
    public int[][] direction = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
    int n; // rows
    int m; // columns
    int[][] dp; // represent different things in two different methods

    /**
     * Using DFS to find the longest increasing starting at each (i, j). As we are
     * traversing using DFS, we update the dp[][] table along the path, such that we
     * don't need to use DFS for each block.
     * <p>
     * O(M*N), since we only find the path at each block for once.
     */
    public int longestIncreasingPathDFS(int[][] matrix) {
        n = matrix.length;
        if (n == 0)
            return 0;
        m = matrix[0].length;
        dp = new int[n][m]; // records the longest increasing path starting at matrix[i][j]

        int longest = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int path = walk(i, j, matrix);
                if (path > longest)
                    longest = path;
            }
        }
        // for(int[] row : dp)
        // System.out.println(Arrays.toString(row));
        return longest;
    }

    // DFS with DP
    private int walk(int i, int j, int[][] matrix) {
        if (dp[i][j] > 0)
            return dp[i][j];
        else {
            int curr = 0;
            for (int[] dir : direction) {
                int r = i + dir[0], c = j + dir[1];
                if (r >= 0 && r < n && c >= 0 && c < m && matrix[i][j] < matrix[r][c])
                    curr = Math.max(curr, walk(r, c, matrix));
            }
            ++curr; // including current block
            dp[i][j] = curr;
            return curr;
        }
    }

    /**
     * Convert (imagine) the matrix as a DAG. Since we are moving along the
     * increasing path, there won't be cycle. In DAG, we can create topogical order
     * to find the longest path.
     * <p>
     * Create Topological Order based on indegree (the number of directed path that
     * connects to current vertex). BFS is maintained by a queue. First, add all
     * vertices with zero indegree into the queue, and starts BFS, as we are
     * traversing, we subtract the indegree of all neighbour vertices by 1, and add
     * the zero indgree vertices into the queue.
     * <p>
     * As we are repeating such process, we maintain a dp[][] table, that records
     * the indegree of each (i, j). Since BFS traverses graph layer by layer, the
     * answer is the layers of BFS.
     * <p>
     * Also O(M*N), but the constants involved in all the operations for BFS
     * dramatically slow it down.
     */
    public int longestIncreasingPathTopo(int[][] mtx) {
        n = mtx.length;
        if (n == 0)
            return 0;
        m = mtx[0].length;
        dp = new int[n][m]; // indegree

        // vertex can be represented using int[]{i,j}
        Queue<Vertex> queue = new LinkedList<>();
        // find 0 indegree vertices
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                dp[i][j] = indegree(i, j, mtx);
                if (dp[i][j] == 0)
                    queue.offer(new Vertex(i, j));
            }
        }

        // BFS
        int levels = 0;
        while (!queue.isEmpty()) {
            ++levels;
            // per layer
            int layerSize = queue.size();
            for (int i = 0; i < layerSize; i++) {
                Vertex v = queue.poll();
                for (Vertex w : neighbours(v.r, v.c, mtx)) { // neighbours (with greater values) around v
                    if (mtx[v.r][v.c] < mtx[w.r][w.c]) {
                        // v->w
                        --dp[w.r][w.c]; // subtract indegree of neighbours by 1
                        if (dp[w.r][w.c] == 0) {
                            queue.offer(w);
                        }
                    }
                }
            }
        }
        return levels;
    }

    private int indegree(int i, int j, int[][] mtx) {
        int count = 0;
        for (Vertex w : neighbours(i, j, mtx)) {
            if (mtx[i][j] > mtx[w.r][w.c]) {
                ++count;
            }
        }
        return count;
    }

    private List<Vertex> neighbours(int i, int j, int[][] mtx) {
        ArrayList<Vertex> ws = new ArrayList<>(4);
        for (int[] dir : direction) {
            int r = i + dir[0], c = j + dir[1];
            if (r >= 0 && r < n && c >= 0 && c < m)
                ws.add(new Vertex(r, c));
        }
        return ws;
    }

    // for readability
    class Vertex {
        int r, c;

        Vertex(int i, int j) {
            r = i;
            c = j;
        }
    }
}