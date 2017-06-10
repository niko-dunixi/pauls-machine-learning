package wtf.paulbaker.myai;

import org.junit.jupiter.api.Test;
import wtf.paulbaker.myai.impl.DefaultNeuralNet;
import wtf.paulbaker.myai.math.ActivationFunctions;
import wtf.paulbaker.myai.math.RandomSingletonProvider;

import java.util.List;
import java.util.function.Function;

import static java.util.Arrays.asList;
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
        List<Integer> hiddenLayerCounts = asList(3, 5);
        List<Function<Double, Double>> activationFunctions = asList(Sigmoid(1.0d), Sigmoid(1.0d));
        Function<Double, Double> outputFunction = ActivationFunctions.Linear(1d);

        NeuralNet neuralNet = new DefaultNeuralNet(inputCount, outputCount, hiddenLayerCounts, activationFunctions, outputFunction);

        assertThat(neuralNet.getHiddenLayerCount(), is(2));

        double[] inputs = {1.5, 0.5};
        neuralNet.setInputs(inputs);
        neuralNet.calculate();
        double[] outputs = neuralNet.getOutputs();
        assertThat(outputs[0], is(1.7720883618445207));

        inputs = new double[]{1, 2.1};
        neuralNet.setInputs(inputs);
        neuralNet.calculate();
        outputs = neuralNet.getOutputs();
        assertThat(outputs[0], is(1.7953157816231302));
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
