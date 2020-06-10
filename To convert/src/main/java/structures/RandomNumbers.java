package structures;

public final class RandomNumbers {
    private static java.util.Random r;

    public static int nextNumber() {
        if (r == null)
            seed();

        return r.nextInt();
    }

    public static void seed() {
        r = new java.util.Random();
    }


}