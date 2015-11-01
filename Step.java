package com.innovations.simple.simplemiles.maps;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by keiron on 10/28/15.
 */
public class Step
{
    private String travelMode = null;
    private String htmlInstructions = null;
    private Duration duration = null;
    private Distance distance = null;
    private LatLng startPoint = null;
    private LatLng endPoint = null;
    private String polyline = null;

    public Step setTravelMode(String mode)throws FinalModifierException{
        if(travelMode == null)
            travelMode = mode;
        else
            throw new FinalModifierException();
        return this;
    }

    public String getTravelMode(){
        return travelMode;
    }

    public Step setHtmlInstructions(String html)throws FinalModifierException{
        if(htmlInstructions == null)
            htmlInstructions = html;
        else
            throw new FinalModifierException();
        return this;
    }

    public String getHtmlInstructions(){
        return htmlInstructions;
    }

    public Step setDuration(Duration dur)throws FinalModifierException{
        if(duration == null)
            duration = dur;
        else
            throw new FinalModifierException();
        return this;
    }

    public Duration getDuration(){
        return duration;
    }

    public Step setDistance(Distance dist)throws FinalModifierException{
        if(distance == null)
            distance = dist;
        else
            throw new FinalModifierException();
        return this;
    }

    public Distance getDistance(){
        return distance;
    }

    public Step setStartPoint(LatLng point)throws FinalModifierException{
        if(startPoint == null)
            startPoint = point;
        else
            throw new FinalModifierException();
        return this;
    }

    public LatLng getStartPoint(){
        return startPoint;
    }

    public Step setEndPoint(LatLng point)throws FinalModifierException{
        if(endPoint == null)
            endPoint = point;
        else
            throw new FinalModifierException();
        return this;
    }

    public LatLng getEndPoint(){
        return endPoint;
    }

    public Step setPolyline(String line)throws FinalModifierException{
        if(polyline == null)
            polyline = line;
        else
            throw new FinalModifierException();
        return this;
    }

    public String getPolyline(){
        return polyline;
    }
}
