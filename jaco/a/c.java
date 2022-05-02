// 
// Decompiled by Procyon v0.5.36
// 

package jaco.a;

import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Component;
import java.awt.image.BufferedImage;
import javax.swing.Icon;

final class c implements Icon
{
    private BufferedImage a;
    
    private c(final BufferedImage a, final int i) {
        this.a = a;
    }
    
    @Override
    public final void paintIcon(final Component component, final Graphics graphics, final int n, final int n2) {
        graphics.drawImage(this.a, n, n2, component);
    }
    
    @Override
    public final int getIconWidth() {
        return this.a.getWidth();
    }
    
    @Override
    public final int getIconHeight() {
        return this.a.getHeight();
    }
}
