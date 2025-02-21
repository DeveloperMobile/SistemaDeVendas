
package com.github.developermobile.sistemadevendas.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

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
    
    public static void maskFormatterNubmer(String mask, JFormattedTextField jftf, Class<?> numberFormat) {
        try {
            DecimalFormat formatter = new DecimalFormat(mask);
            NumberFormatter numberFormatter = new NumberFormatter(formatter);
            numberFormatter.setValueClass(numberFormat);
            jftf.setFormatterFactory(new DefaultFormatterFactory(numberFormatter));
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
