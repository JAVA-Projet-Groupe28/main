package com.example.appproject;

import java.util.Objects;

/**
 * The Cursor Class contains the attributes used to describe a Cursor (drawing pen) and the methods used in the
 * Interpreter Class to execute the user's instructions.
 */

public class Cursor {

    int positionX;
    int positionY;

    //reprensents the angle, in degrees from the abscissa, pointed by the cursor
    double direction;
    int id;
    double thickness;
    double opacity;
    boolean hidden;
    Colorj color;

    /**
     * This constructor method creates a cursor with preset attributes
     * @param id The id of the cursor used as a Key in MapCursor Objects.
     */

    public Cursor(int id) {
        this.id = id;

        this.positionX = 500;
        this.positionY = 200;
        this.direction = 0;
        this.thickness = 3;
        this.opacity = 1;
        this.hidden = true;
        this.color = new Colorj(0,0,0);
    }
    public Cursor(int positionX, int positionY, float direction, int id, double thickness, boolean hidden, Colorj color, double opacity) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.direction = direction;
        this.id = id;
        this.thickness = thickness;
        this.opacity = opacity;
        this.hidden = hidden;
        this.color = color;
    }

    /**
     * The position method is used to implement the POS instruction and MOV instruction.
     * When POS is called the position method is used, but when MOV is called a lign is
     * drawn between the last position and the new one.
     */
    public void position(int positionX,int positionY){
        setPositionX(positionX);
        setPositionY(positionY);
    }

    /**
     * The POS and MOV methods can be used with percentages of the dimensions of the canvas.
     * per_x and per_y are Percentage objects which ensure that their value is between 0 and 1.
     * @param per_x Position X on the abscissa in percentage of the width of the canvas.
     * @param per_y Position Y on the ordinate in percentage of the height of the canvas.
     * @param dimensionX Width of the canvas.
     * @param dimensionY Height of the canvas.
     */
    public void position(Percentage per_x, Percentage per_y, int dimensionX, int dimensionY){
        setPositionX((int) Math.floor(per_x.getValue()*dimensionX));
        setPositionY((int) Math.floor(per_y.getValue()*dimensionY));
    }

    /**
     * The forward method move the cursor by "distance" pixels, following the direction it is headed on, which is
     * the "direction" angle from the abscissa.
     * @param distance
     */
    public void forward(int distance){
        this.positionX += distance*Math.cos(Math.toRadians(direction));
        this.positionY += distance*Math.sin(Math.toRadians(direction));
    }

    /**
     *
     * @param value As the "distance" attribute but based of a percentage of the dimension of the canvas.
     * @param dimension The dimension linked to the canvas from which we want the percentage value to be
     *                  related to.
     */
    public void forward(Percentage value, int dimension){
        int distance = (int) Math.floor(value.getValue()*dimension);
        this.positionX += distance*Math.cos(Math.toRadians(direction));
        this.positionY += distance*Math.sin(Math.toRadians(direction));
    }

    public void backward(int value){
        forward(-value);
    }

    public void backward(Percentage value, int dimension) {
        int distance = (int) Math.floor(value.getValue() * dimension);
        positionX -= distance * Math.cos(Math.toRadians(direction));
        positionY -= distance * Math.sin(Math.toRadians(direction));
    }

    /**
     *  Turn the cursor by "angle" degrees, rotating counter clock-wise.
     * The direction is supposed to be in degrees, between 0 and 359, the setDirection method assures that it is the case.
     * The parameter "angle" could be a positive as well as a negative value in degrees.
     */
    public void turn(double angle){
        setDirection(getDirection() + angle);
    }

    /**
     *  The lookat method turns the cursor so it points to the wanted position.
     * @param lookAt_x
     * @param lookAt_y
     */
    public void lookAt(int lookAt_x, int lookAt_y){
        double u = Math.abs(lookAt_x - getPositionX());
        double v = Math.abs(lookAt_y - getPositionY());

        if (lookAt_x==getPositionX()) {
            if (lookAt_y > getPositionY()){setDirection(90);}
            else if (lookAt_y < getPositionY()){setDirection(270);}
        }
        else {
            setDirection(Math.toDegrees(Math.atan(v / u)));
        }
    }

    /**
     * Turns the cursor to point at the "modelCursor".
     * @param modelCursor The cursor to point at.
     */
    public void lookAt(Cursor modelCursor){
        lookAt(modelCursor.getPositionX(), modelCursor.getPositionY());
    }

    public void lookAt(Percentage per_x, Percentage per_y, int width, int height){
        int lookAt_x = (int) Math.floor(per_x.getValue()*width);
        int lookAt_y = (int) Math.floor(per_y.getValue()*height);
        lookAt(lookAt_x,lookAt_y);
    }

    public double getOpacity() {
        return opacity;
    }

    public void setOpacity(double opacity) {
        this.opacity = opacity;
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

    public double getDirection() {
        return direction;
    }

    /**
     * The method checks if the angle from the abscissa is in degrees from 0 to 359, and sets the new value.
     */
    public void setDirection(double direction) {
        this.direction = direction % 360;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getThickness() {
        return thickness;
    }

    public void setThickness(double thickness) {
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


    @Override
    public String toString() {
        return String.format("Cursor[positionX=%d, positionY=%d, angle=%.2f, id=%d, thickness=%.2f, hidden=%b,"+this.color.toString()+"]",
                positionX, positionY, direction, id, thickness, hidden, color);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cursor cursor = (Cursor) obj;
        return id == cursor.id; // It is supposed that the id is unique for each cursor.
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean isVisible(){
        boolean b = this.hidden;
        if (this.hidden) {
            return true;
        }
        else {return false;}

    }


}
