package org.mayukh.jxconverter.types.json;

import org.mayukh.jparse.types.StringV;
import org.mayukh.jparse.types.Value;
import org.mayukh.xparse.dom.RegularXml;
import org.mayukh.xparse.dom.XmlElement;

/**
 * Created by mayukh42 on 7/14/2017.
 */
public class StringVX extends ValueX {

    private StringV stringV = null;

    public StringVX(Value value) {
        super(value);
        this.stringV = (StringV) value;
        convertX();
    }

    @Override
    protected void convertX() {
    }

    /**
     * create a xml element from this json value and add to the provided parent
     */
    @Override
    public XmlElement toXmlElement(String key, XmlElement parent) {
        RegularXml parentR = (RegularXml) parent;
        parentR.setValue(stringV.getValue());
        return parent;
    }

    @Override
    public String toString() {
        return stringV.getValue();
    }
}
