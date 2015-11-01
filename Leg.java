package com.innovations.simple.simplemiles.maps;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by keiron on 10/28/15.
 */
public class Leg
{
    private List<Step> steps = new ArrayList<>();
    private Distance distance = null;
    private Duration duration = null;
    private LatLng startPoint = null;
    private LatLng endPoint = null;
    private String startAddress = null;
    private String endAddress = null;

    public Leg addStep(Step step){
        steps.add(step);
        return this;
    }

    public List<Step> getSteps(){
        return steps;
    }

    public Leg setDuration(Duration dur)throws FinalModifierException{
        if(duration == null)
            duration = dur;
        else
            throw new FinalModifierException();
        return this;
    }

    public Duration getDuration(){
        return duration;
    }

    public Leg setDistance(Distance dist)throws FinalModifierException{
        if(distance == null)
            distance = dist;
        else
            throw new FinalModifierException();
        return this;
    }

    public Distance getDistance(){
        return distance;
    }

    public Leg setStartPoint(LatLng point)throws FinalModifierException{
        if(startPoint == null)
            startPoint = point;
        else
            throw new FinalModifierException();
        return this;
    }

    public LatLng getStartPoint(){
        return startPoint;
    }

    public Leg setEndPoint(LatLng point)throws FinalModifierException{
        if(endPoint == null)
            endPoint = point;
        else
            throw new FinalModifierException();
        return this;
    }

    public LatLng getEndPoint(){
        return endPoint;
    }

    public Leg setStartAddress(String addr) throws FinalModifierException{
        if(startAddress == null)
            startAddress = addr;
        else
            throw new FinalModifierException();
        return this;
    }

    public String getStartAddress(){
        return startAddress;
    }

    public Leg setEndAddress(String addr) throws FinalModifierException{
        if(endAddress == null)
            endAddress = addr;
        else
            throw new FinalModifierException();
        return this;
    }

    public String getEndAddress(){
        return endAddress;
    }
}
