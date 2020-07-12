/**
 * https://leetcode-cn.com/problems/dungeon-game/
 */
public class DungeonGame {

    /**
     * Since we are asking what the minimum hp is for each dungeon, without knowing
     * what the initial hp is, we know that we must backtrack to find the solution
     * from the end towards the beginning.
     * <p>
     * For example, we need to arrive the bottom-right dungeon and save the
     * princess, we know that when we are at the bottom-right dungeon, we must have
     * at least 1 hp. So we can calculate the minimum hp for the last dungeon is:
     * 
     * <pre>
     * minHp = dungeon[row - 1][col - 1] < 0 ? -dungeon[row - 1][col - 1] + 1 : 1;
     * </pre>
     * 
     * Starting from here, we can be from top or left. When the top or left dungeon,
     * gives us way too much health, the minimum hp for such dungeon will be 1. If
     * such extra health is less than the minimum hp for current dungeon, then we
     * subtract the extra health with our minimum hp needed for current dungeon.
     * <p>
     * E.g., if the minHp for current dungeon[i][j] is 5hp, and the one on top
     * (dungeon[i-1][j]) gives us 3hp, then, the min hp for dungeon[i-1][j] will be
     * 2, since we gain the 3 extra hp on this dungeon.
     * <p>
     * However, if the minHp for current dungeon[i][j] is 5hp, but the one on top
     * (dungeon[i-1][j]) gives us 7hp, which is way too much, then we only need to
     * be alive to move to dungeon[i][j]. So, the min hp for dungeon[i-1][j] will be
     * 1hp only. Then we have:
     *
     * <pre>
     *LET prevHp <- hp[i][j]
     *LET dungeonOnTop <- dungeon[i-1][j]
     *
     *IF dungeonOnTop > 0 
     *   hp[i-1][j] = prevHp > dungeonOnTop ? prevHp - dungeonOnTop : 1;
     *ELSE IF (dungeonOnTop < 0) 
     *   hp[i-1][j] = prevHp - dungeonOnTop;
     *ELSE 
     *   hp[i-1][j] = prevHp;
     *
     * </pre>
     * <p>
     * For some dungeons, we only consider moving left or top (base cases). However,
     * most of the dungeons can be point where two possible paths meet. For these
     * dungeons, we find the minimum health needed between the two paths.
     */
    public int calculateMinimumHP(int[][] dungeon) {
        int r = dungeon.length;
        int c = dungeon[0].length;
        // minimum hp needed for moving to dungeon[i][j]
        int[][] hp = new int[dungeon.length][dungeon[0].length];

        // base cases, last dungeon (bottom-right), last row and last column
        hp[r - 1][c - 1] = dungeon[r - 1][c - 1] < 0 ? -dungeon[r - 1][c - 1] + 1 : 1;
        for (int i = r - 2; i >= 0; i--) {
            hp[i][c - 1] = calMinHp(hp[i + 1][c - 1], dungeon[i][c - 1]);
        }
        for (int i = c - 2; i >= 0; i--) {
            hp[r - 1][i] = calMinHp(hp[r - 1][i + 1], dungeon[r - 1][i]);
        }
        for (int i = r - 2; i >= 0; i--) {
            for (int j = c - 2; j >= 0; j--) {
                if (dungeon[i][j] > 0) {
                    if (hp[i + 1][j] > dungeon[i][j] && hp[i][j + 1] > dungeon[i][j])
                        hp[i][j] = Math.min(hp[i + 1][j] - dungeon[i][j], hp[i][j + 1] - dungeon[i][j]);
                    else
                        hp[i][j] = 1;
                } else if (dungeon[i][j] < 0) {
                    hp[i][j] = Math.min(hp[i + 1][j] - dungeon[i][j], hp[i][j + 1] - dungeon[i][j]);
                } else {
                    hp[i][j] = Math.min(hp[i + 1][j], hp[i][j + 1]);
                }
            }
        }
        // for(int[] row : hp)
        // System.out.println(Arrays.toString(row));
        return hp[0][0];
    }

    private int calMinHp(int prevHp, int dungeon) {
        if (dungeon > 0) {
            return prevHp > dungeon ? prevHp - dungeon : 1;
        } else if (dungeon < 0) {
            return prevHp - dungeon;
        } else {
            return prevHp;
        }
    }
}