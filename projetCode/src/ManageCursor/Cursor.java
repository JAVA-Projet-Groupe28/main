package ManageCursor;
public class Cursor {

    int positionX;
    int positionY;
    float angle;
    int id;
    float thickness;

    boolean hidden;
    Color color;

    public Cursor(int positionX, int positionY, float angle, int id, float thickness, boolean hidden, Color color) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.angle = angle;
        this.id = id;
        this.thickness = thickness;
        this.hidden = hidden;
        this.color = color;
    }

    public void mov(Position position){}

    public void mov(Percentage x, Percentage y){}

    public void position(Position position){
        this.positionX=position.getPositionX();
        this.positionY=position.getPositionY();
    }

    //à determiner
    public void position(Percentage x, Percentage y){}

    public void forward(int value){}

    public void forward(Percentage value){}

    public void backward(int value){}

    public void backward(Percentage value){}

    public void turn(double angle){}

    public void press(Percentage value){}

    public void thick(int value){}

    public void lookAt(int cursorID){}

    public void lookAt(Position position){}

    public void lookAt(Percentage x, Percentage y){}

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

    public float getThickness() {
        return thickness;
    }

    public void setThickness(float thickness) {
        this.thickness = thickness;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
