package org.mayukh.jxconverter.types.json;

import org.mayukh.jparse.types.NumberV;
import org.mayukh.jparse.types.Value;
import org.mayukh.xparse.dom.RegularXml;
import org.mayukh.xparse.dom.XmlElement;

/**
 * Created by mayukh42 on 7/14/2017.
 */
public class NumberVX extends ValueX {

    private NumberV numberV;

    public NumberVX(Value value) {
        super(value);
        this.numberV = (NumberV) value;
        convertX();
    }

    @Override
    protected void convertX() {
    }

    @Override
    public XmlElement toXmlElement(String key, XmlElement parent) {
        RegularXml parentR = (RegularXml) parent;
        parentR.setValue(numberV.toString());
        return parent;
    }

    @Override
    public String toString() {
        return numberV.toString();
    }
}
