package wtf.paulbaker.myai;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by paul.baker on 6/8/17.
 */
public interface NeuralLayer extends Serializable {

    void initializeNeuralLayer();

    void calculateOutput();

    double getCurrentOutput();

    int getNeuronCount();

    default void setInputs(double[] inputs) {
        this.setInputs(Arrays.stream(inputs).boxed().collect(Collectors.toList()));
    }

    void setInputs(List<Double> inputs);

    List<Double> getOutputs();

    List<Neuron> getNeurons();

    default Neuron getNeuron(int index) {
        return getNeurons().get(index);
    }

    Optional<NeuralLayer> getPreviousLayer();

    void setPreviousLayer(NeuralLayer layer);

    Optional<NeuralLayer> getNextLayer();

    void setNextLayer(NeuralLayer layer);
}
