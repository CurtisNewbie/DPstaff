import java.util.*;

public class LargestBorderedSquare {

    // naive O(N^3) approach
    public int naiveLargest1BorderedSquare(int[][] grid) {
        int row = grid.length;
        if (row == 0)
            return 0;
        int col = grid[0].length;
        boolean valid = true;
        int maxLen = -1;
        // O(N^3)
        for (int len = 0; len < row; len++) {
            // top-left corner
            for (int r = 0; r + len < row; r++) {
                for (int c = 0; c + len < col; c++) {
                    // top
                    for (int j = c; valid && j <= len + c; j++) {
                        if (grid[r][j] == 0) {
                            valid = false;
                        }
                    }
                    // bottom
                    for (int j = c; valid && j <= len + c; j++) {
                        if (grid[r + len][j] == 0) {
                            valid = false;
                        }
                    }
                    // left
                    for (int j = r; valid && j <= len + r; j++) {
                        if (grid[j][c] == 0) {
                            valid = false;
                        }
                    }
                    // right
                    for (int j = r; valid && j <= len + r; j++) {
                        if (grid[j][c + len] == 0) {
                            valid = false;
                        }
                    }
                    if (valid) {
                        maxLen = len > maxLen ? len : maxLen;
                        // System.out.printf("Len:%d, r:%d, c:%d\n", len, r, c);
                    }
                    // reset
                    valid = true;
                }
            }
        }
        ++maxLen;
        return maxLen * maxLen;
    }

    public int largest1BorderedSquare(int[][] grid) {
        int row = grid.length;
        if (row == 0)
            return 0;
        int col = grid[0].length;

        // records the max contingous 1 from left to right for each row
        int[][] dpToRight = new int[row][col];
        // records the max continguous 1 from top to bottom for each col
        int[][] dpToBtm = new int[row][col];

        int max = 0;
        if (grid[0][0] == 1) {
            dpToRight[0][0] = 1;
            dpToBtm[0][0] = 1;
            max = 1;
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] > 0) {
                    dpToRight[i][j] = 1 + (j > 0 ? dpToRight[i][j - 1] : 0);
                    dpToBtm[i][j] = 1 + (i > 0 ? dpToBtm[i - 1][j] : 0);
                } else {
                    dpToRight[i][j] = 0;
                    dpToBtm[i][j] = 0;
                }
            }
        }
        // Understand this operation is the key to solve the problem
        // Recall that:
        // dpToRight[i][j] records the number of continguous 1 from grid[i][j]'s left
        // and dpToBtm[i][j] records the number of continguous 1 from grid[i][j]'s top
        // so, it's very likely that dpToRight[i][j] != dpToBtm[i][j]
        //
        // e.g.,
        // 1 1 1 1
        // 0 1 1 1 => let i = 2, j = 3 , dpToRight[i][j] = 4, dpToBtm[i][j] = 3
        // 1 1 1 1
        // We pick the minimum one among them (len), and attempt to verify that
        // it's actually a square where all sides are 1.
        //
        // let len <- MIN(dpToRight[i][j], dpToBtm[i][j])
        //
        // for dpToRight[i][j], we compare with one on the top, dpToRight[i-len+1][j]
        // for dpToBtm[i][j], we compare with one on the left, dpToBtm[i][j-len+1]
        // Note that grid[i][j] is the bottom-right cornor of the square, and
        // Using dpToRight, we check all the rows in the square with the len
        // Using dpToBtm, we check all the col in the square with the len
        //
        // E.g., imagine, when we decide to use grid[i][j], we know that:
        // the row: grid[i][j-len+1] => grid[i][j] is valid
        // and
        // the col: grid[i-len+1][j] => grid[i][j] is also valid
        //
        // when we verify that dpToBtm[i][j-len+1] >= len
        // we actually verify that:
        // the col: grid[i-len+1][j-len+1] => grid[i][j-len+1] is valid
        //
        // when we very that dpToRight[i-len+1][j] >= len
        // we actually verify that:
        // the row: grid[i-len+1][j-len+1] => grid[i-len+1][j] is valid
        //
        // To conclude: four edges for a square of len whose bottom-right cornor
        // is located at grid[i][j] are verified.
        int len;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {

                if (grid[i][j] == 1) {
                    len = Math.min(dpToRight[i][j], dpToBtm[i][j]);
                    while (dpToBtm[i][j - len + 1] < len || dpToRight[i - len + 1][j] < len) {
                        len--;
                    }
                    max = Math.max(max, len);
                }
            }
        }
        for (int[] r : dpToRight)
            System.out.println(Arrays.toString(r));
        System.out.println();
        for (int[] r : dpToBtm)
            System.out.println(Arrays.toString(r));
        return max * max;
    }
}