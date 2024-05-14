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
        this.angle = normalizeAngle(angle);
        this.id = id;
        this.thickness = thickness;
        this.hidden = hidden;
        this.color = color;
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
    public void mov(Position position){}

    public void mov(Percentage x, Percentage y){}

    public void position(Position position){
        this.positionX=position.getPositionX();
        this.positionY=position.getPositionY();
    }

    //à determiner
    public void position(Percentage x, Percentage y){}

    public void forward(int value){
    if ((this.angle >=0) && (this.angle <=90)) {
        this.positionX = (int) Math.round(this.positionX + (value*Math.cos(Math.toRadians(this.angle))));
        this.positionY = (int) Math.round(this.positionY + (value*Math.sin(Math.toRadians(this.angle))));
    }
    else if ((this.angle >90) && (this.angle <=180)) {
        this.positionX = (int) Math.round(this.positionX + (value*Math.cos(Math.toRadians(-this.angle))));
        this.positionY = (int) Math.round(this.positionY + (value*Math.sin(Math.toRadians(this.angle))));
    }
    else if ((this.angle >180) && (this.angle <=270)) {
        this.positionX = (int) Math.round(this.positionX + (value*Math.cos(Math.toRadians(-this.angle))));
        this.positionY = (int) Math.round(this.positionY + (value*Math.sin(Math.toRadians(-this.angle))));
    }
    else if ((this.angle >270) && (this.angle <=359)) {
        this.positionX = (int) Math.round(this.positionX + (value*Math.cos(Math.toRadians(this.angle))));
        this.positionY = (int) Math.round(this.positionY + (value*Math.sin(Math.toRadians(-this.angle))));
    }
    /*Il faut rajouter le fait que l'on dessine ici*/
    /*surement un appelle de fonction de javafx pour mettre un trait dans une liste*/
}

    public void forward(Percentage value){}

    public void backward(int value){}

    public void backward(Percentage value){}

    public void turn(double angle){
    this.angle+=angle;
    normalizeAngle(this.angle);
}

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
        @Override
    public String toString() {
        return String.format("Cursor[positionX=%d, positionY=%d, angle=%.2f, id=%d, thickness=%.2f, hidden=%b, color=%s]",
                positionX, positionY, angle, id, thickness, hidden, color);
    }
}
