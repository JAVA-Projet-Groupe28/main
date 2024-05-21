package com.example.appproject;
/*import Exception.IncorrectPercentageException;*/
public class Percentage
{
    double value;

    public Percentage(double value) /*throws IncorrectPercentageException */{
        if(value<=1.0&&value>=0.0)//les pourcentages sont des rééls entre 0 et 1
        {
            this.value=value;
        }
        /*else throw new IncorrectPercentageException("Pourcentage non compris entre 0 et 1");*/
    }

    public void setValue(double value) /*throws IncorrectPercentageException*/{
        if(value<=1.0&&value>=0.0)//les pourcentages sont des rééls entre 0 et 1
        {
            this.value=value;
        }
        /*else throw new IncorrectPercentageException("Pourcentage non compris entre 0 et 1");*/
    }

    public double getValue() {
        return value;
    }
}
