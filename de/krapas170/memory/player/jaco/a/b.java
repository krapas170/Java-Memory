// 
// Decompiled by Procyon v0.5.36
// 

package jaco.a;

import java.awt.image.PixelGrabber;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;
import java.net.URL;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.awt.RenderingHints;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public final class b
{
    static {
        try {
            final float[] data = new float[9];
            for (int i = 0; i < data.length; ++i) {
                data[i] = 0.11111111f;
            }
            final ConvolveOp convolveOp = new ConvolveOp(new Kernel(3, 3, data), 1, null);
        }
        catch (Throwable thrown) {
            throw new ExceptionInInitializerError(thrown);
        }
    }
    
    private static BufferedImage a(final int n, final int n2, final boolean b) {
        try {
            return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(n, n2, b ? 3 : 1);
        }
        catch (Throwable t) {
            return new BufferedImage(n, n2, b ? 2 : 1);
        }
    }
    
    public static BufferedImage a(final URL location) {
        final Image image;
        if ((image = new ImageIcon(location).getImage()).getWidth(null) == -1 || image.getHeight(null) == -1) {
            return null;
        }
        final Image image2 = image;
        final BufferedImage a;
        final Graphics2D graphics;
        (graphics = (a = a(0 + image2.getWidth(null), 0 + image2.getHeight(null), a(image2))).createGraphics()).drawImage(image2, 0, 0, null);
        graphics.dispose();
        return a;
    }
    
    private static BufferedImage c(final BufferedImage bufferedImage, final float n) {
        final BufferedImage a = a(bufferedImage.getWidth(), bufferedImage.getHeight(), bufferedImage.getColorModel().hasAlpha());
        final a.a.a.b b;
        (b = new a.a.a.b()).a(n);
        b.filter(bufferedImage, a);
        return a;
    }
    
    public static BufferedImage a(final BufferedImage bufferedImage, final float n) {
        return c(bufferedImage, n);
    }
    
    public static BufferedImage b(final BufferedImage bufferedImage, final float n) {
        return c(bufferedImage, -n);
    }
    
    private static boolean a(Image img) {
        if (img instanceof BufferedImage) {
            return ((BufferedImage)img).getColorModel().hasAlpha();
        }
        img = (Image)new PixelGrabber(img, 0, 0, 1, 1, false);
        try {
            ((PixelGrabber)img).grabPixels();
        }
        catch (InterruptedException ex) {}
        return ((PixelGrabber)img).getColorModel().hasAlpha();
    }
}
