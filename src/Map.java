import java.util.ArrayList;
import java.util.List;

class Map {

    private static volatile Map instance = null;

    private static List<Brick> bricks = new ArrayList<Brick>();

    private Map() {
        super();
        generation();
    }

    public final static Map getInstance() {
        if (Map.instance == null) {
            synchronized (Map.class) {
                if (Map.instance == null) {
                    Map.instance = new Map();
                }
            }
        }
        return Map.instance;
    }

    public static void generation() {
        int i = 1;
        int c = 10;
        int l = 5;
        int sb = BrickBreaker.size / 15;
        int initX = (BrickBreaker.size / 2) - ((c * sb) / 2);
        for (int x = 0; x < c; x++) {
            for (int y = 0; y < l; y++) {
                bricks.add(new Brick(initX + x * sb, initX / 2 + y * sb, sb, i * 5));
                i++;
            }
        }
    }

    public static List<Brick> getBricks() {
        return bricks;
    }

    public static Brick getBrick(int _x, int _y) {
        for (Brick brick : bricks) {
            if (brick.getX() == _x && brick.getY() == _y) {
                return brick;
            }
        }
        return null;
    }
}