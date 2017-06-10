package wtf.paulbaker.myai.data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Created by paul.baker on 6/9/17.
 */
public class DataSet {

    private double[][] inputData, outputData;

    public DataSet(double[][] rawData, int partitionIndex) {
        int sampleCount = rawData.length;
        inputData = new double[sampleCount][];
        outputData = new double[sampleCount][];

        int inputRowSize = 0;
        int outputRowSize = 0;

        for (int rowIndex = 0; rowIndex < rawData.length; rowIndex++) {
            double[] currentRow = rawData[rowIndex];
            double[] currentInput = Arrays.copyOfRange(currentRow, 0, partitionIndex);
            double[] currentOutput = Arrays.copyOfRange(currentRow, partitionIndex, currentRow.length);
            // Minor validation
            int currentInputRowSize = currentInput.length;
            int currentOutputRowSize = currentOutput.length;
            if (rowIndex == 0) {
                inputRowSize = currentInputRowSize;
                outputRowSize = currentOutputRowSize;
            } else if (inputRowSize != currentInputRowSize) {
                throw new IllegalArgumentException("On row " + rowIndex + " we encountered a different size of inputs");
            } else if (outputRowSize != currentOutputRowSize) {
                throw new IllegalArgumentException("On row " + rowIndex + " we encountered a different size of outputs");
            }
            // Store the data
            inputData[rowIndex] = currentInput;
            outputData[rowIndex] = currentOutput;
        }
    }

    private DataSet(double[][] inputs, double[][] outputs) {
        this.inputData = inputs;
        this.outputData = outputs;
    }

    public double[] getInput(int rowIndex) {
        return inputData[rowIndex];
    }

    public double[] getOutput(int rowIndex) {
        return outputData[rowIndex];
    }

    public static DataSet fromCSVFile(File csvFile, int partitionIndex) throws IOException {
        return fromCSVFile(csvFile, partitionIndex, false);
    }

    public static DataSet fromCSVFile(File csvFile, int partitionIndex, boolean skipFirstLine) throws IOException {
        Stream<String> lines = Files.lines(csvFile.toPath());
        double[][] data = lines.skip(skipFirstLine ? 1 : 0)
                .map(line -> line.split(","))
                .map(array -> Arrays.stream(array).mapToDouble(Double::parseDouble).toArray())
                .toArray(double[][]::new);

        return new DataSet(data, partitionIndex);
    }
}
