package wtf.paulbaker.myai;

import java.io.Serializable;

/**
 * Created by paul.baker on 6/9/17.
 */
public interface NeuralNet extends Serializable {

    void setInputs(double[] inputs);

    double[] getOutputs();

    void calculate();

    int getHiddenLayerCount();

    default boolean hasHiddenLayers() {
        return getHiddenLayerCount() > 0;
    }

    default boolean isSingleLayer() {
        return !hasHiddenLayers();
    }
}
