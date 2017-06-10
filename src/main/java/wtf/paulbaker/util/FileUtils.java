package wtf.paulbaker.util;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by paul.baker on 6/10/17.
 */
public class FileUtils {

    private FileUtils() {
    }

    public static File fromClassPath(String filepath) {
        URL resource = FileUtils.class.getClassLoader().getResource("dataset/xor.csv");
        try {
            if (resource == null) {
                // Resource is null if the file doesn't exist.
                throw new IllegalArgumentException("No such file in classpath: " + filepath);
            }
            // If the URI is bad (which it shouldn't be to get to this point?) we say the file doesn't exist.
            URI uri = resource.toURI();
            return new File(uri);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("No such file in classpath: " + filepath);
        }
    }

}
