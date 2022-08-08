import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;

class Surface extends JPanel {

    Graphics2D g2d;

    int fontSize;

    public Surface() {
        super();
    }

    private void doDrawing() {

        g2d.setFont(new Font("TimesRoman", Font.BOLD, 20));

        g2d.drawArc(5, 5, 40, 40, 0, 360);
        g2d.drawString("" + Player.getLife(), 20, 30);
        g2d.drawString("" + Player.getBalls().get(0).getX() + ":" +
                Player.getBalls().get(0).getY(), 100, 15);
        g2d.drawString("" + Player.getX() + ":" + Player.getY(), 100, 30);

        g2d.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));

        g2d.fillRect(Player.getX(), Player.getY(), Player.getSize(), Player.getSize() / 10);

        List<Ball> balls = Player.getBalls();

        for (Ball ball : balls) {
            g2d.fillArc(ball.getX(), ball.getY(), ball.getSize(), ball.getSize(), 0, 360);
        }
        for (Brick brick : Map.getBricks()) {

            int x0 = brick.getX();
            int y0 = brick.getY();
            int size = brick.getSize();

            g2d.setColor(brick.getColor());
            g2d.fillRect(x0, y0, size, size);

            g2d.setColor(new Color(0, 0, 0, 255));
            g2d.drawRect(x0, y0, size, size);
            this.fontSize = size / 3 - 1;
            int fs = ("" + brick.getValue()).length();
            g2d.drawString("" + brick.getValue(),
                    (int) (x0 + size / 2 - (fontSize / 4 * fs)),
                    (int) ((y0 + size) - (size * 0.3)));
        }
    }

    public void rePaint() {
        doDrawing();
    }

    @Override
    public void paintComponent(Graphics g) {
        g2d = (Graphics2D) g;
        super.paintComponent(g);
        doDrawing();
    }

}