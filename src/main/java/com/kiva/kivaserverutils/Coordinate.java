package com.kiva.kivaserverutils;

public class Coordinate {
    public double x, y, z;

    public Coordinate(){}

    public Coordinate(String str){
        this.fromString(str);
    }

    @Override
    public String toString() {
        return x + " " + y + " " + z;
    }

    // TODO Use temporary values?
    public void fromString(final String str){
        String[] values = str.split(" ");
        try {
            x = Double.parseDouble(values[0]);
            y = Double.parseDouble(values[1]);
            z = Double.parseDouble(values[2]);
        } catch(NumberFormatException e){
            e.printStackTrace();
        }
    }
}
