package org.mayukh.jxconverter.types.json;

import org.mayukh.jparse.types.*;
import org.mayukh.xparse.dom.RegularXml;
import org.mayukh.xparse.dom.XmlElement;
import org.mayukh.xparse.parser.EndTag;
import org.mayukh.xparse.parser.StartTag;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by mayukh42 on 7/13/2017.
 *
 * Json key-value pairs to XML
 */
public class JsonVX extends ValueX {

    private final Json json;
    private Map<String, ValueX> kv;

    public JsonVX(Value value) {
        super(value);
        this.json = (Json) value;
        this.kv = new LinkedHashMap<>();
        convertX();
    }

    /**
     * convertX(): Convert Json to JsonX
     *  replace whitespaces in key with underscores
     */
    @Override
    protected void convertX() {
        for (String key : json.keys())
            kv.put(key.replaceAll(" ", "_"), transformValue(json.get(key)));
    }

    /**
     * For root JSON, parent is null, so create it.
     *
     * TODO: Create inlne XML for empty JSON. Currently creates empty Regular XMl
     */
    @Override
    public XmlElement toXmlElement(String key, XmlElement parent) {
        List<XmlElement> children = null;
        if (parent == null)
            parent = new RegularXml(new StartTag(key), new EndTag(key), children);

        RegularXml parentR = (RegularXml) parent;
        for (String k : kv.keySet()) {
            if (!(kv.get(k) instanceof ListVX)) {
                // for lists we will create from within that value. Ref. ListVX class
                RegularXml current = new RegularXml(new StartTag(k), new EndTag(k), children);
                parentR.addChild(current);
                RegularXml valueXml = (RegularXml) kv.get(k).toXmlElement(k, current);
                if (!Objects.equals(valueXml, current)) current.addChild(valueXml);
            }
            else kv.get(k).toXmlElement(k, parent);
        }
        return parent;
    }

    @Override
    public String toString() {
        return kv.toString();
    }
}
