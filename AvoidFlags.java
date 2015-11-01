package com.innovations.simple.simplemiles.maps;

/**
 * Created by keiron on 10/28/15.
 */
public class AvoidFlags{
    private boolean tolls = false;
    private boolean highways = false;
    private boolean ferries = false;

    public AvoidFlags(){}

    public AvoidFlags(boolean tolls, boolean highways, boolean ferries){
        this.tolls = tolls;
        this.highways = highways;
        this.ferries = ferries;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("");
        if(tolls || highways || ferries){
            sb.append("avoid=");
            if(tolls){
                sb.append("tolls");
                if(highways)
                    sb.append("|highways");
                if(ferries)
                    sb.append("|ferries");
            }else if(highways){
                sb.append("highways");
                if(ferries)
                    sb.append("|ferries");
            }else if(ferries){
                sb.append("ferries");
            }
        }

        return sb.toString();
    }
}
