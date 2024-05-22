package com.example.appproject;

import java.util.Objects;

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

    //This constructor method creates a cursor with preset attributes
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

    //**
    // The position method is used to implement the POS instruction and MOV instruction,
    // When POS is called the position method is used, but when MOV is called a lign is
    // drawn between the last position and the new one.
    // */
    public void position(int positionX,int positionY){
        setPositionX(positionX);
        setPositionY(positionY);
    }

    public void position(Percentage per_x, Percentage per_y, int dimensionX, int dimensionY){
        setPositionX((int) Math.floor(per_x.getValue()*dimensionX));
        setPositionY((int) Math.floor(per_y.getValue()*dimensionY));
    }

    public void forward(int distance){
        this.positionX += distance*Math.cos(Math.toRadians(direction));
        this.positionY += distance*Math.sin(Math.toRadians(direction));
    }

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

    //**
    // The direction is supposed to be in degrees, between 0 and 359, the setDirection method assures that it is the case.
    // The parameter "angle" could be a positive as well as a negative value in degrees.
    // */
    public void turn(double angle){
        setDirection(getDirection() + angle);
    }

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

    public void lookAt(Cursor modelCursor){
        lookAt(modelCursor.getPositionX(), modelCursor.getPositionY());
    }

    public void lookAt(Percentage per_x, Percentage per_y, int dimensionX, int dimensionY){
        int lookAt_x = (int) Math.floor(per_x.getValue()*dimensionX);
        int lookAt_y = (int) Math.floor(per_y.getValue()*dimensionY);
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

    //The angle from the abscissa is supposed to be in degrees from 0 to 359
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
        return id == cursor.id; // Supposons que l'ID soit unique pour chaque curseur
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
