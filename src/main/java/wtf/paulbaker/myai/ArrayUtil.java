package wtf.paulbaker.myai;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paul.baker on 6/8/17.
 */
public class ArrayUtil {
    private ArrayUtil() {
    }

    public static List<Double> emptyDoubleListOfSize(int size) {
        List<Double> doubles = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            doubles.add(0d);
        }
        return doubles;
    }

    public static <T> List<T> emptyListOfSize(int size) {
        List<T> values = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            values.add(null);
        }
        return values;
    }
}
