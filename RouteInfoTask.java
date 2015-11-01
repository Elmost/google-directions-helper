package com.innovations.simple.simplemiles.maps;

import com.google.android.gms.maps.model.LatLng;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by keiron on 5/28/15.
 */
public class RouteInfoTask extends AsyncTask<Void, Void, RouteInformation>
{
    public static final String IMPERIAL = "imperial";
    public static final String METRIC = "metric";


    /**By quickest time*/
    public static final int DISTANCE_BY_QUICKEST_ROUTE = 1;

    /**By quickest time*/
    public static final int DURATION_BY_QUICKEST_ROUTE = 2;

    /**By shortest distance*/
    public static final int DISTANCE_BY_SHORTEST_ROUTE = 4;

    /**By shortest distance*/
    public static final int DURATION_BY_SHORTEST_ROUTE = 8;

    /**By longest time*/
    public static final int DISTANCE_BY_LONGEST_ROUTE = 16;

    /**By longest time*/
    public static final int DURATION_BY_LONGEST_ROUTE = 32;

    /**By farthes distance*/
    public static final int DISTANCE_BY_FARTHEST_ROUTE = 64;

    /**By farthest distance*/
    public static final int DURATION_BY_FARTHEST_ROUTE = 128;

    private StartEndPoints mAddresses;
    private AvoidFlags mAvoid;
    private String mUnit = null;

    private List<InfoRetrievedListener> listeners = new ArrayList<>();
    private List<ResponseListener> responseListeners = new ArrayList<>();

    /**
     @param addresses The origin and destination addresses
     @param flags Any paths to avoid
     @param unit IMPERIAL (miles) or METRIC (km)

     */
    public RouteInfoTask(StartEndPoints addresses, AvoidFlags flags, String unit)
    {
        if(addresses == null || flags == null)
            throw new NullPointerException();
        mAddresses = addresses;
        mAvoid = flags;
        mUnit = unit;
    }

    /**
     @param addresses The origin and destination addresses
     @param flags Any paths to avoid

     Unit default IMPERIAL (miles)

     */
    public RouteInfoTask(StartEndPoints addresses, AvoidFlags flags){
        this(addresses, flags, IMPERIAL);
    }

    /**
     @param addresses The origin and destination addresses
     No AvoidFlags
     Unit default IMPERIAL (miles)

     */
    public RouteInfoTask(StartEndPoints addresses){
        this(addresses, new AvoidFlags());
    }

    /**
     @param addresses The origin and destination addresses
     No AvoidFlags
     @param unit IMPERIAL (miles) or METRIC (km)

     */
    public RouteInfoTask(StartEndPoints addresses, String unit){
        this(addresses, new AvoidFlags(), unit);
    }

    @Override
    protected RouteInformation doInBackground(Void[] p1)
    {
        RouteInformation info = getRouteBy(mAddresses, mAvoid);
        return info;
    }

    @Override
    protected void onPostExecute(RouteInformation result)
    {
        notifyListeners(result);
        super.onPostExecute(result);
    }

    public void registerInfoListener(InfoRetrievedListener l)
    {
        listeners.add(l);
    }

    public void registerResponseListener(ResponseListener l){
        responseListeners.add(l);
    }

    private void notifyListeners(RouteInformation info)
    {
        for (InfoRetrievedListener l:listeners)
        {
            l.onInfoRetrieved(info);
        }
    }

    private RouteInformation getRouteBy(StartEndPoints addr, AvoidFlags avoid)
    {
        String url = "http://maps.google.com/maps/api/directions/json?origin="
                + addr.getStartAddress() + "&destination=" + addr.getEndAddress()
                + "&sensor=false&alternatives=true&units="
                + mUnit
                + avoid.toString();
        HttpResponse response = null;
        String json = null;
        RouteInformation ri = new RouteInformation();
        try
        {
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpPost httpPost = new HttpPost(url);
            response = httpClient.execute(httpPost, localContext);
            json = EntityUtils.toString(response.getEntity());
            for(ResponseListener l: responseListeners){
                l.onResponse(json);
            }
			/*Get routes*/
            JSONObject jo = new JSONObject(json);
            JSONArray routes = jo.getJSONArray("routes");
			/*Traverse routes*/
            for (int i = 0; i < routes.length(); i++)
            {
                JSONObject route = routes.getJSONObject(i);
                addRoute(route, ri);
            }
        }
        catch (ClientProtocolException e)
        {
            Log.d("maptest2", e.getMessage());
            e.printStackTrace();
        }
        catch (IOException e)
        {
            Log.d("maptest2", e.getMessage());
            e.printStackTrace();
        }
        catch (JSONException e)
        {
            Log.d("maptest2", e.getMessage());
            e.printStackTrace();
        }
        return ri;
    }

    private void addRoute(JSONObject json, RouteInformation ri) throws JSONException{
        Route route = new Route();
        try
        {
            route.setCopyrights(json.getString("copyrights"));
            route.setSummary(json.getString("summary"));
            JSONObject bounds = json.getJSONObject("bounds");

            route.setNortheast(new LatLng(
                    bounds.getJSONObject("northeast").getDouble("lat"),
                    bounds.getJSONObject("northeast").getDouble("lng")));

            route.setSouthwest(new LatLng(
                    bounds.getJSONObject("southwest").getDouble("lat"),
                    bounds.getJSONObject("southwest").getDouble("lng")));

            route.setEncodedOverviewPolyline(json.getJSONObject("overview_polyline").getString("points"));
            if(!json.has("distance")){
                Log.d("maptest2", "json doesnt have distance");
            }else{
                Distance dist = new Distance(json.getJSONObject("distance").getDouble("value"), json.getJSONObject("distance").getString("text"));
                route.setDistance(dist);
            }

            if(!json.has("duration")){
                Log.d("maptest2", "json doesnt have duration");
            }else{
                Duration dur = new Duration(json.getJSONObject("duration").getLong("value"));
                route.setDuration(dur);
            }

            JSONArray legs = json.getJSONArray("legs");
            for(int i = 0; i < legs.length(); i++){
                addLeg(legs.getJSONObject(i), route);
            }
            ri.addRoute(route);

        }
        catch (FinalModifierException e)
        {
            Log.d("maptest2", e.getMessage());
        }
    }

    private void addLeg(JSONObject json, Route route) throws JSONException{
        Leg leg = new Leg();

        try
        {
            Distance dist = new Distance(json.getJSONObject("distance").getDouble("value"),
                    json.getJSONObject("distance").getString("text"));
            leg.setDistance(dist);
            Duration dur = new Duration(json.getJSONObject("duration").getLong("value"));
            leg.setDuration(dur);
            LatLng start = new LatLng(json.getJSONObject("start_location").getDouble("lat"),
                    json.getJSONObject("start_location").getDouble("lng"));
            leg.setStartPoint(start);

            LatLng end = new LatLng(json.getJSONObject("end_location").getDouble("lat"),
                    json.getJSONObject("end_location").getDouble("lng"));
            leg.setEndPoint(end);
            leg.setStartAddress(json.getString("start_address"));
            leg.setEndAddress(json.getString("end_address"));
            JSONArray steps = json.getJSONArray("steps");
            for(int i = 0; i < steps.length(); i++){
                addStep(steps.getJSONObject(i), leg);
            }
            route.addLeg(leg);
        }
        catch (FinalModifierException e)
        {
            Log.d("maptest2", e.getMessage());
        }
    }

    private void addStep(JSONObject json, Leg leg) throws JSONException{
        Step step = new Step();
        try
        {
            step.setTravelMode(json.getString("travel_mode"));
            step.setHtmlInstructions(json.getString("html_instructions"));
            step.setPolyline(json.getString("polyline"));
            LatLng start = new LatLng(json.getJSONObject("start_location").getDouble("lat"),
                    json.getJSONObject("start_location").getDouble("lng"));
            step.setStartPoint(start);
            LatLng end = new LatLng(json.getJSONObject("end_location").getDouble("lat"),
                    json.getJSONObject("end_location").getDouble("lng"));
            step.setEndPoint(end);
            Distance dist = new Distance(json.getJSONObject("distance").getDouble("value"),
                    json.getJSONObject("distance").getString("text"));
            step.setDistance(dist);
            Duration dur = new Duration(json.getJSONObject("duration").getLong("value"));
            step.setDuration(dur);
            leg.addStep(step);
        }
        catch (FinalModifierException e)
        {
            Log.d("maptest2", e.getMessage());
        }
    }

   /* private float findBestRouteBy(int queryBy)
    {
        int len = durations.size();
        float dist = Float.POSITIVE_INFINITY;
        float dur = Float.POSITIVE_INFINITY;
        float response = -1f;

        switch (queryBy)
        {
            case DISTANCE_BY_QUICKEST_ROUTE:
                for (int i = 0; i < len; i++)
                {
                    if (durations.get(i) < dur)
                    {
                        dur = durations.get(i);
                        dist = distances.get(i);
                    }
                }
                response = dist;
                break;

            case DURATION_BY_QUICKEST_ROUTE:
                for (int i = 0; i < len; i++)
                {
                    if (durations.get(i) < dur)
                    {
                        dur = durations.get(i);
                    }
                }
                response = dur;
                break;

            case DISTANCE_BY_SHORTEST_ROUTE:
                for (int i = 0; i < len; i++)
                {
                    if (distances.get(i) < dist)
                    {
                        dist = distances.get(i);
                    }
                }
                response = dist;
                break;

            case DURATION_BY_SHORTEST_ROUTE:
                for (int i = 0; i < len; i++)
                {
                    if (distances.get(i) < dist)
                    {
                        dur = durations.get(i);
                        dist = distances.get(i);
                    }
                }
                response = dur;
                break;

            case DISTANCE_BY_LONGEST_ROUTE:
                dur = 0;
                dist = 0;
                for (int i = 0; i < len; i++)
                {
                    if (durations.get(i) > dur)
                    {
                        dur = durations.get(i);
                        dist = distances.get(i);
                    }
                }
                response = dist;
                break;

            case DURATION_BY_LONGEST_ROUTE:
                dur = 0;
                dist = 0;
                for (int i = 0; i < len; i++)
                {
                    if (durations.get(i) > dur)
                    {
                        dur = durations.get(i);
                    }
                }
                response = dur;
                break;

            case DISTANCE_BY_FARTHEST_ROUTE:
                dur = 0;
                dist = 0;
                for (int i = 0; i < len; i++)
                {
                    if (distances.get(i) > dist)
                    {
                        dist = distances.get(i);
                    }
                }
                response = dist;
                break;

            case DURATION_BY_FARTHEST_ROUTE:
                dur = 0;
                dist = 0;
                for (int i = 0; i < len; i++)
                {
                    if (distances.get(i) > dist)
                    {
                        dur = durations.get(i);
                        dist = distances.get(i);
                    }
                }
                response = dur;
                break;
        }
        return response;
    }*/

    public interface ResponseListener{
        void onResponse(String s);
    }

    public interface InfoRetrievedListener
    {
        void onInfoRetrieved(RouteInformation info);
    }
}
