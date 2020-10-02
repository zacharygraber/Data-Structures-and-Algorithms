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
      // TODO
    }

    static Map<Pair<List<Integer>, Integer>, Integer> coinChangeMemo = new WeakHashMap<>();
    /**
     * Uses a greedy algorithm to return the lowest number of coins needed to make change. Map is found above.
     * @param coins The set of all coin values, sorted from highest to lowest.
     * @param target The target change amount.
     * @return The minimum coins needed to make change. Return Integer.MAX_VALUE if not possible.
     */
    static int memoCoinChange(List<Integer> coins, int target) {
      // TODO
    }

    /**
     * Use bottom-up dynamic programming to return the lowest number of coins needed to make change.
     * @param coins The set of all coin values, sorted from highest to lowest.
     * @param target The target change amount.
     * @return The minimum coins needed to make change. Return Integer.MAX_VALUE if not possible.
     */
    static int bupCoinChange(List<Integer> coins, int target) {
      // TODO
    }
}
