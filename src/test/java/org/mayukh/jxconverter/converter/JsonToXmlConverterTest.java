package org.mayukh.jxconverter.converter;

import org.junit.Test;
import org.mayukh.jparse.parser.Parser;
import org.mayukh.jparse.types.Value;
import org.mayukh.jxconverter.types.json.ValueX;
import org.mayukh.jxconverter.utils.Utils;
import org.mayukh.xparse.dom.XmlElement;

/**
 * Created by mayukh42 on 7/15/2017.
 */
public class JsonToXmlConverterTest {

    private final String location = "src/main/resources/json/";
    private final Parser jsonParser = new Parser();

    @Test
    public void testTransfomX() {
        String jsonContent = Utils.readFromFile(location, "trip.json");
        ValueX jsonX = JsonToXmlConverter.applyX(jsonParser.parse(jsonContent));
        System.out.println(jsonX);
    }

    @Test
    public void testConvertComplexJson() {
        String jsonContent = Utils.readFromFile(location, "trip.json");
        Value json = jsonParser.parse(jsonContent);
        XmlElement xml = JsonToXmlConverter.apply(json);
        System.out.println(xml);
    }

    @Test
    public void testConvertArraysJson() {
        String jsonContent = Utils.readFromFile(location, "array.json");
        Value json = jsonParser.parse(jsonContent);
        XmlElement xml = JsonToXmlConverter.apply(json);
        System.out.println(xml);
    }
}
