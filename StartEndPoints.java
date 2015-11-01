package com.innovations.simple.simplemiles.maps;

/**
 * Created by keiron on 10/28/15.
 */
public class StartEndPoints
{
    private String start;
    private String end;

    public StartEndPoints(String startAddress, String endAddress){
        if(startAddress == null || endAddress == null)
            throw new NullPointerException();
        start = startAddress;
        end = endAddress;
    }

    public String getStartAddress(){
        return start.replaceAll(" ", "%20");
    }

    public String getEndAddress(){
        return end.replaceAll(" ", "%20");
    }
}
