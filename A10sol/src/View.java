import java.awt.*;
import javax.swing.*;

public class View extends JPanel {
    private final int DIM = 800;
    private final Game game;

    public View(Game game, int delay) {
        this.game = game;

        setDoubleBuffered(true);

        Timer timer = new Timer(delay, e -> {
            game.step();
            repaint();
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
