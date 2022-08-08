import java.awt.Color;
import java.util.Random;

public class Ball {
    private int x;
    private int y;
    private int lastX;
    private int lastY;
    private float velocity;
    private Vector2D direction = new Vector2D(0, 0);
    private Color color = new Color(0, 0, 0, 0);
    private int size;
    private int force;
    private boolean active = true;

    public Ball(int _x, int _y, float _velocity, int _size, int _force) {
        this.x = _x;
        this.y = _y;
        this.velocity = _velocity;
        this.size = _size;
        this.force = _force;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int _x, int _y) {
        setLastPosition();
        x = _x;
        this.y = _y;
    }

    public void setPosition() {
        setLastPosition();
        Vector2D dir = direction.normalize().mult(velocity);
        x += Math.round(dir.getX());
        y += Math.round(dir.getY());
    }

    private void setLastPosition() {
        this.lastX = this.x;
        this.lastY = this.y;
    }

    public int getForce() {
        return force;
    }

    public void setForce(int _force) {
        this.force = _force;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int _size) {
        this.size = _size;
    }

    public void setDirection(Vector2D _direction) {
        this.direction = _direction;
    }

    public void setVelocity(float _velocity) {
        this.velocity = _velocity;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color _color) {
        this.color = _color;
    }

    public Boolean isActive() {
        return active;
    }

    public void calculateBounceDirection(Boolean vertical) {
        float x0 = direction.getX();
        float y0 = direction.getY();
        Random rand = new Random();
        if (vertical) {
            if (lastX < x) {
                x0 = rand.nextFloat() + 2;
                x0 *= -1;
            } else {
                x0 = rand.nextFloat() - 2;
                x0 = Math.abs(x0);
            }
        } else {
            if (lastY < y) {
                y0 = rand.nextFloat() + 2;
                y0 *= -1;
            } else {
                y0 = rand.nextFloat() - 2;
                y0 = Math.abs(y0);
            }
        }
        direction = new Vector2D(x0, y0);
    }

    public void calculatePositionIfHasCollision() {

        if (y > 500) {
            kill();
        } else if (x <= 0 || x >= 500) {
            calculateBounceDirection(true);
        } else if (y <= 0) {
            calculateBounceDirection(false);
        } else {
            int sp = Player.getSize();
            int pX = Player.getX();
            int pY = Player.getY();
            if (y >= pY && y <= pY + sp / 10 && x >= pX && x <= pX + sp) {
                if (x < pX + (sp * 0.3)) {
                    direction = new Vector2D(-1, -1);
                } else if (x >= pX + (sp * 0.3) && x <= pX + sp - (sp * 0.3)) {
                    calculateBounceDirection(false);
                } else if (x > pX + sp - (sp * 0.3)) {
                    direction = new Vector2D(1, -1);
                }
                return;
            }

            for (Brick brick : Map.getBricks()) {
                int sb = brick.getSize();
                int bX = brick.getX();
                int bY = brick.getY();
                if (x >= bX && x <= bX + sb && y >= bY && y <= bY + sb) {

                    if (y + 1 <= bY + sb && y - 1 >= bY) {
                        calculateBounceDirection(true);
                    } else {
                        if (x + 1 <= bX + sb) {
                            if (x - 1 >= bX) {
                                calculateBounceDirection(false);
                            } else {
                                if (y + 1 <= bY) {
                                    direction = new Vector2D(-1, -1);
                                } else {
                                    direction = new Vector2D(-1, 1);
                                }
                            }
                        } else {
                            if (y + 1 <= bY) {
                                direction = new Vector2D(1, -1);
                            } else {
                                direction = new Vector2D(1, 1);
                            }
                        }
                    }

                    brick.setValue(this.force * -1);
                    return;
                }
            }
        }
    }

    public void kill() {
        this.active = false;
    }
}