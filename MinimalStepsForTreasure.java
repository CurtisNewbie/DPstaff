import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * https://leetcode-cn.com/problems/xun-bao/
 */
public class MinimalStepsForTreasure {

    static final int[][] direction = {{1, 0}, {-1, 0}, {0, -1}, {0, 1}};

    public int minimalSteps(String[] maze) {
        int n = maze.length;
        int m = maze[0].length();

        Vertex end = null; // end
        Vertex start = null; // start
        List<Vertex> buttonSites = new ArrayList<>();
        List<Vertex> stoneSites = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                Vertex v = toVertex(i, j, maze);
                if (v.c == 'O')
                    stoneSites.add(v);
                else if (v.c == 'S')
                    start = v;
                else if (v.c == 'T')
                    end = v;
                else if (v.c == 'M')
                    buttonSites.add(v);
            }
        }

        int buttons = buttonSites.size();
        int stones = stoneSites.size();
        int[][] distFromStart = shortestPath(start.i, start.j, maze);

        if (buttons == 0) { // no button at all
            // start -> end
            return shortestPath(start.i, start.j, maze)[end.i][end.j];
        }

        // min dist from each buttonSite to another buttonSite or start or end
        int[][] dist = new int[buttons][buttons + 2];
        for (int i = 0; i < buttons; i++)
            Arrays.fill(dist[i], -1);

        // all BFS results starting at each button
        int[][][] distFromBtns = new int[buttons][][];
        for (int i = 0; i < buttons; i++) {
            Vertex btn = buttonSites.get(i);
            distFromBtns[i] = shortestPath(btn.i, btn.j, maze);
            dist[i][buttons + 1] = distFromBtns[i][end.i][end.j];
        }

        for (int i = 0; i < buttons; i++) {
            int tmp = -1;
            for (int k = 0; k < stones; k++) {
                Vertex stone = stoneSites.get(k);
                int iBtnToStone = distFromBtns[i][stone.i][stone.j];
                int startToStone = distFromStart[stone.i][stone.j];
                // dist of buttonSite -> stoneSite -> start
                // where stone is the one at the middle
                if (iBtnToStone != -1 && startToStone != -1) {
                    int pathDist = iBtnToStone + startToStone;
                    if (tmp == -1 || tmp > pathDist) {
                        tmp = pathDist;
                    }
                }
            }
            dist[i][buttons] = tmp;
            for (int j = i + 1; j < buttons; j++) {
                int mn = -1;
                for (int k = 0; k < stones; k++) {
                    Vertex stone = stoneSites.get(k);
                    int iBtnToStone = distFromBtns[i][stone.i][stone.j];
                    int jBtnToStone = distFromBtns[j][stone.i][stone.j];
                    // dist of buttonSite[i] -> stoneSite -> buttonSite[j]
                    // where stone is the one at the middle
                    if (iBtnToStone != -1 && jBtnToStone != -1) {
                        int pathDist = iBtnToStone + jBtnToStone;
                        if (mn == -1 || mn > pathDist) {
                            mn = pathDist;
                        }
                    }
                }
                dist[i][j] = mn;
                dist[j][i] = mn;
            }
        }

        for (int i = 0; i < buttons; i++) {
            if (dist[i][buttons] == -1 || dist[i][buttons + 1] == -1) {
                return -1;
            }
        }

        int[][] dp = new int[1 << buttons][buttons];
        for (int i = 0; i < 1 << buttons; i++) {
            Arrays.fill(dp[i], -1);
        }
        for (int i = 0; i < buttons; i++) {
            dp[1 << i][i] = dist[i][buttons];
        }

        for (int mask = 1; mask < (1 << buttons); mask++) {
            for (int i = 0; i < buttons; i++) {
                if ((mask & (1 << i)) != 0) {
                    for (int j = 0; j < buttons; j++) {
                        if ((mask & (1 << j)) == 0) {
                            int next = mask | (1 << j);
                            if (dp[next][j] == -1 || dp[next][j] > dp[mask][i] + dist[i][j]) {
                                dp[next][j] = dp[mask][i] + dist[i][j];
                            }
                        }
                    }
                }
            }
        }

        int ret = -1;
        int finalMask = (1 << buttons) - 1;
        for (int i = 0; i < buttons; i++) {
            if (ret == -1 || ret > dp[finalMask][i] + dist[i][buttons + 1]) {
                ret = dp[finalMask][i] + dist[i][buttons + 1];
            }
        }
        return ret;
    }

    // BFS starting at (i, j)
    private int[][] shortestPath(int i, int j, String[] maze) {
        int n = maze.length;
        int m = maze[0].length();

        int[][] distances = new int[n][m];
        for (int r = 0; r < n; r++) {
            Arrays.fill(distances[r], -1);
        }
        distances[i][j] = 0;
        Queue<Vertex> queue = new LinkedList<>();
        queue.offer(toVertex(i, j, maze));
        while (!queue.isEmpty()) {
            Vertex v = queue.poll();
            for (Vertex w : neighbours(v, n, m, maze)) {
                if (distances[w.i][w.j] == -1) { // not seemed yet
                    if (w.c != '#') { // not a wall
                        queue.offer(w);
                        distances[w.i][w.j] = distances[v.i][v.j] + 1;
                    }
                }
            }
        }
        return distances;
    }

    private Iterable<Vertex> neighbours(Vertex v, int n, int m, String[] maze) {
        List<Vertex> nei = new ArrayList<>();
        for (int[] dir : direction) {
            int row = v.i + dir[0];
            int col = v.j + dir[1];
            if (row >= 0 && row < n && col >= 0 && col < m) {
                nei.add(toVertex(row, col, maze));
            }
        }
        return nei;
    }

    private Vertex toVertex(int i, int j, String[] maze) {
        return new Vertex(i, j, at(i, j, maze));
    }

    private char at(int i, int j, String[] maze) {
        return maze[i].charAt(j);
    }

    class Vertex {
        char c; // '.', 'M', '#', 'S', 'O'
        int i;
        int j;

        Vertex(int i, int j, char c) {
            this.i = i;
            this.j = j;
            this.c = c;
        }
    }
}
