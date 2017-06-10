package wtf.paulbaker.myai;

import org.junit.jupiter.api.Test;
import wtf.paulbaker.myai.impl.DefaultNeuralNet;
import wtf.paulbaker.myai.math.ActivationFunctions;
import wtf.paulbaker.myai.math.RandomSingletonProvider;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static wtf.paulbaker.myai.math.ActivationFunctions.Sigmoid;

/**
 * Created by paul.baker on 6/8/17.
 */
public class NeuralNetTest {

    @Test
    public void testFunctionalWithHiddenLayers() {
        RandomSingletonProvider.setRandomSeed(0);

        int inputCount = 2, outputCount = 1;
        List<Integer> hiddenLayerCounts = Arrays.asList(3);
        List<Function<Double, Double>> activationFunctions = Arrays.asList(Sigmoid(1.0d));
        Function<Double, Double> outputFunction = ActivationFunctions.Linear(1d);

        NeuralNet neuralNet = new DefaultNeuralNet(inputCount, outputCount, hiddenLayerCounts, activationFunctions, outputFunction);

        assertThat(neuralNet.getHiddenLayerCount(), is(1));

        double[] inputs = {1.5, 0.5};
        neuralNet.setInputs(inputs);
        neuralNet.calculate();
        double[] outputs = neuralNet.getOutputs();
        assertThat(outputs[0], is(0.44921607391601426));

        inputs = new double[]{1, 2.1};
        neuralNet.setInputs(inputs);
        neuralNet.calculate();
        outputs = neuralNet.getOutputs();
        assertThat(outputs[0], is(0.4722421543930707));
    }

    @Test
    public void testSingleLayer() {
        RandomSingletonProvider.setRandomSeed(0);

        int inputCount = 2, outputCount = 1;
        Function<Double, Double> outputFunction = ActivationFunctions.Linear(1d);

        NeuralNet neuralNet = new DefaultNeuralNet(inputCount, outputCount, outputFunction);

        assertThat(neuralNet.hasHiddenLayers(), is(false));

        double[] inputs = {1.5, 0.5};
        neuralNet.setInputs(inputs);
        neuralNet.calculate();
        double[] outputs = neuralNet.getOutputs();
        assertEquals(1.1373519143541104, outputs[0]);
    }
}
