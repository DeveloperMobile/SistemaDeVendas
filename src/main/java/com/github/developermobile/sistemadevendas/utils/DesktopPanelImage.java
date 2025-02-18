
package com.github.developermobile.sistemadevendas.utils;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;

/**
 *
 * @author tiago
 */
public class DesktopPanelImage extends JDesktopPane {
    private URL path;

    public DesktopPanelImage(URL path) {
        this.path = path;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        ImageIcon img = new ImageIcon(path);
        
        Dimension d = this.getSize();
        
        int x = (int) d.getWidth();
        int y = (int) d.getHeight();
        
        BufferedImage bi = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
        
        // cria uma versão 2D de gráficos para que possamos desenhar a imagem na tela
        Graphics2D g2 = bi.createGraphics();
        
        /* define algumas configurações de interpolação, renderização e antialiasing
         * para melhorar um pouco a qualidade da imagem quando for dimensionada      */
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
         //desenha a imagem dentro do BufferedImagem
         g2.drawImage(img.getImage(), 0, 0, x, y, null);
         
         // com a imagem já desenhada, dispensa essa instância de gráficos
         g2.dispose();
         g.drawImage(bi, 0, 0, this);
    }
    
    
}
