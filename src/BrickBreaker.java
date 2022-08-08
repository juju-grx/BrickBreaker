import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.awt.event.*;

public class BrickBreaker extends JFrame implements KeyListener {

    Surface surface;

    public static int size = 500;

    Thread paintThread;
    Thread playerThread;
    public static Thread ballsThread;

    Process p;

    public static int FPS;

    public BrickBreaker() {
        initUI();
    }

    private void initUI() {
        FPS = 120;

        Map.getInstance();

        Player.getInstance(Color.RED, size / 10, 1, 3);
        Player.setVelocity(120 / FPS);

        surface = new Surface();
        add(surface);

        setTitle("Lab");
        setSize(size + 15, size);
        setLocationRelativeTo(surface);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initGame();
        playerThread.start();
        ballsThread.start();
        paintThread.start();

        addKeyListener(this);
        requestFocus();
    }

    public void initGame() {
        BrickBreaker instance = this;
        paintThread = new Thread() {
            public void run() {
                while (true) {
                    try {
                        sleep((int) 1000 / FPS);
                        SwingUtilities.invokeAndWait(new Runnable() {
                            public void run() {
                                instance.repaint();
                            }
                        });
                    } catch (InterruptedException e) {
                        return;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        playerThread = new Thread() {
            public void run() {
                while (true) {
                    try {
                        sleep((int) 1000 / FPS);
                        SwingUtilities.invokeAndWait(new Runnable() {
                            public void run() {
                                int x = Player.getX();
                                int s = Player.getSize();
                                int dx = (int) Player.getDirection().getX();
                                if ((x > 0 || (x == 0 && dx == 1)) && (x + s < size || (x + s == size && dx == -1))) {
                                    Player.setPosition();
                                    if (Player.getX() > size) {
                                        Player.setValidePosition(size);
                                    } else if (Player.getX() < 0) {
                                        Player.setValidePosition(0);
                                    }
                                }
                            }
                        });
                    } catch (InterruptedException e) {
                        return;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        ballsThread = new Thread() {
            public void run() {
                while (true) {
                    try {
                        sleep((int) 1000 / FPS);
                        SwingUtilities.invokeAndWait(new Runnable() {
                            public void run() {
                                if (Player.hasBall() == null) {
                                    if (Player.getBalls().size() > 0) {
                                        for (Ball ball : Player.getBalls()) {
                                            ball.setPosition();
                                            ball.calculatePositionIfHasCollision();
                                        }
                                        Player.updateBalls();
                                    } else {
                                        try {
                                            paintThread.wait();
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        Player.setLife(-1);
                                    }
                                } else {
                                    Player.hasBall().setPosition(Player.getX() + Player.getSize() / 2 - 2,
                                            Player.getY() - 16);
                                }
                            }
                        });
                    } catch (InterruptedException e) {
                        return;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent evt) {
        switch (evt.getKeyCode()) {
            case 37:
                Player.setDirection(new Vector2D(-1, 0));
                break;
            case 39:
                Player.setDirection(new Vector2D(1, 0));
                break;
            case 27:
                FPS = 1;
                break;
        }
        if (evt.getKeyCode() == 32) {
            if (Player.hasBall() != null) {
                Player.hasBall().setDirection(new Vector2D(0, -1));
                Player.setHasBall(null);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent evt) {
        switch (evt.getKeyCode()) {
            case 37:
                if (Player.getDirection().getX() == -1) {
                    Player.setDirection(new Vector2D(0, 0));
                }
                break;
            case 39:
                if (Player.getDirection().getX() == 1) {
                    Player.setDirection(new Vector2D(0, 0));
                }
                break;
            case 27:
                FPS = 120;
                break;
        }
    }
}
