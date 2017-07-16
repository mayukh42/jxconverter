package org.mayukh.jxconverter.format;

import org.junit.Test;

/**
 * Created by mayukh42 on 7/13/2017.
 */
public class JsonFormatterTest {

    private final String location = "src/main/resources/json/";

    @Test
    public void testSimpleJson() {
        String formatted = JsonFormatter.format(location, "engine.json");
        System.out.println(formatted);
    }

    @Test
    public void testSimpleJsonWithValidate() {
        String formatted = JsonFormatter.format(location, "engine.json", true);
        System.out.println(formatted);
    }

    @Test
    public void testAlreadyFormattedSimple() {
        String formatted = JsonFormatter.format(location, "engine-formatted.json");
        System.out.println(formatted);
    }

    @Test
    public void testAlreadyFormattedSimpleWithValidate() {
        String formatted = JsonFormatter.format(location, "engine-formatted.json", true);
        System.out.println(formatted);
    }

    @Test
    public void testComplexJson() {
        String formatted = JsonFormatter.format(location, "trip.json", true);
        System.out.println(formatted);
    }

    @Test
    public void testFormattedAndCommentedJson() {
        String formatted = JsonFormatter.format(location, "trip-formatted-commented.json", true);
        System.out.println(formatted);
    }
}
