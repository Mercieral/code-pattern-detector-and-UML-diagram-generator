package puzzle;
public class Creator {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            @SuppressWarnings("unused")
			Creature creature = new Creature();
        }
        System.out.println(Creature.numCreated());
    }
}

class Creature {
    private static long numCreated = 0;

    public Creature() {
        numCreated++;
    }

    public static long numCreated() {
        return numCreated;
    }
}
