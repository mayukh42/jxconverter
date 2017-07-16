package org.mayukh.jxconverter.types.json;

import org.mayukh.jparse.types.*;
import org.mayukh.xparse.dom.XmlElement;

/**
 * Created by mayukh42 on 7/13/2017.
 */
public abstract class ValueX {

    public ValueX(Value value) {
        // cons with Value, to be implemented by subclasses
    }

    protected abstract void convertX();

    public static ValueX transformValue(Value value) {
        ValueX valueX = null;
        if (value instanceof Json) valueX = new JsonVX(value);
        else if (value instanceof ListV) valueX = new ListVX(value);
        else if (value instanceof StringV) valueX = new StringVX(value);
        else if (value instanceof NumberV) valueX = new NumberVX(value);
        return valueX;
    }

    public abstract XmlElement toXmlElement(String key, XmlElement parent);
}
