package com.skgadi.controltoolbox;

/**
 * Created by gadis on 14-Feb-18.
 */


public class Parameter {
    public String Name;
    public float Min;
    public float Max;
    public float DefaultValue;
    Parameter (String name, float min, float max, float defaultValue) {
        Name = name;
        Min = min;
        Max = max;
        DefaultValue = defaultValue;
    }
}