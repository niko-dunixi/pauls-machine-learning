package wtf.paulbaker.myai.impl;

import wtf.paulbaker.myai.NeuralLayer;
import wtf.paulbaker.myai.NeuralNet;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Collections.emptyList;

/**
 * Created by paul.baker on 6/8/17.
 */
public class DefaultNeuralNet implements NeuralNet {

    private NeuralLayer inputLayer, outputLayer;

    private double[] inputs, outputs;

    private int hiddenLayerCount;

    public DefaultNeuralNet(int inputCount, int outputCount, Function<Double, Double> outputActivationFunction) {
        this(inputCount, outputCount, emptyList(), emptyList(), outputActivationFunction);
    }

    public DefaultNeuralNet(int inputCount, int outputCount,
                            List<Integer> hiddenNeuronCounts, List<Function<Double, Double>> hiddenNeuronActivationFunctions,
                            Function<Double, Double> outputActivationFunction) {

        inputs = new double[inputCount];
        outputs = new double[outputCount];

        buildInputLayer(inputCount);

        NeuralLayer tail = buildHiddenLayers(hiddenNeuronCounts, hiddenNeuronActivationFunctions);

        outputLayer = buildOutputLayer(tail, outputCount, outputActivationFunction);
    }

    private void buildInputLayer(int inputCount) {
        inputLayer = new DefaultNeuralLayer(inputCount, inputCount, Function.identity());
    }

    private NeuralLayer buildHiddenLayers(List<Integer> hiddenNeuronCounts, List<Function<Double, Double>> hiddenNeuronActivationFunctions) {
        if (hiddenNeuronCounts.size() != hiddenNeuronActivationFunctions.size()) {
            throw new IllegalArgumentException("Need to have the same size for hidden neurons and activation functions.");
        }

        this.hiddenLayerCount = hiddenNeuronCounts.size();

        NeuralLayer tail = inputLayer;

        for (int i = 0; i < this.hiddenLayerCount; i++) {
            int currentNeuronCount = hiddenNeuronCounts.get(i);
            Function<Double, Double> currentFunction = hiddenNeuronActivationFunctions.get(i);

            int outputCount = tail.getNeuronCount();
            NeuralLayer currentLayer = new DefaultNeuralLayer(currentNeuronCount, outputCount, currentFunction);
            currentLayer.setPreviousLayer(tail);
            tail.setNextLayer(currentLayer);

            tail = currentLayer;
        }

        return tail;
    }

    private NeuralLayer buildOutputLayer(NeuralLayer previousLayer, int outputCount, Function<Double, Double> outputActivationFunction) {
        NeuralLayer outputLayer = new DefaultNeuralLayer(outputCount, previousLayer.getNeuronCount(), outputActivationFunction);
        outputLayer.setPreviousLayer(previousLayer);
        previousLayer.setNextLayer(outputLayer);
        return outputLayer;
    }

    @Override
    public void setInputs(double[] inputs) {
        if (inputs.length != this.inputs.length) {
            throw new IllegalArgumentException("Wrong number of inputs provided, should be "
                    + this.inputs.length + " but was " + inputs.length);
        }

        for (int i = 0; i < inputs.length; i++) {
            this.inputs[i] = inputs[i];
        }
    }

    @Override
    public double[] getOutputs() {
        return outputs;
    }

    private void setOutputs(double[] outputs) {
        if (outputs.length != this.outputs.length) {
            throw new IllegalArgumentException("Wrong number of inputs provided, should be "
                    + this.outputs.length + " but was " + outputs.length);
        }
        for (int i = 0; i < outputs.length; i++) {
            this.outputs[i] = outputs[i];
        }
    }

    @Override
    public void calculate() {
        Optional<NeuralLayer> nextLayer = Optional.of(inputLayer);
        double[] nextInputs = inputs;

        while (nextLayer.isPresent()) {
            NeuralLayer currentLayer = nextLayer.get();
            currentLayer.setInputs(nextInputs);
            currentLayer.calculateOutput();
            nextInputs = currentLayer.getOutputs();
            nextLayer = currentLayer.getNextLayer();
        }

        setOutputs(nextInputs);
    }

    @Override
    public int getHiddenLayerCount() {
        return hiddenLayerCount;
    }
}
