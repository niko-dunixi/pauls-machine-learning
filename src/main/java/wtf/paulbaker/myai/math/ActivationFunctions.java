package wtf.paulbaker.myai.math;

import java.util.function.Function;

import static java.lang.Math.exp;

/**
 * Created by paul.baker on 4/4/17.
 * <p>
 * Functional or classic method calling. Pick your poison.
 */
public class ActivationFunctions {

    public static final Function<Double, Double> Sigmoid = Sigmoid(1);

    public static final Function<Double, Double> HyperbolicTangent = HyperbolicTangent(1);

    public static final Function<Double, Double> HardLimitingThreshold = HardLimitingThreshold(0);

    public static final Function<Double, Double> Linear = Linear(1);

    public static final Function<Double, Double> Sigmoid(double a) {
        return (x) -> 1 / (1 + exp(-x * a));
    }

    public static final Function<Double, Double> HyperbolicTangent(double a) {
        return (x) -> (1 - exp(-x * a)) / (1 + exp(-x * a));
    }

    public static Function<Double, Double> HardLimitingThreshold(double threshold) {
        return (x) -> {
            if (x < threshold) {
                return 0D;
            }
            return 1D;
        };
    }

    public static final Function<Double, Double> Linear(double a) {
        return Linear(a, 0);
    }

    public static final Function<Double, Double> Linear(double a, double b) {
        return (x) -> a * x + b;
    }

    /**
     * http://nn.cs.utexas.edu/downloads/papers/stanley.ec02.pdf page 112
     */
    public static final Function<Double, Double> ModifiedSigmoid = Sigmoid(4.9D);

    /**
     * Don't ask me where SethBling got this from. I have no idea. It doesn't match the cited paper.
     * <p>
     * It's effectively the same as the {@link #ModifiedSigmoid}, except multiplied by 2 and then add 1
     */
    public static final Function<Double, Double> SethBlingSigmoid = (x) -> 2 / (1 + exp(-4.9 * x)) + 1;

    /**
     * We want only readonly static constants. No initialization for you!
     */
    private ActivationFunctions() {
    }
}
