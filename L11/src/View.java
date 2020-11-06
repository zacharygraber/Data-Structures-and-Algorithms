import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class View extends JPanel {
    private final int DIM = 800;
    private Game game;
    private int delay;

    public View(Game game, int delay) {
        this.game = game;
        this.delay = delay;

        setDoubleBuffered(true);

        Timer timer = new Timer(delay, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.step();
                repaint();
            }
        });
        timer.start();
    }

    public Dimension getPreferredSize() {
        return new Dimension(DIM, DIM);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.draw((Graphics2D)g, DIM);
    }
}
