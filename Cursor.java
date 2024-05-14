package projetgl.chromatynk;

public class Cursor {
    private int x, y;
    private double direction; // en degrés
    private String color;
    private int thickness;

    public Cursor() {
        reset();
    }

    public void reset() {
        this.x = 250;
        this.y = 250;
        this.direction = 0;
        this.color = "#000000";
        this.thickness = 5;
    }

    public void forward(int pixels) {
        x += (int) (pixels * Math.cos(Math.toRadians(direction)));
        y += (int) (pixels * Math.sin(Math.toRadians(direction)));
    }

    public void backward(int pixels) {
        forward(-pixels);
    }

    public void turn(double angle) {
        direction += angle;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getColor() {
        return color;
    }

    public double getDirection() {
        return direction;
    }

    public int getThickness() {
        return thickness;
    }
}
