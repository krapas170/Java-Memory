// 
// Decompiled by Procyon v0.5.36
// 

package jaco.mp3.player.plaf;

import java.awt.event.ActionEvent;
import jaco.mp3.player.MP3Player;
import java.awt.event.ActionListener;

final class d implements ActionListener
{
    private final /* synthetic */ MP3Player a;
    
    d(final MP3PlayerUICompact mp3PlayerUICompact, final MP3Player a) {
        this.a = a;
    }
    
    @Override
    public final void actionPerformed(final ActionEvent actionEvent) {
        if (this.a.isPaused() || this.a.isStopped()) {
            this.a.play();
            return;
        }
        this.a.pause();
    }
}
