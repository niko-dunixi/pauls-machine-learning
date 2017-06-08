package wtf.paulbaker.myai.math;

import java.util.Random;

/**
 * Created by paul.baker on 6/8/17.
 * <p>
 * This is not thread safe. Don't do anything stupid with it.
 */
public class RandomSingletonProvider {

    private static Random RANDOM = new Random();

    private RandomSingletonProvider() {
    }

    public static Random getInstance() {
        return RANDOM;
    }

    public static void setRandomSeed(long seed) {
        RANDOM = new Random(seed);
    }
}
