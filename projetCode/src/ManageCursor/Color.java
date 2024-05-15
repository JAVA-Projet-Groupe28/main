package ManageCursor;
import Exception.OutOfRangeRGBException;
public class Colorj {
    int rgb[] = new int[3];
    String web;
    double hsl[] = new double[3];

    public Colorj(String web) {
        this.web = web;
    }

    public Colorj(double hue, double saturation, double luminosity) {
        this.hsl[0] = hue;
        this.hsl[1] = saturation;
        this.hsl[2] = luminosity;

        //calcul du code web


    }

    public Colorj(int red, int green, int blue)/* throws OutOfRangeRGBException*/ {
        if (red >= 0 && red <= 255 && green >= 0 && green <= 255 && blue >= 0 && blue <= 255) {
            this.rgb[0] = red;
            this.rgb[1] = green;
            this.rgb[2] = blue;
        }
        /*else throw new OutOfRangeRGBException("Couleurs non compris entre 0 et 255");*/

        //calcul du code web
        String redHex = Integer.toHexString(red);
        String blueHex = Integer.toHexString(green);
        String greenHex = Integer.toHexString(blue);

        this.web = "#" + redHex + blueHex + greenHex;
    }

    public String rgbToWeb()/* throws OutOfRangeRGBException*/ {
        int red = this.rgb[0];
        int green = this.rgb[1];
        int blue = this.rgb[2];
        //Convert rgb format to web in hexadecimal
        if (red >= 0 && red <= 255 && green >= 0 && green <= 255 && blue >= 0 && blue <= 255) {
            String redHex = Integer.toHexString(red);
            String blueHex = Integer.toHexString(green);
            String greenHex = Integer.toHexString(blue);

            this.web = "#" + redHex + blueHex + greenHex;

            //Method can be used as a getter
            return this.web;
        }
        /*else throw new OutOfRangeRGBException("RGB format : color not included between 0 and 255");*/
        return this.web;
    }

    public String getWeb() {
        return this.web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public void setRgb(int red, int green, int blue) {
        this.rgb[0] = red;
        this.rgb[1] = green;
        this.rgb[2] = blue;
    }

    public int[] getRgb() {
        return this.rgb;
    }

    public void sethsl(double hue, double saturation, double luminosity) {
        this.hsl[0] = hue;
        this.hsl[1] = saturation;
        this.hsl[2] = luminosity;
    }


    @Override
    public String toString() {
        return ("R:"+this.getRgb()[0]+", G:"+this.getRgb()[1]+",B:"+this.getRgb()[2]);
    }
}
