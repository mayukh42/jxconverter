package org.mayukh.jxconverter.format;

/**
 * Created by mayukh42 on 7/13/2017.
 */
public class FormatException extends RuntimeException {

    public FormatException(String message) {
        super("[JxConverter.Format] Invalid source file " + message);
    }
}
