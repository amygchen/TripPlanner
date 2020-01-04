import java.io.*;
import java.util.ArrayList;

public class Trip {
    private String city;
    private String state;
    private int latDeg;
    private int latMin;
    private int longDeg;
    private int longMin;

    public Trip (String initCity, String initState, int initLatDeg, int initLatMin,
                 int initLongDeg, int initLongMin){
        city = initCity; state = initState;
        latDeg = initLatDeg; latMin = initLatMin;
        longDeg = initLongDeg; longMin = initLongMin;
    }

    public String getName(){
        return city;
    }
    public String getState(){return state;}
    public int getLatDeg(){
        return latDeg;
    }
    public int getLatMin(){
        return latMin;
    }
    public int getLongDeg(){
        return longDeg;
    }
    public int getLongMin(){
        return longMin;
    }


    public String toString(){
        return city + ", " + state + ", " + latDeg + ", " + latMin + ", " + longDeg + ", " + longMin +"\n";
    }



}