// 
// Decompiled by Procyon v0.5.36
// 

package jaco.mp3.player;

import java.awt.Component;
import java.net.URL;
import jaco.mp3.player.plaf.MP3PlayerUICompact;
import java.awt.Color;
import javax.swing.JApplet;

public class MP3PlayerApplet extends JApplet
{
    @Override
    public void init() {
        try {
            try {
                this.getContentPane().setBackground(Color.decode(this.getParameter("background")));
            }
            catch (Exception ex2) {}
            if ("true".equals(this.getParameter("compact"))) {
                MP3Player.setDefaultUI(MP3PlayerUICompact.class);
            }
            final MP3Player comp;
            (comp = new MP3Player()).setRepeat(true);
            String[] split;
            for (int length = (split = this.getParameter("playlist").split(",")).length, i = 0; i < length; ++i) {
                comp.addToPlayList(new URL(this.getCodeBase() + split[i].trim()));
            }
            this.getContentPane().add(comp);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
