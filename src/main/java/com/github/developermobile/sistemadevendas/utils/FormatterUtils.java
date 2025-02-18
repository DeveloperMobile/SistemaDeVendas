
package com.github.developermobile.sistemadevendas.utils;

import java.text.ParseException;
import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author tiago
 */
public class FormatterUtils {
    public static void maskFormatter(String mask, JFormattedTextField jftf) {
        try {
            MaskFormatter formatter = new MaskFormatter(mask);
            formatter.setValueContainsLiteralCharacters(false);
            DefaultFormatterFactory dff = new DefaultFormatterFactory(formatter);
            jftf.setFormatterFactory(dff);
        } catch (ParseException e) {
            System.out.println("Erro: " + e.getMessage());
        }

    }
}
