package com.innovations.simple.simplemiles.maps;

import java.util.concurrent.TimeUnit;

/**
 * Created by keiron on 10/28/15.
 */
public class Duration
{
    private long value;

    public Duration(long val){
        value = val;
    }

    public long value(){
        return value;
    }

    public String valueAsString(){
        int days = (int) TimeUnit.SECONDS.toDays(value);
        int hours = (int)TimeUnit.SECONDS.toHours(value) - (days *24);
        int minutes = (int)(TimeUnit.SECONDS.toMinutes(value) - (TimeUnit.SECONDS.toHours(value)* 60));

        StringBuilder sb = new StringBuilder();
        if(days > 0)
            sb.append(days + " days ");
        if(hours > 0)
            sb.append(hours + " hours ");
        sb.append(minutes + " minutes");
        return sb.toString();
    }

    public void add(Duration d){
        value += d.value();
    }
}
