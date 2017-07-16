package org.mayukh.jxconverter.format;

import org.junit.Test;
import org.mayukh.jxconverter.utils.Utils;

/**
 * Created by mayukh42 on 7/16/2017.
 */
public class XmlFormatterTest {

    private final String location = "src/main/resources/xml/";

    @Test
    public void formatSimple() {
        String xml = XmlFormatter.format(location, "date.xml");
        System.out.println("Formatted XML: \n" + xml);
    }

    @Test
    public void formatComplex() {
        String xml = XmlFormatter.format(location, "trip.xml");
        System.out.println("Formatted XML: \n" + xml);
    }

    @Test
    public void testPreprocessAndFormat() {
        String raw = Utils.readFromFile(location, "trip-formatted.xml");
        String processed = XmlFormatter.preprocess(raw);
        System.out.println("Preprocessed XML: \n" + processed);
    }

    /**
     * Parses XML before formatting
     */
    @Test
    public void requiresValidation() {
        String xml = XmlFormatter.format(location, "trip-formatted.xml", true);
        System.out.println("Formatted XML: \n" + xml);
    }
}
