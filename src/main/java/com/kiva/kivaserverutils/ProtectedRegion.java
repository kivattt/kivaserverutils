package com.kiva.kivaserverutils;

import com.fox2code.foxloader.network.ChatColors;

public class ProtectedRegion {
    public int dimension;
    public int xMin, yMin, zMin;
    public int xMax, yMax, zMax;

    public ProtectedRegion(){}

    public ProtectedRegion(String fromString){
        String[] values = fromString.split(" ");
        try {
            xMin = Integer.parseInt(values[0]);
            yMin = Integer.parseInt(values[1]);
            zMin = Integer.parseInt(values[2]);

            xMax = Integer.parseInt(values[3]);
            yMax = Integer.parseInt(values[4]);
            zMax = Integer.parseInt(values[5]);

            dimension = Integer.parseInt(values[6]);
        } catch(NumberFormatException e){
            e.printStackTrace();
        }
    }

    public String toWriteableDataString(){
        return xMin + " " + yMin + " " + zMin + " " + xMax + " " + yMax + " " + zMax + " " + dimension;
    }

    @Override
    public String toString() {
        return xMin + " " + yMin + " " + zMin + ChatColors.GRAY + " to " + ChatColors.RESET + xMax + " " + yMax + " " + zMax + ChatColors.GRAY + " (" + Coordinate.dimensionToString(dimension) + ")";
    }
}
