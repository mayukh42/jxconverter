package org.mayukh.jxconverter.types.json;

/**
 * Created by mayukh42 on 7/15/2017.
 */
public class JsonTypeException extends RuntimeException {

    public JsonTypeException(String message) {
        super("[JxConverter.Types] " + message);
    }
}
