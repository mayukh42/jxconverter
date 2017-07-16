package org.mayukh.jxconverter.types.json;

import org.mayukh.jparse.types.ListV;
import org.mayukh.jparse.types.Value;
import org.mayukh.xparse.dom.InlineXml;
import org.mayukh.xparse.dom.RegularXml;
import org.mayukh.xparse.dom.XmlElement;
import org.mayukh.xparse.parser.EndTag;
import org.mayukh.xparse.parser.StartTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by mayukh42 on 7/14/2017.
 *
 * ListV parallel for converter type
 */
public class ListVX extends ValueX {

    private ListV listV;
    private List<ValueX> elems;

    public ListVX(Value value) {
        super(value);
        this.listV = (ListV) value;
        elems = new ArrayList<>();
        convertX();
    }

    @Override
    protected void convertX() {
        if (listV.getValues() != null) {
            for (Value value : listV.getValues())
                elems.add(transformValue(value));
        }
    }

    @Override
    public XmlElement toXmlElement(String key, XmlElement parent) {
        return createIdenticalTags(key, parent);
    }

    /**
     * toXmlElement(): create identical elements
     */
    private XmlElement createIdenticalTags(String key, XmlElement parent) {
        RegularXml parentR = (RegularXml) parent;
        List<XmlElement> children = null;

        if (elems.isEmpty()) {
            InlineXml current = new InlineXml(new StartTag(key));
            parentR.addChild(current);
            return parent;
        }

        for (ValueX valueX : elems) {
            RegularXml current = new RegularXml(new StartTag(key), new EndTag(key), children);
            parentR.addChild(current);
            RegularXml valueXml = (RegularXml) valueX.toXmlElement(key, current);
            if (!Objects.equals(valueXml, current)) current.addChild(valueXml);
        }
        return parent;
    }

    @Override
    public String toString() {
        return elems.toString();
    }
}
