package com.innovations.simple.simplemiles.maps;

import java.util.ArrayList;
import java.util.List;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class RouteInformation
{
    private List<Route> routes = new ArrayList<>();
    private double north = Double.NEGATIVE_INFINITY;
    private double east = Double.NEGATIVE_INFINITY;
    private double south = Double.POSITIVE_INFINITY;
    private double west = Double.POSITIVE_INFINITY;

    public RouteInformation(){
    }
    public void addRoute(Route r){
        checkBounds(r.getNortheast(), r.getSouthwest());
        routes.add(r);
    }

    public List<Route> getRoutes(){
        return routes;
    }

    public Route getShortestRoute(){
        Route r = null;
        if(routes.size() > 0){
            r = routes.get(0);
            if(routes.size() > 1){
                for(int i = 1; i < routes.size(); i++){
                    if(routes.get(i).getDistance().valueInMeters() < routes.get(i - 1).getDistance().valueInMeters()){
                        r = routes.get(i);
                    }
                }
            }
        }
        return r;
    }

    public LatLng getStartPoint(){
        return routes.get(0).getLegs().get(0).getStartPoint();
    }

    public LatLng getEndPoint(){
        List<Leg> legs = routes.get(0).getLegs();
        return legs.get(legs.size() - 1).getEndPoint();
    }

    public String getStartAddress(){
        return routes.get(0).getLegs().get(0).getStartAddress();
    }

    public String getEndAddress(){
        List<Leg> legs = routes.get(0).getLegs();
        return legs.get(legs.size() - 1).getEndAddress();
    }

    private void checkBounds(LatLng ne, LatLng sw){
        if(ne.latitude > north)
            north = ne.latitude;
        if(ne.longitude > east)
            east = ne.longitude;

        if(sw.latitude < south)
            south = sw.latitude;
        if(sw.longitude < west)
            west = sw.longitude;
    }

    public LatLng getNortheastBounds(){
        return new LatLng(north, east);
    }

    public LatLng getSouthwestBounds(){
        return new LatLng(south, west);
    }

    public LatLngBounds getBounds(){
        return new LatLngBounds(getSouthwestBounds(), getNortheastBounds());
    }
}
