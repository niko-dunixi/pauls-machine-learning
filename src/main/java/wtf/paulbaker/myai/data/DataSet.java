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

        int rowSize = 0;

        for (int rowIndex = 0; rowIndex < rawData.length; rowIndex++) {
            double[] currentRow = rawData[rowIndex];
            // Minor validation
            int currentRowSize = currentRow.length;
            if (rowIndex == 0) {
                rowSize = currentRowSize;
                if (partitionIndex >= rowSize) {
                    throw new IllegalArgumentException("Bad partition index " + partitionIndex + ", where the size of the row is " + rowSize);
                }
            } else if (rowSize != currentRowSize) {
                throw new IllegalArgumentException("On row " + rowIndex + " we encountered a jagged row size. All rows must be uniform length.");
            }
            // Store the data
            double[] currentInput = Arrays.copyOfRange(currentRow, 0, partitionIndex);
            double[] currentOutput = Arrays.copyOfRange(currentRow, partitionIndex, currentRow.length);
            inputData[rowIndex] = currentInput;
            outputData[rowIndex] = currentOutput;
        }
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
