package wtf.paulbaker.myai;

import java.io.Serializable;

/**
 * Created by paul.baker on 6/9/17.
 */
public interface NeuralNet extends Serializable {

    void setInputs(double[] inputs);

    double[] getOutputs();

    void calculate();
}
