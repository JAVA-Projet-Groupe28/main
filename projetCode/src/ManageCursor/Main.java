package ManageCursor;
import Exception.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        try {
            Color bleuClair = new Color("#1B4231");
            Color rouge = new Color(256, 50, 1);
            Color majenta = new Color(0.3, 0.5, 0.1);

            System.out.println(rouge.rgbToWeb());
        }
        catch(OutOfRangeRGBException e){System.out.println(e.getMessage());}

        try {
            Percentage dixPourcent = new Percentage(0.1);
            Percentage centVingtPourcent = new Percentage(1.2);
            System.out.println(dixPourcent.getValue());
            System.out.println(centVingtPourcent.getValue());
        }
        catch(IncorrectPercentageException e){System.out.println(e.getMessage());}
        catch(Exception e){}
    }
}