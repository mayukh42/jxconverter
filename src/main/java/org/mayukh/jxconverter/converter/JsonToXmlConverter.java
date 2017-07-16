package org.mayukh.jxconverter.converter;

import org.mayukh.jparse.types.*;
import org.mayukh.jxconverter.types.json.*;
import org.mayukh.xparse.dom.XmlElement;

/**
 * Created by mayukh42 on 7/14/2017.
 */
public class JsonToXmlConverter {

    // API
    public static XmlElement apply(Value json) {
        return transform(json, null);
    }

    static ValueX applyX(Value json) {
        return transformX(json, null);
    }

    private static XmlElement transform(Value json, XmlElement root) {
        if (json == null) return root;
        ValueX rootX = applyX(json);
        return rootX.toXmlElement("root", root);
    }

    private static ValueX transformX(Value json, ValueX jsonX) {
        if (json == null) return jsonX;
        if (jsonX == null) jsonX = ValueX.transformValue(json);
        return jsonX;
    }
}
