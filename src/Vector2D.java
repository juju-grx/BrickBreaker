class Vector2D {

    float x;
    float y;

    public Vector2D(float _x, float _y) {
        this.x = _x;
        this.y = _y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float _x) {
        this.x = _x;
    }

    public void setY(float _y) {
        this.y = _y;
    }

    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.getX(), this.y + other.getY());
    }

    public Vector2D mult(Vector2D other) {
        return new Vector2D(this.x * other.getX(), this.y * other.getY());
    }

    public Vector2D mult(float other) {
        return new Vector2D(this.x * other, this.y * other);
    }

    public Vector2D normalize() {
        float max = Math.max(Math.abs(this.x), Math.abs(this.y));
        float x0 = this.x / max;
        float y0 = this.y / max;
        return new Vector2D(x0, y0);
    }

    public void print() {
        System.out.print("x:" + this.x + " | y:" + this.y);
    }
}