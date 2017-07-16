package org.mayukh.jxconverter.format;

import org.mayukh.jparse.parser.Parser;
import org.mayukh.jparse.parser.State;
import org.mayukh.jparse.types.Value;

import java.util.Objects;

import static org.mayukh.jxconverter.utils.Utils.*;

/**
 * Created by mayukh42 on 7/13/2017.
 *
 * JSON formatter
 *  Egyptian style
 */
public class JsonFormatter {

    private static final Parser jsonParser = new Parser();

    /**
     * Format json file
     */
    public static String format(String location, String jsonFile) {
        String json = readFromFile(location, jsonFile);
        return formatJsonString(json);
    }

    /**
     * Validate and format JSON file
     * Creates the DOM first, then formats the DOM output
     */
    public static String format(String location, String jsonFile, boolean validate) {
        if (!validate) return format(location, jsonFile);

        String jsonStr = readFromFile(location, jsonFile);
        Value root = jsonParser.parse(jsonStr);

        return formatJsonString(root.toString());
    }

    /**
     * Remove stale formatting, but keep the space after ':'
     */
    private static String preprocess(String json) {
        String uncommented = jsonParser.preprocess(json);
        StringBuilder builder = new StringBuilder();
        State state = null;
        for (char c : json.toCharArray()) {
            if (state == null && c == '"') state = State.STRING;
            else if (state != null && c == '"') state = null;

            if (Objects.equals(state, State.STRING)) builder.append(c);
            else if (state == null && c != '\r' && c != '\n' && c != '\t' && c != ' ') {
                builder.append(c);
                if (c == ':') builder.append(' ');
            }
        }
        return builder.toString();
    }

    /**
     * format json string with tabs and newlines in one pass
     */
    private static String formatJsonString(String raw) {
        if (raw == null) return null;

        String json = preprocess(raw);
        int level = 0;
        StringBuilder builder = new StringBuilder();
        for (char c : json.toCharArray()) {
            if (c == '{' || c == '[') {
                builder.append(c);
                level++;
                builder.append(nextLineAndIndent(level));
            }
            else if (c == '}' || c == ']') {
                level--;
                builder.append(nextLineAndIndent(level));
                builder.append(c);
            }
            else if (c == ',') {
                builder.append(c);
                builder.append(nextLineAndIndent(level));
            }
            else builder.append(c);
        }
        if (level != 0) throw new FormatException("Json is invalid");
        return builder.toString();
    }
}
