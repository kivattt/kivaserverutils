package com.kiva.kivaserverutils;

public class Coordinate {
    public double x, y, z;
    public Integer dimension;
    public static String dimensionToString(final Integer dimension){
        switch(dimension){
            case -1:
                return "nether";
            case 0:
                return "overworld";
            default:
                return "unknown dimension";
        }
    }

    public Coordinate(){}

    public Coordinate(String str){
        this.fromString(str);
    }

    @Override
    public String toString() {
        return x + " " + y + " " + z + " " + dimension;
    }

    public String toStringXYZInt(){return (int)x + " " + (int)y + " " + (int)z;}

    // TODO Use temporary values?
    public void fromString(final String str){
        String[] values = str.split(" ");
        try {
            x = Double.parseDouble(values[0]);
            y = Double.parseDouble(values[1]);
            z = Double.parseDouble(values[2]);

            if (values.length >= 4)
                dimension = Integer.parseInt(values[3]);
            else
                dimension = 0; // Let's assume overworld for old playerhomes.txt format
        } catch(NumberFormatException e){
            e.printStackTrace();
        }
    }
}
