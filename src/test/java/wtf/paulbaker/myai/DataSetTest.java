package wtf.paulbaker.myai;

import org.junit.jupiter.api.Test;
import wtf.paulbaker.myai.data.DataSet;
import wtf.paulbaker.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by paul.baker on 6/10/17.
 */
public class DataSetTest {

    @Test
    public void testXORLoads() throws URISyntaxException, IOException {
        File xorFile = FileUtils.fromClassPath("dataset/xor.csv");
        DataSet xorData = DataSet.fromCSVFile(xorFile, 2, true);

        assertThat(xorData.getInput(0), is(new double[]{0, 0}));
        assertThat(xorData.getInput(1), is(new double[]{0, 1}));
        assertThat(xorData.getInput(2), is(new double[]{1, 0}));
        assertThat(xorData.getInput(3), is(new double[]{1, 1}));

        assertThat(xorData.getOutput(0), is(new double[]{0}));
        assertThat(xorData.getOutput(1), is(new double[]{1}));
        assertThat(xorData.getOutput(2), is(new double[]{1}));
        assertThat(xorData.getOutput(3), is(new double[]{0}));
    }

    @Test
    public void testXORFailsToLoadWithoutLineSkip() throws IOException {
        File xorFile = FileUtils.fromClassPath("dataset/xor.csv");
        assertThrows(NumberFormatException.class, () -> DataSet.fromCSVFile(xorFile, 2, false));
    }
}
