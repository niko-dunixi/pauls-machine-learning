package wtf.paulbaker.myai;

import org.junit.jupiter.api.Test;
import wtf.paulbaker.myai.math.ActivationFunctions;
import wtf.paulbaker.myai.math.RandomSingletonProvider;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static wtf.paulbaker.myai.math.ActivationFunctions.Sigmoid;

/**
 * Created by paul.baker on 6/8/17.
 */
public class FirstAITest {

    @Test
    public void testItFirst() {
        RandomSingletonProvider.setRandomSeed(0);

        int inputCount = 2, outputCount = 1;
        List<Integer> hiddenLayerCounts = Arrays.asList(3);
        List<Function<Double, Double>> activationFunctions = Arrays.asList(Sigmoid(1.0d));
        Function<Double, Double> outputFunction = ActivationFunctions.Linear(1d);

        NeuralNet neuralNet = new NeuralNet(inputCount, outputCount, hiddenLayerCounts, activationFunctions, outputFunction);

        double[] inputs = {1.5, 0.5};
        neuralNet.setInputs(inputs);

        neuralNet.calculate();
        List<Double> outputs = neuralNet.getOutputs();

        System.out.println(outputs);
    }
}
