import java.util.Arrays;
import java.util.Map;
import java.util.WeakHashMap;

class CoinChange {
    /**
     * Uses a greedy algorithm to return the lowest number of coins needed to make change.
     * @param coins The set of all coin values, sorted from highest to lowest.
     * @param target The target change amount.
     * @return The minimum coins needed to make change. Return Integer.MAX_VALUE if not possible.
     */
    static int greedyCoinChange(List<Integer> coins, int target) {
        try {
            int numCoins = 0;
            int remainingBalance = target;

            // We loop until there is no more remaining balance to pay out
            while (remainingBalance > 0) {
                // If this coin works, then we use it and increment the number of coins used
                if (coins.getFirst() <= remainingBalance) {
                    remainingBalance -= coins.getFirst();
                    numCoins++;
                }
                // Otherwise, try the next smallest coin.
                else {
                    coins = coins.getRest();
                }
            }
            return numCoins;
        }
        // If we run out of coins, we'll get this exception, and return the max value.
        catch (EmptyListE e) {
            return Integer.MAX_VALUE;
        }
    }

    static Map<Pair<List<Integer>, Integer>, Integer> coinChangeMemo = new WeakHashMap<>();
    /**
     * Uses top-down dynamic programming to return the lowest number of coins needed to make change. Map is found above.
     * @param coins The set of all coin values, sorted from highest to lowest.
     * @param target The target change amount.
     * @return The minimum coins needed to make change. Return Integer.MAX_VALUE if not possible.
     */
    static int memoCoinChange(List<Integer> coins, int target) {
        return coinChangeMemo.computeIfAbsent(new Pair<>(coins,target), p->{
            try {
                // Base case
                if (target == coins.getFirst()) {
                    return 1;
                }
                else if (target < coins.getFirst()) {
                    return memoCoinChange(coins.getRest(), target);
                }

                // case a represents using this coin, whereas case b is the option to use the next coin instead
                int a = memoCoinChange(coins, target - coins.getFirst());
                int b = memoCoinChange(coins.getRest(), target);
                return (a < b) ? a : b; // return the smaller of the two
            }
            catch (EmptyListE e) {
                return Integer.MAX_VALUE;
            }
        });
    }

    /**
     * Use bottom-up dynamic programming to return the lowest number of coins needed to make change.
     * @param coins The set of all coin values, sorted from highest to lowest.
     * @param target The target change amount.
     * @return The minimum coins needed to make change. Return Integer.MAX_VALUE if not possible.
     */
    static int bupCoinChange(List<Integer> coins, int target) {
      // TODO
        return 0;
    }
}
