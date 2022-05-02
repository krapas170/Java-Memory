// 
// Decompiled by Procyon v0.5.36
// 

package jaco.mp3.player.plaf;

import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import jaco.a.b;
import jaco.mp3.player.MP3Player;
import javax.swing.plaf.ComponentUI;
import javax.swing.JComponent;
import javax.swing.JButton;
import javax.swing.plaf.basic.BasicPanelUI;

public class MP3PlayerUI extends BasicPanelUI
{
    private JButton a;
    private JButton b;
    private JButton c;
    private JButton d;
    private JButton e;
    
    public static ComponentUI createUI(final JComponent component) {
        return new MP3PlayerUI();
    }
    
    @Override
    public final void installUI(final JComponent c) {
        super.installUI(c);
        this.a((MP3Player)c);
    }
    
    @Override
    public final void uninstallUI(final JComponent c) {
        super.uninstallUI(c);
        final MP3Player mp3Player;
        (mp3Player = (MP3Player)c).removeAll();
        mp3Player.removeAllMP3PlayerListeners();
    }
    
    protected void a(final MP3Player mp3Player) {
        mp3Player.setOpaque(false);
        this.a = new a(this, jaco.a.b.a(this.getClass().getResource("resources/mp3PlayerPlay.png")));
        this.b = new a(this, jaco.a.b.a(this.getClass().getResource("resources/mp3PlayerPause.png")));
        this.c = new a(this, jaco.a.b.a(this.getClass().getResource("resources/mp3PlayerStop.png")));
        this.d = new a(this, jaco.a.b.a(this.getClass().getResource("resources/mp3PlayerSkipBackward.png")));
        this.e = new a(this, jaco.a.b.a(this.getClass().getResource("resources/mp3PlayerSkipForward.png")));
        final jaco.mp3.player.plaf.b l = new jaco.mp3.player.plaf.b(this, mp3Player);
        this.a.addActionListener(l);
        this.b.addActionListener(l);
        this.c.addActionListener(l);
        this.d.addActionListener(l);
        this.e.addActionListener(l);
        mp3Player.setLayout(new FlowLayout(1, 1, 1));
        mp3Player.add(this.a);
        mp3Player.add(this.b);
        mp3Player.add(this.c);
        mp3Player.add(this.d);
        mp3Player.add(this.e);
    }
}
