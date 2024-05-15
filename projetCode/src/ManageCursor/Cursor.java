package ManageCursor;
public class Cursor {

    int positionX;
    int positionY;
    float angle;
    int id;
    int thickness;

    boolean hidden;
    Colorj color;
    private Test testInstance;
    public Cursor(int positionX, int positionY, float angle, int id, int thickness, boolean hidden, Colorj color) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.angle = normalizeAngle(angle);
        this.id = id;
        this.thickness = thickness;
        this.hidden = hidden;
        this.color = color;
    }

    public void mov(Position position) {
    }

    public void mov(Percentage x, Percentage y) {
    }

    public void position(Position position) {
        this.positionX = position.getPositionX();
        this.positionY = position.getPositionY();
    }

    //à determiner
    public void position(Percentage x, Percentage y) {
    }

    private float normalizeAngle(float angle) {
        while (angle < 0) {
            angle += 360;
        }
        while (angle >= 360) {
            angle -= 360;
        }
        return angle;
    }

    public void forward(int value) {
        double angleRadians = Math.toRadians(this.angle);

        double deltaX = value * Math.cos(-angleRadians);
        double deltaY = value * Math.sin(-angleRadians);
        this.positionX = (int) Math.round(this.positionX + deltaX);
        this.positionY = (int) Math.round(this.positionY + deltaY);


    }

    public void forward(Percentage value) {
    }

    public void backward(int value) {
        this.forward(-value);
    }

    public void backward(Percentage value) {
    }

    public void turn(double angle) {
        this.angle += angle;
        normalizeAngle(this.angle);
    }

    public void press(Percentage value) {
    }

    public void thick(int value) {
    }

    public void lookAt(int cursorID) {
    }

    public void lookAt(Position position) {
    }

    public void lookAt(Percentage x, Percentage y) {
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getThickness() {
        return this.thickness;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public int[] getColorj() {
        return this.color.getRgb();
    }


    public void setColor(Colorj color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return String.format("Cursor[positionX=%d, positionY=%d, angle=%.2f, id=%d, thickness=%d, hidden=%b,"+this.color.toString()+"]",
                positionX, positionY, angle, id, thickness, hidden, color);
    }

}
}
