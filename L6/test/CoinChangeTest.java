import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoinChangeTest {
    List<Integer> coins, weirdCoins;

    @BeforeEach
    void setup() {
        coins = new NonEmptyList<>(25,
                new NonEmptyList<>(10,
                        new NonEmptyList<>(5,
                                new NonEmptyList<>(1,
                                        new EmptyList<>()))));
        weirdCoins = new NonEmptyList<>(30,
                new NonEmptyList<>(11,
                        new NonEmptyList<>(7,
                                new NonEmptyList<>(3,
                                        new EmptyList<>()))));
    }
    @Test
    void greedyCoinChange() {
        assertEquals(9, CoinChange.greedyCoinChange(coins, 94));
        assertEquals(4, CoinChange.greedyCoinChange(coins, 28));
        assertEquals(2, CoinChange.greedyCoinChange(weirdCoins, 41));
    }

    @Test
    void memoCoinChange() {
        assertEquals(9, CoinChange.memoCoinChange(coins, 94));
        assertEquals(4, CoinChange.memoCoinChange(coins, 28));
        assertEquals(2, CoinChange.memoCoinChange(weirdCoins, 41));
    }

    @Test
    void bupCoinChange() {
        assertEquals(9, CoinChange.bupCoinChange(coins, 94));
        assertEquals(4, CoinChange.bupCoinChange(coins, 28));
        assertEquals(2, CoinChange.bupCoinChange(weirdCoins, 41));
    }
}