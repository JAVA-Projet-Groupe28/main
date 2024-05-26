package com.example.appproject;


/**
 * Num Class inherits from Variable class.
 * This Class is used to create numerical variables.
 */

public class Num extends Variable {

    Double value;

    /**
     * This constructor creates a boolean variable.
     *
     * @param id
     * @param value
     */
    public Num(String id, Double value) {
        super(id);
        this.value = value;
    }

    /**
     * The getter
     *
     * @return value the float value of the <i>Num</i> variable.
     */
    public double getNumValue() {return this.value;}

}
