package nep.timeline.cirno.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import nep.timeline.cirno.log.Log;

public class RWUtils {
    public static String readConfig(String name) {
        try {
            return String.join("\n", FileUtils.readLines(new File(name), StandardCharsets.UTF_8));
        } catch (IOException e) {
            Log.e("Read Config", e);
        }

        return "";
    }

    public static void writeStringToFile(File file, String value) throws IOException {
        writeStringToFile(file, value + "\n", false);
    }

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
