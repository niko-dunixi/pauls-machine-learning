package wtf.paulbaker.myai;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by paul.baker on 6/8/17.
 */
public class DefaultNeuralLayer implements NeuralLayer {

    private final int neuronCount;
    private final int inputCount;
    private final double[] inputs;
    private final double[] outputs;
    private final Neuron[] neurons;
    private final Function<Double, Double> activationFunction;

    private NeuralLayer previousLayer, nextLayer;

    public DefaultNeuralLayer(int neuronCount, int inputCount, Function<Double, Double> activationFunction) {
        this.neuronCount = neuronCount;
        this.inputCount = inputCount;
        inputs = new double[inputCount];
        outputs = new double[neuronCount];
        neurons = new Neuron[neuronCount];
        this.activationFunction = activationFunction;

        previousLayer = nextLayer = null;

        initializeNeuralLayer();
    }

    @Override
    public void initializeNeuralLayer() {

        setInputs(new double[inputCount]);

        for (int i = 0; i < neuronCount; i++) {
            neurons[i] = new Neuron(inputCount, activationFunction);
            outputs[i] = 0;
        }
    }

    @Override
    public void calculateOutput() {
        for (int i = 0; i < neuronCount; i++) {
            Neuron currentNeuron = neurons[i];
            currentNeuron.setInputs(inputs);
            currentNeuron.calculateOutput();
            double currentOutput = currentNeuron.getCurrentOutput();
            outputs[i] = currentOutput;
        }
    }


    @Override
    public int getNeuronCount() {
        return neuronCount;
    }

    @Override
    public void setInputs(double[] inputs) {
        if (inputs.length != this.inputs.length) {
            throw new IllegalArgumentException("Wrong number of inputs, should be " +
                    inputCount + ". Not " + inputs.length);
        }
        for (int i = 0; i < inputs.length; i++) {
            this.inputs[i] = inputs[i];
        }
    }

    @Override
    public double[] getOutputs() {
        return outputs;
    }

    @Override
    public Neuron[] getNeurons() {
        return neurons;
    }

    @Override
    public Optional<NeuralLayer> getPreviousLayer() {
        return Optional.ofNullable(previousLayer);
    }

    @Override
    public void setPreviousLayer(NeuralLayer layer) {
        previousLayer = layer;
    }

    @Override
    public Optional<NeuralLayer> getNextLayer() {
        return Optional.ofNullable(nextLayer);
    }

    @Override
    public void setNextLayer(NeuralLayer layer) {
        nextLayer = layer;
    }
}
