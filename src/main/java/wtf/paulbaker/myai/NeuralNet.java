package wtf.paulbaker.myai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

/**
 * Created by paul.baker on 6/8/17.
 */
public class NeuralNet {

    private NeuralLayer inputLayer, outputLayer;

    private List<NeuralLayer> hiddenLayers;

    private List<Double> inputs, outputs;

    public NeuralNet(int inputCount, int outputCount, Function<Double, Double> outputActivationFunction) {
        this(inputCount, outputCount, emptyList(), emptyList(), outputActivationFunction);
    }

    public NeuralNet(int inputCount, int outputCount,
                     List<Integer> hiddenNeuronCounts, List<Function<Double, Double>> hiddenNeuronActivationFunctions,
                     Function<Double, Double> outputActivationFunction) {

        inputs = Arrays.asList(new Double[inputCount]);
        outputs = Arrays.asList(new Double[outputCount]);

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
        hiddenLayers = new ArrayList<>(hiddenNeuronCount);

        for (int i = 0; i < hiddenNeuronCount; i++) {
            int currentNeuronCount = hiddenNeuronCounts.get(i);
            Function<Double, Double> currentFunction = hiddenNeuronActivationFunctions.get(i);

            NeuralLayer previousLayer;
            if (i == 0) {
                previousLayer = inputLayer;
            } else {
                previousLayer = hiddenLayers.get(i - 1);
            }

            int previousNeuronCount = previousLayer.getNeuronCount();

            DefaultNeuralLayer currentLayer = new DefaultNeuralLayer(currentNeuronCount, previousNeuronCount, currentFunction);
            previousLayer.setNextLayer(currentLayer);
            currentLayer.setPreviousLayer(previousLayer);

            hiddenLayers.add(currentLayer);
        }
    }

    private void buildOutputLayer(int outputCount, Function<Double, Double> outputActivationFunction) {
        NeuralLayer secondToLastLayer;
        if (hiddenLayers.isEmpty()) {
            secondToLastLayer = inputLayer;
        } else {
            secondToLastLayer = hiddenLayers.get(hiddenLayers.size() - 1);
        }

        outputLayer = new DefaultNeuralLayer(outputCount, secondToLastLayer.getNeuronCount(), outputActivationFunction);
        outputLayer.setPreviousLayer(secondToLastLayer);
        secondToLastLayer.setNextLayer(outputLayer);
    }

    public void setInputs(double[] inputs) {
        this.setInputs(Arrays.stream(inputs).boxed().collect(Collectors.toList()));
    }

    public void setInputs(List<Double> values) {
        if (values.size() != this.inputs.size()) {
            throw new IllegalArgumentException("Wrong number of inputs provided, should be "
                    + this.inputs.size() + " but was " + values.size());
        }

        for (int i = 0; i < values.size(); i++) {
            this.inputs.set(i, values.get(i));
        }
    }

    private void setOutputs(List<Double> outputs) {
        for (int i = 0; i < outputs.size(); i++) {
            this.outputs.set(i, outputs.get(i));
        }
    }

    public List<Double> getOutputs() {
        return outputs;
    }

    @SuppressWarnings("ConstantConditions")
    public void calculate() {
        inputLayer.setInputs(inputs);
        for (int i = 0; i < hiddenLayers.size(); i++) {
            NeuralLayer currentLayer = hiddenLayers.get(i);

            NeuralLayer previousLayer = currentLayer.getPreviousLayer().get();
            previousLayer.calculateOutput();
            List<Double> previousLayerOutputs = previousLayer.getOutputs();

            currentLayer.setInputs(previousLayerOutputs);
        }

        NeuralLayer secondToLastLayer = outputLayer.getPreviousLayer().get();
        secondToLastLayer.calculateOutput();
        outputLayer.setInputs(secondToLastLayer.getOutputs());
        outputLayer.calculateOutput();
        setOutputs(outputLayer.getOutputs());
    }
}
