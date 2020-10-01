public class Ride {
    private String name;
    private int length;
    private int price;

    Ride (String name, int length, int price) {
        this.name = name;
        this.length = length;
        this.price = price;
    }

    int getLength () { return length; }
    int getPrice () { return price; }

    public boolean equals (Object o) {
        if (o instanceof Ride) {
            Ride other = (Ride) o;
            return other.name.equals(name);
        }
        return false;
    }

    public int hashCode () {
        return name.hashCode();
    }

    public String toString () {
        return String.format("%s[L%d$%d]", name, length, price);
    }
}

