package com.skgadi.controltoolbox;

import android.util.Log;

/**
 * Created by gadis on 14-Feb-18.
 */
enum SignalType {
    STEP,
    SINE,
    SAWTOOTH,
    TRIANGLE,
    SQUARE,
    RECTANGLE
}
public class FunctionGenerator {
    public SignalType Type;
    public float Frequency;
    public float MaximumAmplitude;
    public float StartAt;
    public float DutyCycle;
    public float OffSet;
    public float Time;
    public boolean Compliment;
    public float[][] MinMaxDefaultsForFloats = {
            {0, 5, 2.5f}, {0, 5, 0}, {0, 10, 0}, {0, 100, 50}, {-5, 5, 0}
    };
    public FunctionGenerator() {
        Type = SignalType.STEP;
        Frequency = 2.5f;//MinMaxDefaultsForFloats[0][2];
        MaximumAmplitude = MinMaxDefaultsForFloats[1][2];
        StartAt = MinMaxDefaultsForFloats[2][2];
        DutyCycle = MinMaxDefaultsForFloats[3][2];
        OffSet = MinMaxDefaultsForFloats[4][2];
        Time = 0;
        Compliment = false;
    }
    public void SetType (SignalType type) {
        Type = type;
    }
    public void SetType (int i) {
        Type = SignalType.values()[i];
    }
    public String GetSignalDescription () {
        switch (Type) {
            case STEP:
                return ((Compliment) ? "-":"") + MaximumAmplitude
                        + "[2H(t"
                        + ((StartAt!=0) ? -StartAt:"")
                        + ")-1]"
                        + ((OffSet!=0) ? (OffSet>0?"+"+OffSet:OffSet) :"");
            case SINE:
                return ((Compliment) ? "-":"") + MaximumAmplitude
                        + "sin(2\u03C0"
                        + Frequency
                        + ((StartAt!=0) ? "(":"")
                        + "t"
                        + ((StartAt!=0) ? -StartAt+")":"")
                        + ")"
                        + ((OffSet!=0) ? (OffSet>0?"+"+OffSet:OffSet) :"");
            case SAWTOOTH:
                return ((Compliment) ? "-":"") + MaximumAmplitude
                        + "\u00D72["+
                        + Frequency
                        + ((StartAt!=0) ? "(":"")
                        + "t"
                        + ((StartAt!=0) ? -StartAt+")":"")
                        + "-floor(0.5 + "
                        + Frequency
                        + ((StartAt!=0) ? "(":"")
                        + "t"
                        + ((StartAt!=0) ? -StartAt+")":"")
                        + ")]"
                        + ((OffSet!=0) ? (OffSet>0?"+"+OffSet:OffSet) :"");
            case TRIANGLE:
                return ((Compliment) ? "-":"") + MaximumAmplitude
                        + "\u00D7\u222B{sgn[sin(2π"
                        + Frequency
                        + ((StartAt!=0) ? "(":"")
                        + "t"
                        + ((StartAt!=0) ? -StartAt+")":"")
                        + ")]}dt"
                        + ((OffSet!=0) ? (OffSet>0?"+"+OffSet:OffSet) :"");
            case SQUARE:
                return  ((Compliment) ? "-":"") + MaximumAmplitude
                        + "sgn[sin(2π"
                        + Frequency
                        + ((StartAt!=0) ? "(":"")
                        + "t"
                        + ((StartAt!=0) ? -StartAt+")":"")
                        + ")]"
                        + ((OffSet!=0) ? (OffSet>0?"+"+OffSet:OffSet) :"");
            case RECTANGLE:
                return "A pulse train with: Amplitude = "
                        + ((Compliment) ? "-":"") + 2*MaximumAmplitude
                        + "V, Frequency ="
                        + Frequency
                        + "Hz, Duty factor = "
                        + DutyCycle
                        +"%, and Offset ="
                        + OffSet + "V";
        }
        return "";
    }
    public float GetValue (float time) {
        Time = time;
        switch (Type) {
            case STEP:
                return GenStep();
            case SINE:
                return GenSine();
            case SAWTOOTH:
                return GenSawTooth();
            case TRIANGLE:
                return GenTriangle();
            case SQUARE:
                return GetSquare();
            case RECTANGLE:
                return GetRectangle(DutyCycle);
        }
        return 0.0f;
    }
    public float GenStep() {
        if (Time>=StartAt)
            return ((!Compliment) ? 1f : -1f) * MaximumAmplitude + OffSet;
        else
            return 0;
    }
    public float GenSine() {
        if (Time>=StartAt)
            return ((Compliment) ? -1f : 1f) * ((float) (MaximumAmplitude * Math.sin(2 * Math.PI * Frequency * (Time-StartAt)))) + OffSet;
        else
            return 0;
    }
    public float GenSawTooth() {
        float TimePeriod = 1/Frequency;
        if (Time>=StartAt)
            return ((Compliment) ? -1f : 1f) * (((Time - StartAt)%TimePeriod)*2*MaximumAmplitude/TimePeriod - MaximumAmplitude) + OffSet;
        else
            return 0;
    }
    public float GenTriangle() {
        float TimePeriod = 1/Frequency;
        if (Time>=StartAt) {
            if (((Time-StartAt)%TimePeriod)<(TimePeriod/2))
                return ((!Compliment) ? -1f : 1f) * (4*MaximumAmplitude/TimePeriod*(((Time - StartAt)%TimePeriod) - TimePeriod/2) + MaximumAmplitude) + OffSet;
            else
                return ((!Compliment) ? -1f : 1f) * (-4*MaximumAmplitude/TimePeriod*(((Time - StartAt)%TimePeriod) - TimePeriod/2) + MaximumAmplitude) + OffSet;
        } else
            return 0;
    }
    public float GetSquare() {
        return GetRectangle(50);
    }
    public float GetRectangle(float DutyCycle) {
        float TimePeriod = 1f/Frequency;
        Log.i("FunctionGenerator", "Val: "+(Time-StartAt)%TimePeriod);
        if (Time>=StartAt) {
            if (((Time-StartAt)%TimePeriod)<TimePeriod*(DutyCycle/100f))
                return ((Compliment) ? -1f : 1f) * MaximumAmplitude + OffSet;
            else
                return ((Compliment) ? 1f : -1f) * MaximumAmplitude + OffSet;

        } else
            return 0;
    }

}
