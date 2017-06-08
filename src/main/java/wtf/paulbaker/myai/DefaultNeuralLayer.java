package wtf.paulbaker.myai;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static wtf.paulbaker.myai.ArrayUtil.emptyDoubleListOfSize;
import static wtf.paulbaker.myai.ArrayUtil.emptyListOfSize;

/**
 * Created by paul.baker on 6/8/17.
 */
public class DefaultNeuralLayer implements NeuralLayer {

    private final int neuronCount;
    private final int inputCount;
    private final List<Double> inputs;
    private final List<Double> outputs;
    private final List<Neuron> neurons;
    private final Function<Double, Double> activationFunction;

    private NeuralLayer previousLayer, nextLayer;

    private double currentOutput;

    public DefaultNeuralLayer(int neuronCount, int inputCount, Function<Double, Double> activationFunction) {
        this.neuronCount = neuronCount;
        this.inputCount = inputCount;
        inputs = emptyDoubleListOfSize(inputCount);
        outputs = emptyDoubleListOfSize(neuronCount);
        neurons = emptyListOfSize(neuronCount);
        this.activationFunction = activationFunction;

        previousLayer = nextLayer = null;

        initializeNeuralLayer();
    }

    @Override
    public void initializeNeuralLayer() {
        currentOutput = 0d;

        setInputs(new double[inputCount]);

        for (int i = 0; i < neuronCount; i++) {
            neurons.set(i, new Neuron(inputCount, activationFunction));
            outputs.set(i, 0d);
        }
    }

    @Override
    public void calculateOutput() {
        for (int i = 0; i < neuronCount; i++) {
            Neuron currentNeuron = neurons.get(i);
            currentNeuron.setInputs(inputs);
            currentNeuron.calculateOutput();
            double currentOutput = currentNeuron.getCurrentOutput();
            outputs.set(i, currentOutput);
        }
    }

    @Override
    public double getCurrentOutput() {
        return currentOutput;
    }

    @Override
    public int getNeuronCount() {
        return neuronCount;
    }

    @Override
    public void setInputs(List<Double> inputs) {
        if (inputs.size() != inputCount) {
            throw new IllegalArgumentException("Wrong number of inputs, should be " +
                    inputCount + ". Not " + inputs.size());
        }
        for (int i = 0; i < inputs.size(); i++) {
            if (i < this.inputs.size())
                this.inputs.set(i, inputs.get(i));
            else
                this.inputs.add(i, inputs.get(i));
        }
    }

    @Override
    public List<Double> getOutputs() {
        return outputs;
    }

    @Override
    public List<Neuron> getNeurons() {
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
