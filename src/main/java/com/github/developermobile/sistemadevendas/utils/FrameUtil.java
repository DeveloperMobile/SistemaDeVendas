
package com.github.developermobile.sistemadevendas.utils;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

/**
 *
 * @author tiago
 */
public class FrameUtil {
    private static void centralizaJanela(JInternalFrame internalFrame, JFrame frame) {
        internalFrame.setLocation((frame.getWidth() - internalFrame.getWidth()) / 2,
                (frame.getHeight() - internalFrame.getHeight()) / 2); 
    }
    
    public static void abreInternalFrame(JDesktopPane pane, JInternalFrame internalFrame, JFrame frame) {
        pane.add(internalFrame);
        centralizaJanela(internalFrame, frame);
        internalFrame.setVisible(true); 
    }
}
