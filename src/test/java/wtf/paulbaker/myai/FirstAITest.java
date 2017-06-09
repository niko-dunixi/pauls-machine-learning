package wtf.paulbaker.myai;

import org.junit.jupiter.api.Test;
import wtf.paulbaker.myai.impl.DefaultNeuralNet;
import wtf.paulbaker.myai.math.ActivationFunctions;
import wtf.paulbaker.myai.math.RandomSingletonProvider;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

        NeuralNet neuralNet = new DefaultNeuralNet(inputCount, outputCount, hiddenLayerCounts, activationFunctions, outputFunction);

        double[] inputs = {1.5, 0.5};
        neuralNet.setInputs(inputs);
        neuralNet.calculate();
        double[] outputs = neuralNet.getOutputs();
        assertEquals(0.44921607391601426, outputs[0]);

        inputs = new double[]{1, 2.1};
        neuralNet.setInputs(inputs);
        neuralNet.calculate();
        outputs = neuralNet.getOutputs();
        assertEquals(0.4722421543930707, outputs[0]);
    }

    @Test
    public void testSingleLayer() {
        RandomSingletonProvider.setRandomSeed(0);

        int inputCount = 2, outputCount = 1;
        Function<Double, Double> outputFunction = ActivationFunctions.Linear(1d);

        NeuralNet neuralNet = new DefaultNeuralNet(inputCount, outputCount, outputFunction);

        double[] inputs = {1.5, 0.5};
        neuralNet.setInputs(inputs);
        neuralNet.calculate();
        double[] outputs = neuralNet.getOutputs();
        assertEquals(1.1373519143541104, outputs[0]);
    }
}
