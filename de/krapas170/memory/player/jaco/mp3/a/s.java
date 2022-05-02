// 
// Decompiled by Procyon v0.5.36
// 

package jaco.mp3.a;

public final class s extends q
{
    private int a;
    
    public s(final String s, final Throwable t) {
        super(s, t);
        this.a = 256;
    }
    
    public s(final int n, final Throwable t) {
        this("Bitstream errorcode " + Integer.toHexString(n), t);
        this.a = n;
    }
    
    public final int a() {
        return this.a;
    }
}
