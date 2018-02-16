package com.skgadi.controltoolbox;

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
    public boolean Compliment = false;
    public float[][] MinMaxDefaultsForFloats = {
            {0, 100, 50}, {0, 5, 2.5f}, {0, 10, 0}, {0, 100, 50}, {-5, 5, 0}
    };

    public void SetType (SignalType type) {
        Type = type;
    }
    public void SetType (int i) {
        Type = SignalType.values()[i];
    }
    public String GetSignalDescription () {
        switch (Type) {
            case STEP:
                return ((Compliment) ? "-1":"")+MaximumAmplitude+ "H()";
            case SINE:
                return "(Sine)";
            case SAWTOOTH:
                return "()";
            case TRIANGLE:
                return "()";
            case SQUARE:
                return "()";
            case RECTANGLE:
                return "()";
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
            return ((!Compliment) ? 1 : -1) * MaximumAmplitude + OffSet;
        else
            return ((Compliment) ? 1 : -1) * MaximumAmplitude + OffSet;
    }
    public float GenSine() {
        if (Time>=StartAt)
            return ((!Compliment) ? -1 : 1) * ((float) (MaximumAmplitude * Math.sin(2 * Math.PI * Frequency * (Time-StartAt)))) + OffSet;
        else
            return 0;
    }
    public float GenSawTooth() {
        float TimePeriod = 1/Frequency;
        if (Time>=StartAt)
            return ((!Compliment) ? -1 : 1) * (((Time - StartAt)%TimePeriod)*2*MaximumAmplitude/TimePeriod - MaximumAmplitude) + OffSet;
        else
            return 0;
    }
    public float GenTriangle() {
        float TimePeriod = 1/Frequency;
        if (Time>=StartAt) {
            if (((Time-StartAt)%TimePeriod)<(TimePeriod/2))
                return ((!Compliment) ? -1 : 1) * (4*MaximumAmplitude/TimePeriod*(((Time - StartAt)%TimePeriod) - TimePeriod/2) + MaximumAmplitude) + OffSet;
            else
                return ((!Compliment) ? -1 : 1) * (-4*MaximumAmplitude/TimePeriod*(((Time - StartAt)%TimePeriod) - TimePeriod/2) + MaximumAmplitude) + OffSet;
        } else
            return 0;
    }
    public float GetSquare() {
        GetRectangle(50);
            return 0;
    }
    public float GetRectangle(float DutyCycle) {
        float TimePeriod = 1/Frequency;
        if (Time>=StartAt) {
            if (((Time-StartAt)%TimePeriod)<(DutyCycle/100))
                return ((!Compliment) ? -1 : 1) * MaximumAmplitude + OffSet;
            else
                return ((Compliment) ? -1 : 1) * MaximumAmplitude + OffSet;

        } else
            return 0;
    }

}
