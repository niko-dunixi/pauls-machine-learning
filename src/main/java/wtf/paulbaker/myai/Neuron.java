package wtf.paulbaker.myai;

import java.io.Serializable;

/**
 * Created by paul.baker on 6/9/17.
 */
public interface Neuron extends Serializable {

    void initializeNeuron();

    void setInputs(double[] inputs);

    void setInputAtIndex(int index, double input);

    void calculateOutput();

    double getCurrentOutput();
}
