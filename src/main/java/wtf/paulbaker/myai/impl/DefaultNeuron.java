package wtf.paulbaker.myai.impl;

import lombok.NonNull;
import wtf.paulbaker.myai.Neuron;
import wtf.paulbaker.myai.math.RandomSingletonProvider;

import java.util.Random;
import java.util.function.Function;

/**
 * Created by paul.baker on 6/8/17.
 */
public class DefaultNeuron implements Neuron {

    private final int inputCount;
    private final double[] weights;
    private final double[] inputs;
    private final Function<Double, Double> activationFunction;
    private double bias;

    private double currentOutput;

    public DefaultNeuron(int inputCount, Function<Double, Double> activationFunction) {
        this.inputCount = inputCount;
        weights = new double[inputCount];
        inputs = new double[inputCount];
        this.activationFunction = activationFunction;

        initializeNeuron();
    }

    @Override
    public void initializeNeuron() {
        Random random = RandomSingletonProvider.getInstance();

        bias = 0;
        currentOutput = 0;

        for (int i = 0; i < inputCount; i++) {
            double randomWeight = random.nextDouble();
            weights[i] = randomWeight;
        }
    }

    @Override
    public void setInputs(@NonNull double[] inputs) {
        if (inputs.length != this.inputs.length) {
            throw new IllegalArgumentException("Wrong number of inputs. Was " + inputs.length
                    + " but should have been " + this.inputs.length);
        }

        for (int i = 0; i < inputs.length; i++) {
            setInputAtIndex(i, inputs[i]);
        }
    }

    @Override
    public void setInputAtIndex(int index, double input) {
        inputs[index] = input;
    }

    @Override
    public void calculateOutput() {
        double temp = 0d;
        for (int i = 0; i < inputs.length; i++) {
            temp += inputs[i] * weights[i];
        }
        temp += bias;

        currentOutput = activationFunction.apply(temp);
    }

    @Override
    public double getCurrentOutput() {
        return currentOutput;
    }


}
