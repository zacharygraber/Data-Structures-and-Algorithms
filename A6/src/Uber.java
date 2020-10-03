import java.util.Map;
import java.util.WeakHashMap;

public class Uber {

    // Each ride has a length of time and a price.

    // We are given a list of rides and a max length of time. We would like to
    // maximize the price without exceeding the available time. In other
    // words, we want to select the most expensive rides whose total length is
    // no more than the time we have.

    // When considering ride i, it might be possible that two solutions of
    // equal cost can be produced where one solution include ride-i and the other
    // does not include ride-i. In case of such ties, report the solution
    // that does include ride-i

    static Pair<List<Ride>, Integer> schedule(List<Ride> rides, int maxLength) {
        // TODO. Solution goes here
        return null;
    }

    // TODO. Top-down solution (mschedule) goes here

    // TODO. Bottom-up solution (buschedule) goes here
}
