// 
// Decompiled by Procyon v0.5.36
// 

package jaco.mp3.a;

public final class g implements Cloneable
{
    private f a;
    
    public g() {
        this.a = new f();
    }
    
    public final Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException obj) {
            throw new InternalError(this + ": " + obj);
        }
    }
    
    public final f a() {
        return this.a;
    }
}
