// 
// Decompiled by Procyon v0.5.36
// 

package jaco.mp3.player.plaf;

import java.awt.image.BufferedImage;
import java.awt.LayoutManager;
import java.awt.Component;
import jaco.mp3.player.c;
import java.awt.event.ActionListener;
import jaco.a.a;
import java.awt.Insets;
import javax.swing.BorderFactory;
import java.awt.Cursor;
import jaco.a.b;
import jaco.mp3.player.MP3Player;
import javax.swing.plaf.ComponentUI;
import javax.swing.JComponent;
import javax.swing.JButton;

public class MP3PlayerUICompact extends MP3PlayerUI
{
    private JButton a;
    
    public static ComponentUI createUI(final JComponent component) {
        return new MP3PlayerUICompact();
    }
    
    @Override
    protected final void a(final MP3Player mp3Player) {
        mp3Player.setOpaque(false);
        final BufferedImage a2;
        final BufferedImage a = jaco.a.b.a(a2 = jaco.a.b.a(this.getClass().getResource("resources/mp3PlayerSoundOn.png")), 0.05f);
        final BufferedImage b = jaco.a.b.b(a2, 0.05f);
        final BufferedImage a4;
        final BufferedImage a3 = jaco.a.b.a(a4 = jaco.a.b.a(this.getClass().getResource("resources/mp3PlayerSoundOff.png")), 0.05f);
        final BufferedImage b2 = jaco.a.b.b(a4, 0.05f);
        (this.a = new JButton()).setCursor(Cursor.getPredefinedCursor(12));
        this.a.setBorder(BorderFactory.createEmptyBorder());
        this.a.setMargin(new Insets(0, 0, 0, 0));
        this.a.setContentAreaFilled(false);
        this.a.setFocusable(false);
        this.a.setFocusPainted(false);
        this.a.setIcon(jaco.a.a.a(a4));
        this.a.setRolloverIcon(jaco.a.a.a(a3));
        this.a.setPressedIcon(jaco.a.a.a(b2));
        this.a.addActionListener(new d(this, mp3Player));
        mp3Player.addMP3PlayerListener(new e(this, a2, a, b, a4, a3, b2));
        mp3Player.add(this.a);
        mp3Player.setLayout(new jaco.mp3.player.plaf.c(this));
    }
}
