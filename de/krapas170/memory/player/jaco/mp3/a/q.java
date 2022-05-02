// 
// Decompiled by Procyon v0.5.36
// 

package jaco.mp3.a;

import java.io.PrintStream;

public class q extends Exception
{
    private Throwable a;
    
    public q() {
    }
    
    public q(final String message, final Throwable a) {
        super(message);
        this.a = a;
    }
    
    @Override
    public void printStackTrace() {
        this.printStackTrace(System.err);
    }
    
    @Override
    public void printStackTrace(final PrintStream s) {
        if (this.a == null) {
            super.printStackTrace(s);
            return;
        }
        this.a.printStackTrace();
    }
}
