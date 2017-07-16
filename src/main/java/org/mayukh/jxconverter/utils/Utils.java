package org.mayukh.jxconverter.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by mayukh42 on 7/13/2017.
 */
public class Utils {

    /**
     * Create a new line and indent as per the level
     * Used by both JsonFormatter and XmlFormatter
     */
    public static String nextLineAndIndent(int level) {
        StringBuilder spaces = new StringBuilder();
        spaces.append('\n');
        for (int i = 0; i < level; i++)
            spaces.append('\t');
        return spaces.toString();
    }

    /**
     * Read string from file
     */
    public static String readFromFile(String location, String file) {
        Path path = Paths.get(location, file);
        String content = null;
        try {
            content = new String(Files.readAllBytes(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public static boolean isTagLiteral(char c) {
        return  (c >= '0' && c <= '9') ||
                (c >= 'A' && c <= 'Z') ||
                (c >= 'a' && c <= 'z') ||
                (c == '_');
    }

    public static boolean isLiteral(char c) {
        return isTagLiteral(c) ||
                (c == ' ' || c == '-' || c == '.' || c == '*' || c == ':');
    }
}
