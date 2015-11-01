package com.innovations.simple.simplemiles.maps;

/**
 * Created by keiron on 10/28/15.
 */
public class Distance
{
    private double value = -1f;
    private String text = null;

    public Distance(double val, String txt){
        value = val;
        text = txt;
    }

    public double valueInMeters(){
        return value;
    }

    public String text(){
        return text;
    }

    public double valueInMiles(){
        return value * 0.00062137f;
    }

    public double valueInKM(){
        return value / 1000;
    }

    public float getSimpleValue(){
        String[] s = text.split(" ");
        return Float.valueOf(s[0]);
    }

    public Distance add(Distance d){
        double v = value + d.valueInMeters();
        String[] tArray = text.split(" ");
        String[] dArray = d.text().split(" ");
        double t = Double.valueOf(tArray[0]) + Double.valueOf(dArray[0]);
        return new Distance(v, t + " " + dArray[1]);
    }
}
