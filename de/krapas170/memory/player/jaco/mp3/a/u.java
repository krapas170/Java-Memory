// 
// Decompiled by Procyon v0.5.36
// 

package jaco.mp3.a;

public final class u extends q
{
    private u(final String s, final Throwable t) {
        super(s, t);
    }
    
    public u(int i, final Throwable t) {
        i = i;
        this("Decoder errorcode " + Integer.toHexString(i), t);
    }
}
