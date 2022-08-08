import java.awt.Color;

public class Brick {

    private int x;
    private int y;
    private int value;
    private int size;
    private Color color;

    public Brick(int _x, int _y, int _size, int _value) {
        this.x = _x;
        this.y = _y;
        this.value = _value;
        this.size = _size;
        setColor();
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getSize() {
        return this.size;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int _value) {
        this.value += _value;
        setColor();
        if (value <= 0) {
            Map.getBricks().remove(this);
        }
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor() {
        int[] rgb = new int[] { 255, 0, 0 };
        if (value >= 170) {
            rgb[0] = 255;
            rgb[1] = (255 - ((value - 170) * 3));
            rgb[2] = 0;
        } else if (value >= 85) {
            rgb[0] = (0 + ((value - 85) * 3));
            rgb[1] = 255;
            rgb[2] = 0;
        } else {
            rgb[0] = 0;
            rgb[1] = 255;
            rgb[2] = (255 - value * 3);
        }

        this.color = new Color(rgb[0], rgb[1], rgb[2], 255);
    }

}
