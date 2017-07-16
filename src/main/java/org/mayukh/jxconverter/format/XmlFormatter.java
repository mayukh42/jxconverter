package org.mayukh.jxconverter.format;

import org.mayukh.xparse.parser.Parser;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

import static org.mayukh.jxconverter.utils.Utils.*;

/**
 * Created by mayukh42 on 7/16/2017.
 */
public class XmlFormatter {

    // API

    /**
     * Format raw XML (when assumed to be without comments and unformatted)
     */
    public static String format(String location, String xmlFile) {
        String xml = readFromFile(location, xmlFile);
        return formatXmlString(xml);
    }

    /**
     * Format processed XML using xparse XML parser when raw XML is assumed to contain comments and stale whitespaces
     */
    public static String format(String location, String xmlFile, boolean validate) {
        if (!validate) return format(location, xmlFile);

        String raw = readFromFile(location, xmlFile);
        String processed = Parser.parse(raw).toString();
        return formatXmlString(processed);
    }

    /**
     * Remove comments and stale whitespaces. Uses xparse's preprocess routine to remove comments and new line/ tabs,
     *  then make another pass to remove any stale whitespaces left.
     *  O(n) in number of characters
     */
    static String preprocess(String raw) {
        String uncommented = Parser.preprocess(raw);    // uncommented and \n, \r, \t removed
        StringBuilder builder = new StringBuilder();
        int length = uncommented.length();
        XmlMode mode = null;
        boolean inXml = false;
        for (int i = 0; i < length; i++) {
            char c = uncommented.charAt(i);
            if (mode == null && c == '<' && i < length-1 && isTagLiteral(uncommented.charAt(i+1))) {
                // tag start; space is allowed here
                builder.append(c);
                mode = XmlMode.START;
                inXml = true;
            }
            else if (Objects.equals(mode, XmlMode.START) && (isLiteral(c) || c == '"' || c == '=')) {
                builder.append(c);
            }
            else if (Objects.equals(mode, XmlMode.START) && c == '>') {
                builder.append(c);
                mode = null;
            }
            else if (inXml && isLiteral(c) && c != ' ') {
                builder.append(c);
            }
            else if (inXml && c == '<' && i < length-1 && uncommented.charAt(i+1) == '/') {
                builder.append(c);
                mode = XmlMode.END;
            }
            else if (Objects.equals(mode, XmlMode.END) && (isTagLiteral(c) || c == '/')) {
                builder.append(c);
            }
            else if (Objects.equals(mode, XmlMode.END) && c == '>') {
                // end tag ends
                builder.append(c);
                mode = null;
                inXml = false;
            }
            else if (Objects.equals(mode, XmlMode.START) && c == '/' && i < length-1
                    && uncommented.charAt(i+1) == '>') {
                builder.append(c);
                inXml = false;
            }
            else if (!inXml && c != ' ') {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    /**
     * formatXmlString(): formats a given xml string after preprocessing it (removal of comments and stale whitespaces,
     *  any existing formatting).
     *  O(n) in number of characters including preprocess(), which just adds a couple of passes.
     */
    private static String formatXmlString(String raw) {
        String xml = preprocess(raw);
        StringBuilder builder = new StringBuilder();
        Deque<XmlMode> modes = new ArrayDeque<>();
        int level = 0, length = xml.length();
        for (int i = 0; i < length; i++) {
            char c = xml.charAt(i);
            if (modes.isEmpty() && c == '<' && i < length-1 && isTagLiteral(xml.charAt(i+1))) {
                // root tag
                modes.push(XmlMode.XML);
                modes.push(XmlMode.START);
            }
            else if (Objects.equals(modes.peek(), XmlMode.XML) && c == '<' && i < length-1
                    && isTagLiteral(xml.charAt(i+1))) {
                // start of nested start tag
                modes.push(XmlMode.XML);
                modes.push(XmlMode.START);
                level++;
                builder.append(nextLineAndIndent(level));
            }
            else if (Objects.equals(modes.peek(), XmlMode.XML) && isLiteral(c)) {
                // start of literal
                modes.push(XmlMode.LITERAL);
            }
            else if (Objects.equals(modes.peek(), XmlMode.START) && c == '>') {
                // end of start tag
                modes.pop();
            }
            else if (Objects.equals(modes.peek(), XmlMode.START) && c == '/' && i < length-1
                    && xml.charAt(i+1) == '>') {
                // inline tag end
                modes.pop();
                modes.pop();
                level--;
            }
            else if (Objects.equals(modes.peek(), XmlMode.XML) && c == '<' && i < length-1
                    && xml.charAt(i+1) == '/') {
                // start of end tag
                modes.push(XmlMode.END);
                builder.append(nextLineAndIndent(level));
            }
            else if (Objects.equals(modes.peek(), XmlMode.LITERAL) && c == '<' && i < length-1
                    && xml.charAt(i+1) == '/') {
                // end of literal and start of end tag
                modes.pop();
                modes.push(XmlMode.END);
            }
            else if (Objects.equals(modes.peek(), XmlMode.END) && c == '>') {
                // end of end tag
                modes.pop();
                level--;
            }
            builder.append(c);
        }

        if (level > 0) throw new FormatException("Xml is not valid!");
        return builder.toString();
    }
}
