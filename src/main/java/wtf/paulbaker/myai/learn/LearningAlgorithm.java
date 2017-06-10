package wtf.paulbaker.myai.learn;

import wtf.paulbaker.myai.NeuralNet;
import wtf.paulbaker.myai.data.DataSet;

import java.util.function.Supplier;

/**
 * Created by paul.baker on 6/9/17.
 */
public abstract class LearningAlgorithm {

    private final Supplier<NeuralNet> neuralNetSupplier;
    private NeuralNet neuralNet;

    public LearningAlgorithm(Supplier<NeuralNet> neuralNetSupplier, Supplier<DataSet> dataSetSupplier) {
        this.neuralNetSupplier = neuralNetSupplier;
        initialize();
    }

    public void initialize() {
        neuralNet = neuralNetSupplier.get();
    }

    protected NeuralNet getNeuralNet() {
        return neuralNet;
    }

    public abstract void train();

    public abstract void forward();

    public abstract void forward(int k);

    public abstract double calculateNewWeight(int layer, int input, int neuron);

    public abstract double calculateNewWeight(int layer, int input, int neuron, double error);

    public abstract void test();

    public abstract void print();

    public abstract void setLearningRate(double rate);

    public abstract void getLearningRate();

}
