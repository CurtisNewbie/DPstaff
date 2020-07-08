/**
 * https://leetcode-cn.com/problems/diving-board-lcci/
 */
public class DivingBoardLCCI {

    public int[] divingBoard(int shorter, int longer, int k) {
        if (k == 0)
            return new int[k];
        if (shorter == longer)
            return new int[] { shorter * k };

        // First of all, the result must be sum of k boards regardless of
        // which those boards are, shorter or longer.
        // The point is that we are looking for unique combination
        // of k boards. Since shorter < longer, when we replace
        // a shorter with longer, it's guaranteed that resulting
        // board is different, vice versa.
        //
        // So, we starts with k shorter boards (or longer, it doesn't
        // matter). And we start to replace the shorter with longer board
        // one by one. Then, we will have K+1 unique results, where the
        // extra one is all shorter boards.
        int[] boards = new int[k + 1];
        int bp = -1;
        boards[++bp] = shorter * k;
        int diff = longer - shorter;
        for (int i = 0; i < k; i++) {
            boards[++bp] = boards[bp - 1] + diff;
        }
        return boards;
    }
}