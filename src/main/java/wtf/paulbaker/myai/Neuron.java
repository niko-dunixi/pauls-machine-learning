package wtf.paulbaker.myai;

import lombok.NonNull;
import wtf.paulbaker.myai.math.RandomSingletonProvider;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import static wtf.paulbaker.myai.ArrayUtil.emptyDoubleListOfSize;

/**
 * Created by paul.baker on 6/8/17.
 */
public class Neuron implements Serializable {

    private final int inputCount;
    private final List<Double> weights;
    private final List<Double> inputs;
    private final Function<Double, Double> activationFunction;
    private double bias;

    private double currentOutput;

    public Neuron(int inputCount, Function<Double, Double> activationFunction) {
        this.inputCount = inputCount;
        weights = emptyDoubleListOfSize(inputCount);
        inputs = emptyDoubleListOfSize(inputCount);
        this.activationFunction = activationFunction;

        initializeNeuron();
    }

    public void initializeNeuron() {
        Random random = RandomSingletonProvider.getInstance();

        bias = 0;
        currentOutput = 0;

        for (int i = 0; i < inputCount; i++) {
            double randomWeight = random.nextDouble();
            weights.set(i, randomWeight);
        }
    }

    public void setInputs(@NonNull double[] values) {
        this.setInputs(Arrays.stream(values).boxed().collect(Collectors.toList()));
    }

    public void setInputs(@NonNull List<Double> values) {
        if (values.size() != inputs.size()) {
            throw new IllegalArgumentException("Wrong number of inputs. Was " + values.size() + " but should have been " + inputs.size());
        }
        for (int i = 0; i < values.size(); i++) {
            inputs.set(i, values.get(i));
        }
    }

    public void setInputAtIndex(double input, int index) {
        inputs.set(index, input);
    }

    public void calculateOutput() {
        double temp = 0d;
        for (int i = 0; i < inputs.size(); i++) {
            temp += inputs.get(i) * weights.get(i);
        }
        temp += bias;

        currentOutput = activationFunction.apply(temp);
    }

    public double getCurrentOutput() {
        return currentOutput;
    }


}
