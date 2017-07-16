package org.mayukh.jxconverter.types.xml;

import org.mayukh.xparse.parser.StartTag;

/**
 * Created by mayukh42 on 7/14/2017.
 *
 * A StartTag for String/ Number literals, for type homogeneity.
 */
public class LiteralTag extends StartTag {

    public static LiteralTag instance;

    private LiteralTag() {
        super("literal");
    }

    /**
     * Create LiteralTag singleton instance - only one is required.
     */
    public static LiteralTag getInstance() {
        if (instance == null) {
            synchronized (LiteralTag.class) {
                if (instance == null)
                    instance = new LiteralTag();
            }
        }
        return instance;
    }
}
