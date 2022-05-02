// 
// Decompiled by Procyon v0.5.36
// 

package jaco.mp3.player.plaf;

import java.awt.event.ActionEvent;
import jaco.mp3.player.MP3Player;
import java.awt.event.ActionListener;

final class b implements ActionListener
{
    private /* synthetic */ MP3PlayerUI a;
    private final /* synthetic */ MP3Player b;
    
    b(final MP3PlayerUI a, final MP3Player b) {
        this.a = a;
        this.b = b;
    }
    
    @Override
    public final void actionPerformed(final ActionEvent actionEvent) {
        final Object source;
        if ((source = actionEvent.getSource()) == this.a.a) {
            this.b.play();
            return;
        }
        if (source == this.a.b) {
            this.b.pause();
            return;
        }
        if (source == this.a.c) {
            this.b.stop();
            return;
        }
        if (source == this.a.d) {
            this.b.skipBackward();
            return;
        }
        if (source == this.a.e) {
            this.b.skipForward();
        }
    }
}
