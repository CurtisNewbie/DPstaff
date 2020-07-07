/**
 * https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock/
 */
public class StockProblem {

    public int maxProfit(int[] prices) {
        int n = prices.length;
        if (n == 0 || n == 1)
            return 0;
        // let i be the day we buy
        // let j be the day we sell
        // we can observe that:
        // 1. i must be less than j
        // 2. nums[i] must also be less than nums[j], such that we have profit.
        // Then the goal will be looking for min as i and max as j, such that
        // indices: i < j. (We cannot sell before we buy stock)

        // To achieve this, we maintain a minPrice and a maxProfit.
        // minPrice is the min in the part that we have seen so far
        // when we traverse prices[], for prices[i] we compares it with minPrice,
        // if prices[i] > minPrice, which means we are at the day after minPrice,
        // and we can make profit. Based on this, we update maxProfit throughout
        // the process
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;
        int profit;
        for (int i = 0; i < n; i++) {
            if (prices[i] < minPrice)
                minPrice = prices[i];
            else {
                profit = prices[i] - minPrice;
                if (profit > maxProfit)
                    maxProfit = profit;
            }
        }
        return maxProfit;
    }
}