
package com.github.developermobile.sistemadevendas.utils;

import javax.swing.Icon;
import javax.swing.JOptionPane;

/**
 *
 * @author tiago
 */
public class JOPUtil {
    public static void message(String message, String title, Integer messageType) {
        JOptionPane.showMessageDialog(null, message, title, messageType);
    }
    
    public static Integer confirmMessage(String message, String title, int optionType, int messageType) {
        return JOptionPane.showConfirmDialog(null, 
                message,  
                title, 
                optionType, 
                messageType);
    }
}
