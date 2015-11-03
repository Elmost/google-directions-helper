package com.innovations.simple.simplemiles.maps;

/**
 * Created by keiron on 10/28/15.
 */
import java.util.List;
import java.util.ArrayList;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

public class Route
{
    private List<Leg> legs = new ArrayList<>();
    private String summary = null;
    private Duration duration;
    private Distance distance;
    private String encodedPolyline = null;
    private String copyrights = null;
    private LatLng northeast = null;
    private LatLng southwest = null;

    public Route(){
        duration = new Duration(0);
        distance = new Distance(0, "0 ");
    }

    public Route addLeg(Leg leg){
        duration.add(leg.getDuration());
        distance = distance.add(leg.getDistance());
        legs.add(leg);
        return this;
    }

    public List<Leg> getLegs(){
        return legs;
    }

    public Route setSummary(String summary) throws FinalModifierException{
        if(this.summary == null)
            this.summary = summary;
        else
            throw new FinalModifierException();
        return this;
    }

    public String getSummary(){
        return summary;
    }

    public Route setEncodedOverviewPolyline(String line) throws FinalModifierException{
        if(encodedPolyline == null)
            encodedPolyline = line;
        else
            throw new FinalModifierException();
        return this;
    }

    public String getEncodedOverviewPolyline(){
        return encodedPolyline;
    }

    public PolylineOptions getOverviewPolyline(float width, int color){
        PolylineOptions po = new PolylineOptions();
        po.addAll(MapUtils.decode(encodedPolyline)).width(width).color(color);
        return po;
    }

    public Route setCopyrights(String txt) throws FinalModifierException{
        if(copyrights == null)
            copyrights = txt;
        else
            throw new FinalModifierException();
        return this;
    }

    public String getCopyrights(){
        return copyrights;
    }

    public Route setNortheast(LatLng ne) throws FinalModifierException{
        if(northeast == null)
            northeast = ne;
        else
            throw new FinalModifierException();
        return this;
    }

    public LatLng getNortheast(){
        return northeast;
    }

    public Route setSouthwest(LatLng sw) throws FinalModifierException{
        if(southwest == null)
            southwest = sw;
        else
            throw new FinalModifierException();
        return this;
    }

    public LatLng getSouthwest(){
        return southwest;
    }

    public Duration getDuration(){
        return duration;
    }

    public void setDuration(Duration dur){
        duration = dur;
    }

    public Distance getDistance(){
        return distance;
    }

    public void setDistance(Distance dis){
        distance = dis;
    }
}
