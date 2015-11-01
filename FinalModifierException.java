package com.innovations.simple.simplemiles.maps;

/**
 * Created by keiron on 10/28/15.
 */
public class FinalModifierException extends Exception
{
    public FinalModifierException(){
        super("Redefining this member is not allowed.");
    }

    public FinalModifierException(String msg){
        super("Redefining this member is not allowed. " + msg);
    }
}
