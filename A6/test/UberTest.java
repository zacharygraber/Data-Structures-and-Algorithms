import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UberTest {

    @Test
    public void scheduleSmall() throws EmptyListE {
        int numRides = 7;
        int maxLen = 25;
        int maxPrice = 10;
        List<Ride> rides = new Empty<>();

        Random rand = new Random(1);
        for (int i=0; i<numRides; i++) {
            Ride r = new Ride("r"+i, rand.nextInt(maxLen), rand.nextInt(maxPrice));
            rides = new Node<>(r,rides);
        }

        Pair<List<Ride>,Integer> s = Uber.schedule(rides,20);
        assertEquals(20,s.getSecond());
        var selectedRides = s.getFirst();
        assertEquals(3, selectedRides.length());
        assertEquals(8,selectedRides.getFirst().getPrice());
        selectedRides = selectedRides.getRest();
        assertEquals(4,selectedRides.getFirst().getPrice());
        selectedRides = selectedRides.getRest();
        assertEquals(8,selectedRides.getFirst().getPrice());
    }

    @Test
    public void scheduleBig() throws EmptyListE {
        int numRides = 200;
        int maxLen = 89;
        int maxPrice = 59;
        List<Ride> bigRides = new Empty<>();

        Random rand = new Random(1);
        for (int i=0; i<numRides; i++) {
            Ride r = new Ride("r"+i, rand.nextInt(maxLen), rand.nextInt(maxPrice));
            bigRides = new Node<>(r,bigRides);
        }

        Pair<List<Ride>,Integer> s = Uber.mschedule(bigRides,30);
        assertEquals(344,s.getSecond());
        var selectedRides = s.getFirst();
        assertEquals(8, selectedRides.length());
        assertEquals(53,selectedRides.getFirst().getPrice());
        selectedRides = selectedRides.getRest();
        assertEquals(17,selectedRides.getFirst().getPrice());
        selectedRides = selectedRides.getRest();
        assertEquals(33,selectedRides.getFirst().getPrice());
        selectedRides = selectedRides.getRest();
        assertEquals(57,selectedRides.getFirst().getPrice());
        selectedRides = selectedRides.getRest();
        assertEquals(41,selectedRides.getFirst().getPrice());
        selectedRides = selectedRides.getRest();
        assertEquals(52,selectedRides.getFirst().getPrice());
        selectedRides = selectedRides.getRest();
        assertEquals(58,selectedRides.getFirst().getPrice());
        selectedRides = selectedRides.getRest();
        assertEquals(33,selectedRides.getFirst().getPrice());
    }
}