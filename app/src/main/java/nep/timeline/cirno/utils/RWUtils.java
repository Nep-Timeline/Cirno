package nep.timeline.cirno.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class RWUtils {
    public static void writeStringToFile(File file, String value, boolean append) throws IOException {
        FileUtils.write(file, value + "\n", StandardCharsets.UTF_8, append);
    }

    public static boolean writeFrozen(String path, int value) {
        try (PrintWriter writer = new PrintWriter(path)) {
            writer.write(Integer.toString(value));
            writer.flush();
            return true;
        } catch (FileNotFoundException ignored) {
            return false;
        }
    }
}
