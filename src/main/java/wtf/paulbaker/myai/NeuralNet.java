package wtf.paulbaker.myai;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Collections.emptyList;

/**
 * Created by paul.baker on 6/8/17.
 */
public class NeuralNet {

    private NeuralLayer inputLayer, outputLayer;

    private NeuralLayer[] hiddenLayers;

    private double[] inputs, outputs;

    public NeuralNet(int inputCount, int outputCount, Function<Double, Double> outputActivationFunction) {
        this(inputCount, outputCount, emptyList(), emptyList(), outputActivationFunction);
    }

    public NeuralNet(int inputCount, int outputCount,
                     List<Integer> hiddenNeuronCounts, List<Function<Double, Double>> hiddenNeuronActivationFunctions,
                     Function<Double, Double> outputActivationFunction) {

        inputs = new double[inputCount];
        outputs = new double[outputCount];

        buildInputLayer(inputCount);

        buildHiddenLayers(hiddenNeuronCounts, hiddenNeuronActivationFunctions);

        buildOutputLayer(outputCount, outputActivationFunction);
    }

    private void buildInputLayer(int inputCount) {
        inputLayer = new DefaultNeuralLayer(inputCount, inputCount, Function.identity());
    }

    private void buildHiddenLayers(List<Integer> hiddenNeuronCounts, List<Function<Double, Double>> hiddenNeuronActivationFunctions) {
        if (hiddenNeuronCounts.size() != hiddenNeuronActivationFunctions.size()) {
            throw new IllegalArgumentException("Need to have the same size for hidden neurons and activation functions.");
        }

        int hiddenNeuronCount = hiddenNeuronCounts.size();
        hiddenLayers = new NeuralLayer[hiddenNeuronCount];

        for (int i = 0; i < hiddenNeuronCount; i++) {
            int currentNeuronCount = hiddenNeuronCounts.get(i);
            Function<Double, Double> currentFunction = hiddenNeuronActivationFunctions.get(i);

            NeuralLayer previousLayer;
            if (i == 0) {
                previousLayer = inputLayer;
            } else {
                previousLayer = hiddenLayers[i - 1];
            }

            int previousNeuronCount = previousLayer.getNeuronCount();

            DefaultNeuralLayer currentLayer = new DefaultNeuralLayer(currentNeuronCount, previousNeuronCount, currentFunction);
            previousLayer.setNextLayer(currentLayer);
            currentLayer.setPreviousLayer(previousLayer);

            hiddenLayers[i] = currentLayer;
        }
    }

    private void buildOutputLayer(int outputCount, Function<Double, Double> outputActivationFunction) {
        NeuralLayer secondToLastLayer;
        if (hiddenLayers.length == 0) {
            secondToLastLayer = inputLayer;
        } else {
            secondToLastLayer = hiddenLayers[hiddenLayers.length - 1];
        }

        outputLayer = new DefaultNeuralLayer(outputCount, secondToLastLayer.getNeuronCount(), outputActivationFunction);
        outputLayer.setPreviousLayer(secondToLastLayer);
        secondToLastLayer.setNextLayer(outputLayer);
    }

    public void setInputs(double[] inputs) {
        if (inputs.length != this.inputs.length) {
            throw new IllegalArgumentException("Wrong number of inputs provided, should be "
                    + this.inputs.length + " but was " + inputs.length);
        }

        for (int i = 0; i < inputs.length; i++) {
            this.inputs[i] = inputs[i];
        }
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

    public double[] getOutputs() {
        return outputs;
    }

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
}
