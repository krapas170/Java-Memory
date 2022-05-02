// 
// Decompiled by Procyon v0.5.36
// 

package a.a.a;

import java.awt.image.ColorModel;
import java.awt.image.BufferedImage;
import java.awt.image.Kernel;

public class d extends a
{
    protected float e;
    protected Kernel f;
    
    public d() {
        this(2.0f);
    }
    
    private d(float e) {
        final d d = this;
        e = 2.0f;
        this = d;
        d.e = e;
        final d d2 = this;
        final float n2;
        final int n;
        final int width;
        final float[] data = new float[width = ((n = (int)Math.ceil(n2 = e)) << 1) + 1];
        final float n3 = n2 / 3.0f;
        final float n4 = n3 * 2.0f * n3;
        final float n5 = (float)Math.sqrt(6.2831855f * n3);
        final float n6 = n2 * n2;
        float n7 = 0.0f;
        int n8 = 0;
        for (int i = -n; i <= n; ++i) {
            final float n9;
            if ((n9 = (float)(i * i)) > n6) {
                data[n8] = 0.0f;
            }
            else {
                data[n8] = (float)Math.exp(-n9 / n4) / n5;
            }
            n7 += data[n8];
            ++n8;
        }
        for (int j = 0; j < width; ++j) {
            final float[] array = data;
            final int n10 = j;
            array[n10] /= n7;
        }
        d2.f = new Kernel(width, 1, data);
    }
    
    @Override
    public BufferedImage filter(final BufferedImage bufferedImage, BufferedImage compatibleDestImage) {
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        if (compatibleDestImage == null) {
            compatibleDestImage = this.createCompatibleDestImage(bufferedImage, null);
        }
        final int[] array = new int[width * height];
        final int[] array2 = new int[width * height];
        bufferedImage.getRGB(0, 0, width, height, array, 0, width);
        if (this.e > 0.0f) {
            a(this.f, array, array2, width, height, this.c, this.c && this.d, false, d.a);
            a(this.f, array2, array, height, width, this.c, false, this.c && this.d, d.a);
        }
        compatibleDestImage.setRGB(0, 0, width, height, array, 0, width);
        return compatibleDestImage;
    }
    
    public static void a(final Kernel kernel, final int[] array, final int[] array2, final int n, final int n2, final boolean b, final boolean b2, final boolean b3, final int n3) {
        final float[] kernelData = kernel.getKernelData(null);
        final int n4 = kernel.getWidth() / 2;
        for (int i = 0; i < n2; ++i) {
            int n5 = i;
            final int n6 = i * n;
            for (int j = 0; j < n; ++j) {
                float n7 = 0.0f;
                float n8 = 0.0f;
                float n9 = 0.0f;
                float n10 = 0.0f;
                for (int k = -n4; k <= n4; ++k) {
                    final float n11;
                    if ((n11 = kernelData[n4 + k]) != 0.0f) {
                        int n12;
                        if ((n12 = j + k) < 0) {
                            if (n3 == d.a) {
                                n12 = 0;
                            }
                            else if (n3 == d.b) {
                                n12 = (j + n) % n;
                            }
                        }
                        else if (n12 >= n) {
                            if (n3 == d.a) {
                                n12 = n - 1;
                            }
                            else if (n3 == d.b) {
                                n12 = (j + n) % n;
                            }
                        }
                        final int n14;
                        final int n13 = (n14 = array[n6 + n12]) >>> 24;
                        int n15 = n14 >> 16 & 0xFF;
                        int n16 = n14 >> 8 & 0xFF;
                        int n17 = n14 & 0xFF;
                        if (b2) {
                            final float n18 = n13 * 0.003921569f;
                            n15 *= (int)n18;
                            n16 *= (int)n18;
                            n17 *= (int)n18;
                        }
                        n10 += n11 * n13;
                        n7 += n11 * n15;
                        n8 += n11 * n16;
                        n9 += n11 * n17;
                    }
                }
                if (b3 && n10 != 0.0f && n10 != 255.0f) {
                    final float n19 = 255.0f / n10;
                    n7 *= n19;
                    n8 *= n19;
                    n9 *= n19;
                }
                array2[n5] = ((b ? e.a((int)(n10 + 0.5)) : 255) << 24 | e.a((int)(n7 + 0.5)) << 16 | e.a((int)(n8 + 0.5)) << 8 | e.a((int)(n9 + 0.5)));
                n5 += n2;
            }
        }
    }
    
    @Override
    public String toString() {
        return "Blur/Gaussian Blur...";
    }
}
