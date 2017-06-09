package wtf.paulbaker.myai;

import java.io.Serializable;
import java.util.Optional;

/**
 * Created by paul.baker on 6/8/17.
 */
public interface NeuralLayer extends Serializable {

    void initializeNeuralLayer();

    void calculateOutput();

    int getNeuronCount();

    void setInputs(double[] inputs);

    double[] getOutputs();

    Neuron[] getNeurons();

    default Neuron getNeuron(int index) {
        return getNeurons()[index];
    }

    Optional<NeuralLayer> getPreviousLayer();

    void setPreviousLayer(NeuralLayer layer);

    Optional<NeuralLayer> getNextLayer();

    void setNextLayer(NeuralLayer layer);
}
