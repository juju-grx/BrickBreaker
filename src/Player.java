import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class Player {

    private static volatile Player instance = null;

    private static int x = 225;
    private static int y = 450;
    private static float velocity;
    private static Vector2D direction = new Vector2D(0, 0);
    private static Color color;
    private static int size;
    private static int life;
    private static Ball hasBall;

    private static List<Ball> balls = new ArrayList<Ball>();

    private Player(Color _color, int _size, float _velocity, int _life) {
        super();
        color = _color;
        size = _size;
        velocity = _velocity;
        life = _life;
        balls.add(new Ball(x + (size / 2 - 2), y - 16, 120 / BrickBreaker.FPS * 1, 4, 5));
        hasBall = balls.get(0);
    }

    public final static Player getInstance(Color _color, int _size, float _velocity, int _life) {
        if (Player.instance == null) {
            synchronized (Player.class) {
                if (Player.instance == null) {
                    Player.instance = new Player(_color, _size, _velocity, _life);
                }
            }
        }
        return Player.instance;
    }

    public static int getX() {
        return x;
    }

    public static int getY() {
        return y;
    }

    public static void setPosition() {
        x += direction.mult(velocity).getX();
    }

    public static void setValidePosition(int _x) {
        x = _x;
    }

    public static float getVelocity() {
        return velocity;
    }

    public static void setVelocity(float _velocity) {
        velocity = _velocity;
    }

    public static Vector2D getDirection() {
        return direction;
    }

    public static void setDirection(Vector2D _direction) {
        direction = _direction;
    }

    public static Color getColor() {
        return color;
    }

    public static void setColor(Color _color) {
        color = _color;
    }

    public static int getSize() {
        return size;
    }

    public static void setSize(int _size) {
        size = _size;
    }

    public static Ball hasBall() {
        return hasBall;
    }

    public static void setHasBall(Ball _hasBall) {
        hasBall = _hasBall;
    }

    public static int getLife() {
        return life;
    }

    public static void setLife(int _life) {
        life += _life;
        if (life <= 0) {

        }
        if (_life < 0) {
            x = 258;
            y = 446;
            balls.add(new Ball(x + (size / 2 - 2), y - 16, 120 / BrickBreaker.FPS * 2, 4, 15));
            hasBall = balls.get(0);
        }
    }

    public static List<Ball> getBalls() {
        return balls;
    }

    public static void updateBalls() {
        balls = balls.stream().filter(b -> b.isActive()).collect(Collectors.toList());
    }
}
